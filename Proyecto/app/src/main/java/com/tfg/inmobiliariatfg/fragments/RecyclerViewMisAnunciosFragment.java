package com.tfg.inmobiliariatfg.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.tfg.inmobiliariatfg.adapters.RecyclerViewInfoAnuncioAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisAnunciosFragment extends Fragment {

    private List<Favorito> listaFavoritos = null;
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
        } else {
            idUsuario = 0;
        }

        return cl;
    }

    public void obtenerAnuncios() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Cargando tus Anuncios...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService(getPref()).getAnuncioPropietario(idUsuario);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "No tienes anuncios todavia.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void ShowIt(final List<InfoAnuncio> response) {

        adaptadorMisAnuncios = new RecyclerViewInfoAnuncioAdapter(response, listaFavoritos);
        adaptadorMisAnuncios.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InfoAnuncio anuncio = response.get(recyclerViewMisAnuncios.getChildAdapterPosition(v));
                dialogMisAnuncios(anuncio);
            }
        });

        recyclerViewMisAnuncios.setAdapter(adaptadorMisAnuncios);
    }

    private void dialogMisAnuncios(final InfoAnuncio anuncio){
        final CharSequence[] opciones = {"Modificar Anuncio", "Eliminar anuncio", "Acceder Anuncio", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Acceder Anuncio")) {
                    Intent intent = new Intent(getContext(), AnuncioCompletoActivity.class);
                    intent.putExtra("anuncio", anuncio);
                    startActivity(intent);
                } else {
                    if (opciones[which].equals("Eliminar anuncio")) {
                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        Metodos.mostrarDialogo(progressDialog);
                        progressDialog.setMessage("Eliminando anuncio...");
                        Call<Void> call = ApiAdapter.getApiService(getPref()).eliminarAnuncio(anuncio.getId_inmueble(), anuncio.getTipo_anuncio());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 200){
                                    obtenerAnuncios();
                                    Toast.makeText(getContext(),
                                            "Anuncio Eliminado", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }else {
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        if(opciones[which].equals("Modificar Anuncio")){
                            modificarPrecio(anuncio);
                        }else {
                            dialog.dismiss();
                        }
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void modificarPrecio(final InfoAnuncio anuncio){
        final AlertDialog.Builder alertMofificarNombrePerfil = new AlertDialog.Builder(getContext());
        alertMofificarNombrePerfil.setTitle("Escriba el precio nuevo");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertMofificarNombrePerfil.setView(input);

        alertMofificarNombrePerfil.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Double precio = Double.parseDouble(input.getText().toString());
                        if (precio <= 0) {
                            Toast.makeText(getContext(),
                                    "Por favor introduzca un precio valido", Toast.LENGTH_LONG).show();
                        } else {
                            Call<Void> modificarCall = ApiAdapter.getApiService(getPref()).putModificarAnuncio(anuncio.getId_inmueble(), anuncio.getTipo_anuncio(), precio);
                            modificarCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 200) {
                                        obtenerAnuncios();
                                        Toast.makeText(getContext(),
                                                "El precio ha sido modificado", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(),
                                            "La conexión con el servidor ha fallado", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
        alertMofificarNombrePerfil.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertMofificarNombrePerfil.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        obtenerAnuncios();
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }
}
