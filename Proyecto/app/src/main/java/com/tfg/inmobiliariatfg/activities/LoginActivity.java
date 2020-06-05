package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static ProgressDialog progressDialog;
    private EditText etCorreoLogin, etPassLogin;
    private Button btnRegistrar, btnLogin;
    private ImageView ivLogin;
    private String remoteUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreoLogin = findViewById(R.id.etCorreoLogin);
        etPassLogin = findViewById(R.id.etPassLogin);
        ivLogin = findViewById(R.id.ivLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Autentificando credenciales...");
                Metodos.mostrarDialogo(progressDialog);


                String PassHash = Metodos.codificarPass(etPassLogin.getText().toString());
                String Correo = etCorreoLogin.getText().toString();

                Call<Usuario> call = ApiAdapter.getApiService(getPref()).getUsuario(Correo, PassHash);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Usuario usuario = response.body();
                            //int idUsuario = usuario.getId_usuario();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("usuario", usuario);
                            startActivity(i);
                            progressDialog.dismiss();

                        } else {
                            etCorreoLogin.setHint("Correo");
                            etPassLogin.setHint("Contraseña");
                            Toast.makeText(getApplicationContext(), "El correo o la contraseña no son correctos", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "La conexion con la API no se esta realizando", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        int random = (int) (Math.random()*3+1);
        remoteUri = getPref() +  "fotografia/portada/1";
        cargarImagen(remoteUri);
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
                    ivLogin.setImageURI(uriPath);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public String getPref() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }
}
