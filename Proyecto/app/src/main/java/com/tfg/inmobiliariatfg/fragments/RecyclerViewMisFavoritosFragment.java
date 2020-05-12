package com.tfg.inmobiliariatfg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInmuebleAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisFavoritosFragment extends Fragment {

    private RecyclerView recyclerViewBusqueda;
    private RecyclerViewInfoAnuncioAdapter adaptadorBusqueda;
    private Bundle extras;
    Usuario usuario;
    int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view_favoritos, container, false);

        recyclerViewBusqueda = view.findViewById(R.id.recyclerFavorito);
        recyclerViewBusqueda.setLayoutManager(new LinearLayoutManager(getContext()));

        ObtenerAnunciosFavortios();

        return view;
    }

    public void ObtenerAnunciosFavortios() {
        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getFavortiosUsuario(idUsuario);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {

            }
        });
    }

    private void ShowIt(List<InfoAnuncio> response) {
        adaptadorBusqueda = new RecyclerViewInfoAnuncioAdapter(response);
        recyclerViewBusqueda.setAdapter(adaptadorBusqueda);
    }
}
