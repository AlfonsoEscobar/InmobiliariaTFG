package com.tfg.inmobiliariatfg.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.tfg.inmobiliariatfg.activities.AnuncioCompletoActivity;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInmuebleAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisFavoritosFragment extends Fragment {

    private List<Favorito> listaFavoritos;
    private  ProgressDialog progressDialog;
    private RecyclerView recyclerViewFavorito;
    private RecyclerViewInfoAnuncioAdapter adaptadorFavorito;
    private Bundle extras;
    Usuario usuario;
    int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view_favoritos, container, false);

        recyclerViewFavorito = view.findViewById(R.id.recyclerFavorito);
        recyclerViewFavorito.setLayoutManager(new LinearLayoutManager(getContext()));

        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }else {
            idUsuario = 0;
        }

        compararFavorito(idUsuario);
        ObtenerAnunciosFavortios();

        return view;
    }

    public void ObtenerAnunciosFavortios() {

        progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Cargando tus favoritos...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getFavortiosUsuario(idUsuario);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {

            }
        });
    }
    private void compararFavorito(int idUsuario) {
        if (idUsuario != 0) {
            final Call<List<Favorito>> callCompararFavoritos = ApiAdapter.getApiService().getCompararFavorito(idUsuario);
            callCompararFavoritos.enqueue(new Callback<List<Favorito>>() {
                @Override
                public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            callCompararFavoritos(response.body());
                        }
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

        adaptadorFavorito = new RecyclerViewInfoAnuncioAdapter(response, listaFavoritos);
        adaptadorFavorito.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoAnuncio anuncio = response.get(recyclerViewFavorito.getChildAdapterPosition(v));
                Intent intent = new Intent(getActivity(), AnuncioCompletoActivity.class);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);
            }
        });

        recyclerViewFavorito.setAdapter(adaptadorFavorito);
    }
}
