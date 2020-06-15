package com.tfg.inmobiliariatfg.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RegistrarInmuebleActivity;
import com.tfg.inmobiliariatfg.adapters.RecyclerViewInmuebleAdapter;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisInmueblesFragment extends Fragment {

    private RecyclerView recyclerMisInmuebles;
    private RecyclerViewInmuebleAdapter adaptadorMisInmuebles;
    private Bundle extras;
    private Button btnRegistrarInmueble;
    private Usuario usuario;
    private int idUsuario;
    List<String> listaRutas;
    int contador = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view_mis_inmuebles, container, false);

        btnRegistrarInmueble = view.findViewById(R.id.btnRegistrarInmueble);
        btnRegistrarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrarInmuebleActivity.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        recyclerMisInmuebles = view.findViewById(R.id.recyclerMisInmuebles);
        recyclerMisInmuebles.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        obtenerInmuebles();

        return view;
    }

    public void obtenerInmuebles() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Accediendo a tus inmuebles...");

        Call<List<Inmueble>> listCall = ApiAdapter.getApiService(getPref()).getInmueblePropietario(idUsuario);
        listCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    ShowIt(response.body());
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "No tienes inmuebles todavia.", Toast.LENGTH_LONG);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void ShowIt(final List<Inmueble> response) {
        List<Inmueble> inmueblesLista = response;
        adaptadorMisInmuebles = new RecyclerViewInmuebleAdapter(getContext(), inmueblesLista);
        recyclerMisInmuebles.setAdapter(adaptadorMisInmuebles);

        setImagenes(inmueblesLista);
    }

    private void setImagenes(List<Inmueble> inmueblesLista) {
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TFG/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final String pathGuardar = dir.getAbsolutePath() + "/";
        Log.v("pathGuardar", "" + pathGuardar);


        for (int i = 0; i < inmueblesLista.size(); i++) {
            final ArrayList<Uri> listUriImages = new ArrayList<>();
            int idInmueble = inmueblesLista.get(i).getId_inmueble();
            RequestHandle handle = null;

            Call<List<String>> listCall = ApiAdapter.getApiService(getPref()).getStringsUriFotos(idInmueble);
            try {
                Response<List<String>> response = listCall.execute();
                nombreImagenes(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (listaRutas != null) {
                for (int j = 0; j < listaRutas.size(); j++) {
                    contador++;
                    String ruta = getPref() + "fotografia/inmueble/" + idInmueble + "/" + listaRutas.get(j);
                    final int finalJ = j;
                    final int finalI = i;
                    final int finalContador = contador;
                    handle = new AsyncHttpClient().get(ruta, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String nombre = System.currentTimeMillis() / 100 + finalContador + ".jpeg";
                            Uri uriPath = null;
                            FileOutputStream fOS;
                            try {
                                fOS = new FileOutputStream(pathGuardar + nombre);
                                fOS.write(responseBody, 0, responseBody.length);
                                fOS.close();
                                uriPath = Uri.parse(pathGuardar + nombre);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (uriPath != null) {
                                listUriImages.add(uriPath);
                                Log.v("list", "" + pathGuardar + nombre);
                                Log.v("nombre", "" + nombre);
                            }
                        }


                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    while (!handle.isFinished()) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                String archivo = "android.resource://" + getActivity().getPackageName() + "/" + R.drawable.ic_sin_imagen;
                Uri ruta = Uri.parse(archivo);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
                listUriImages.add(ruta);
            }
            inmueblesLista.get(i).setRutasFile(listUriImages);
        }
        adaptadorMisInmuebles.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        obtenerInmuebles();
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("rutaURL", Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl", "https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }

    private void nombreImagenes(List<String> response) {
        listaRutas = response;
    }

}
