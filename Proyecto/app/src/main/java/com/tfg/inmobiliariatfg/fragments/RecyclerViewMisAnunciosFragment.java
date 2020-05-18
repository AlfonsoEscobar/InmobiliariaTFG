package com.tfg.inmobiliariatfg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisAnunciosFragment extends Fragment {

    private RecyclerView recyclerViewMisAnuncios;
    private RecyclerViewInfoAnuncioAdapter adaptadorBusqueda;
    private Bundle datos;
    private Usuario usuario;
    private int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_recycler_view_mis_anuncios, container, false);

        recyclerViewMisAnuncios = cl.findViewById(R.id.recyclerMisAnuncios);
        recyclerViewMisAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));

        ObtenerAnuncios();

        return cl;
    }

    public void ObtenerAnuncios() {
        datos = getArguments();
        if (datos != null) {
            usuario = (Usuario) datos.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getAnuncioPropietario(idUsuario);
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

    private void ShowIt(List<InfoAnuncio> response) {
        adaptadorBusqueda = new RecyclerViewInfoAnuncioAdapter(response);
        recyclerViewMisAnuncios.setAdapter(adaptadorBusqueda);
    }
}
