package com.tfg.inmobiliariatfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RecyclerViewBusquedaActivity;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class BuscadorFragment extends Fragment {

    private Bundle datos;
    private Usuario usuario;
    private int idUsuario;
    private EditText etLocalidadBuscador;
    private ImageView ivBuscador;
    private RadioButton rbComprarBuscador, rbAlquilarBuscador;
    private Button btnBuscarBuscador;
    private String remoteUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_buscador, container, false);

        etLocalidadBuscador = cl.findViewById(R.id.etLocalidadBuscador);
        ivBuscador = cl.findViewById(R.id.ivBuscador);
        rbComprarBuscador = cl.findViewById(R.id.rbComprarBuscador);
        rbAlquilarBuscador = cl.findViewById(R.id.rbAlquilerBuscador);
        btnBuscarBuscador = cl.findViewById(R.id.btnBuscarBuscador);

        btnBuscarBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecyclerViewBusquedaActivity.class);
                String localidad = etLocalidadBuscador.getText().toString();
                if (localidad.equals("")) {
                    Log.i("Error", "No ha sido introducido una localidad");
                } else {
                    String localidadPut = etLocalidadBuscador.getText().toString();
                    String tipoPut;
                    i.putExtra("idUsuario",idUsuario);
                    i.putExtra("localidad", localidadPut);
                    if (rbAlquilarBuscador.isChecked()) {
                        tipoPut = "alquilar";
                        i.putExtra("tipo", tipoPut);
                    } else {
                        tipoPut = "comprar";
                        i.putExtra("tipo", tipoPut);
                    }
                    etLocalidadBuscador.setText("");
                    etLocalidadBuscador.setHint("Localidad");
                    rbComprarBuscador.isChecked();
                    startActivity(i);
                }
            }
        });
        datos = getArguments();
        if(datos != null){
            usuario = (Usuario) datos.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }
        int random = (int) (Math.random()*3+1);
        remoteUri = getPref() +  "fotografia/portada/1";
        cargarImagen(remoteUri);

        return cl;
    }

    public void cargarImagen(String remoteUri){
        final File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TFG/");
        final String pathGuardar;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        pathGuardar = dir.getAbsolutePath() + "/TFG";
        Log.v("pathGuardar", "" + pathGuardar);

        new AsyncHttpClient().get(remoteUri, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String nombre = System.currentTimeMillis() / 100 + ".jpeg";
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
                    ivBuscador.setImageURI(uriPath);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }
}
