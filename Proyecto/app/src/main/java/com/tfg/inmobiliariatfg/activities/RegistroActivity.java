package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.RegistroService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCorreoRegistro, etNomRegistro, etTelRegistro,
            etTelOpRegistro, etPassRegistro, etPass2Registro;
    private Button btnConfirmarRegistro;

    RegistroService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etCorreoRegistro = (EditText) findViewById(R.id.etCorreoRegistro);
        etNomRegistro = (EditText) findViewById(R.id.etNomRegistro);
        etTelRegistro = (EditText) findViewById(R.id.etTelRegistro);
        etTelOpRegistro = (EditText) findViewById(R.id.etTelOpRegistro);
        etPassRegistro = (EditText) findViewById(R.id.etPassRegistro);
        etPass2Registro = (EditText) findViewById(R.id.etPass2Registro);
        btnConfirmarRegistro = (Button) findViewById(R.id.btnConfirmarRegistro);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://405b8624.ngrok.io/Restful_Inmo/servicios/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RegistroService.class);

        btnConfirmarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Correo = etCorreoRegistro.getText().toString();
                String Pass = etPassRegistro.getText().toString();
                Call<InfoUsuario> call = service.getUsuario(Pass);

                call.enqueue(new Callback<InfoUsuario>() {
                    @Override
                    public void onResponse(Call<InfoUsuario> call, Response<InfoUsuario> response) {
                        InfoUsuario infoUsuario = response.body();
                        etNomRegistro.setText(infoUsuario.getNombreUsuario());
                    }
                    @Override
                    public void onFailure(Call<InfoUsuario> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"No",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
