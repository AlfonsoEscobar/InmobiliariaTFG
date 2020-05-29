package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.adapters.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewBusquedaActivity extends AppCompatActivity {

    private List<Favorito> listaFavoritos;
    private RecyclerView recyclerViewBusqueda;
    private RecyclerViewInfoAnuncioAdapter adaptadorBusqueda;
    private Bundle datos;
    String tipo, localidad;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view_busqueda);

        recyclerViewBusqueda = findViewById(R.id.recyclerBusqueda);
        recyclerViewBusqueda.setLayoutManager(new LinearLayoutManager(this));

        datos = getIntent().getExtras();
        if (datos != null) {
            tipo = datos.getString("tipo");
            localidad = datos.getString("localidad");
            idUsuario = datos.getInt("idUsuario");
        } else {
            idUsuario = 0;
        }

        compararFavorito(idUsuario);
        ObtenerAnuncios();

    }

    public void ObtenerAnuncios() {

        final ProgressDialog progressDialog = new ProgressDialog(RecyclerViewBusquedaActivity.this);
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Cargando Anuncios...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService(getPref()).getAnuncioLocalidad(tipo, localidad);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "no hay anuncios en esa localidad", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void compararFavorito(int idUsuario) {
        if (idUsuario != 0) {
            final Call<List<Favorito>> callCompararFavoritos = ApiAdapter.getApiService(getPref()).getCompararFavorito(idUsuario);
            callCompararFavoritos.enqueue(new Callback<List<Favorito>>() {
                @Override
                public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                    if (response.isSuccessful()) {

                        callCompararFavoritos(response.body());

                    }
                }

                @Override
                public void onFailure(Call<List<Favorito>> call, Throwable t) {

                }
            });
        }
    }

    private void callCompararFavoritos(List<Favorito> datosAComparar) {
        listaFavoritos = datosAComparar;
    }

    private void ShowIt(final List<InfoAnuncio> response) {

        adaptadorBusqueda = new RecyclerViewInfoAnuncioAdapter(response, listaFavoritos);
        adaptadorBusqueda.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoAnuncio anuncio = response.get(recyclerViewBusqueda.getChildAdapterPosition(v));
                Intent intent = new Intent(getApplicationContext(), AnuncioCompletoActivity.class);
                intent.putExtra("favoritos", (Serializable) listaFavoritos);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);

            }
        });

        recyclerViewBusqueda.setAdapter(adaptadorBusqueda);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compararFavorito(idUsuario);
        ObtenerAnuncios();
    }

    public String getPref() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }
}
