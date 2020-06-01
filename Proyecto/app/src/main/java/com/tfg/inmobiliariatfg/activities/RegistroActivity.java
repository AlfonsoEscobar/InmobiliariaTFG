package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCorreoRegistro, etNomRegistro, etTelRegistro,
            etTelOpRegistro, etPassRegistro, etPass2Registro;
    private Button btnConfirmarRegistro;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etCorreoRegistro = findViewById(R.id.etCorreoRegistro);
        etNomRegistro = findViewById(R.id.etNomRegistro);
        etTelRegistro = findViewById(R.id.etTelRegistro);
        etTelOpRegistro = findViewById(R.id.etTelOpRegistro);
        etPassRegistro = findViewById(R.id.etPassRegistro);
        etPass2Registro = findViewById(R.id.etPass2Registro);
        btnConfirmarRegistro = findViewById(R.id.btnConfirmarRegistro);
        btnConfirmarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                if (etCorreoRegistro.getText().toString().equals("") || etNomRegistro.getText().toString().equals("") ||
                        etTelRegistro.getText().toString().equals("") || etPassRegistro.getText().toString().equals("") ||
                        etPass2Registro.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Por favor introduzca todos los campos Obligatorios", Toast.LENGTH_LONG).show();
                } else {
                    if (etPassRegistro.getText().toString().equals(etPass2Registro.getText().toString())) {
                        usuario.setCorreo(String.valueOf(etCorreoRegistro.getText()));
                        usuario.setNombre(String.valueOf(etNomRegistro.getText()));
                        usuario.setTelefono1(String.valueOf(etTelRegistro.getText()));
                        if (!etTelOpRegistro.getText().toString().equals("")) {
                            usuario.setTelefono2(String.valueOf(etTelOpRegistro.getText()));
                        }
                        usuario.setContrasena(Metodos.codificarPass(String.valueOf(etPassRegistro.getText())));
                        usuario.setId_usuario(0);
                        usuario.setImagen_perfil(null);

                        Call<Void> usuarioCall = ApiAdapter.getApiService(getPref()).createUsuario(usuario);
                        usuarioCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext(), "El usuario ha sido registrado correctamente", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "La peticion no es correcta", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Fallo en la conexion", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Por favor introduzca de nuevo la contraseña, no coinciden", Toast.LENGTH_LONG).show();
                        etPassRegistro.setText("");
                        etPass2Registro.setText("");
                    }
                }
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
