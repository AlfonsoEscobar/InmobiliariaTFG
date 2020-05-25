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
import com.tfg.inmobiliariatfg.activities.AnuncioCompletoActivity;
import com.tfg.inmobiliariatfg.activities.CrearAnuncioActivity;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisAnunciosFragment extends Fragment {

    private List<Favorito> listaFavoritos;
    private  ProgressDialog progressDialog;
    private RecyclerView recyclerViewMisAnuncios;
    private RecyclerViewInfoAnuncioAdapter adaptadorMisAnuncios;
    private Button btnCrearAnuncio;
    private Bundle datos;
    private Usuario usuario;
    private int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_recycler_view_mis_anuncios, container, false);

        btnCrearAnuncio = cl.findViewById(R.id.btnCrearAnuncio);
        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCrearAnuncio = new Intent(getActivity(), CrearAnuncioActivity.class);
                intentCrearAnuncio.putExtra("idUsuario", idUsuario);
                startActivity(intentCrearAnuncio);
            }
        });

        recyclerViewMisAnuncios = cl.findViewById(R.id.recyclerMisAnuncios);
        recyclerViewMisAnuncios.setLayoutManager(new LinearLayoutManager(getContext()));

        datos = getArguments();
        if (datos != null) {
            usuario = (Usuario) datos.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }else{
            idUsuario = 0;
        }

        compararFavorito(idUsuario);
        obtenerAnuncios();

        return cl;
    }

    public void obtenerAnuncios() {

        progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Cargando tus Anuncios...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getAnuncioPropietario(idUsuario);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ShowIt(response.body());
                        progressDialog.dismiss();
                    }
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

        adaptadorMisAnuncios = new RecyclerViewInfoAnuncioAdapter(response, listaFavoritos);
        adaptadorMisAnuncios.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoAnuncio anuncio = response.get(recyclerViewMisAnuncios.getChildAdapterPosition(v));
                Intent intent = new Intent(getActivity(), AnuncioCompletoActivity.class);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);
            }
        });

        recyclerViewMisAnuncios.setAdapter(adaptadorMisAnuncios);
    }

    @Override
    public void onResume() {
        super.onResume();
        obtenerAnuncios();
    }
}
