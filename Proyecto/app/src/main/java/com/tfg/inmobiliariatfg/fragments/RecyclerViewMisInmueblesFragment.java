package com.tfg.inmobiliariatfg.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RegistrarInmuebleActivity;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInmuebleAdapter;

import java.util.List;

import kotlin.reflect.KCallable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfg.inmobiliariatfg.R.id.recyclerMisInmuebles;

public class RecyclerViewMisInmueblesFragment extends Fragment {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerMisInmuebles;
    private RecyclerViewInmuebleAdapter adaptadorMisInmuebles;
    private Bundle extras;
    Button btnRegistrarInmueble;
    Usuario usuario;
    int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_recycler_view_mis_inmuebles, container, false);

        btnRegistrarInmueble = view.findViewById(R.id.btnRegistrarInmueble);
        btnRegistrarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrarInmuebleActivity.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivityForResult(intent, 8);
            }
        });
        recyclerMisInmuebles = view.findViewById(R.id.recyclerMisInmuebles);
        recyclerMisInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));

        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        ObtenerInmuebles();

        return view;
    }

    public void ObtenerInmuebles() {

        progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);

        Call<List<Inmueble>> listCall = ApiAdapter.getApiService().getInmueblePropietario(idUsuario);
        listCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    ShowIt(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });
        progressDialog.dismiss();
    }

    private void ShowIt(List<Inmueble> response){
        adaptadorMisInmuebles = new RecyclerViewInmuebleAdapter(response);
        recyclerMisInmuebles.setAdapter(adaptadorMisInmuebles);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ObtenerInmuebles();
    }
}
