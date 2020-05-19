package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewBusquedaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBusqueda;
    private RecyclerViewInfoAnuncioAdapter adaptadorBusqueda;
    private Bundle extras;
    String tipo, localidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view_busqueda);

        recyclerViewBusqueda = findViewById(R.id.recyclerBusqueda);
        recyclerViewBusqueda.setLayoutManager(new LinearLayoutManager(this));

        ObtenerAnuncios();
    }

    public void ObtenerAnuncios() {
        extras = getIntent().getExtras();
        if (extras != null) {
            tipo = extras.getString("tipo");
            localidad = extras.getString("localidad");
        }

        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getAnuncioLocalidad(tipo, localidad);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                    ShowIt(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {

            }
        });
    }

    private void ShowIt(final List<InfoAnuncio> response){

        adaptadorBusqueda = new RecyclerViewInfoAnuncioAdapter(response);
        adaptadorBusqueda.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoAnuncio anuncio = response.get(recyclerViewBusqueda.getChildAdapterPosition(v));
                Intent intent = new Intent(getApplicationContext(), AnuncioCompletoActivity.class);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);

            }
        });

        recyclerViewBusqueda.setAdapter(adaptadorBusqueda);
    }
}
