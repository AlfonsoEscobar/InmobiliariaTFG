package com.tfg.inmobiliariatfg.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.AnuncioCompletoActivity;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.adapters.RecyclerViewInfoAnuncioAdapter;

import java.io.Serializable;
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
    private Usuario usuario;
    private int idUsuario;

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

        return view;
    }

    public void ObtenerAnunciosFavortios() {

        progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Cargando tus favoritos...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService(getPref()).getFavortiosUsuario(idUsuario);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                    progressDialog.dismiss();
                }else {
                    Toast.makeText(getActivity(), "No tienes favoritos todavia.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
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
                intent.putExtra("favoritos", (Serializable) listaFavoritos);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("anuncio", anuncio);
                startActivity(intent);
            }
        });

        recyclerViewFavorito.setAdapter(adaptadorFavorito);
    }

    @Override
    public void onResume() {
        super.onResume();
        compararFavorito(idUsuario);
        ObtenerAnunciosFavortios();
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }
}
