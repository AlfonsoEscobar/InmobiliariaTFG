package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.utiles.DiagnosticVetApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCorreoRegistro, etNomRegistro, etTelRegistro,
            etTelOpRegistro, etPassRegistro, etPass2Registro;
    private Button btnConfirmarRegistro;

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

        Call<InfoUsuario> call = DiagnosticVetApiAdapter.getApiService().getUsuario("alfonso@gmail.es","alfonso123");
        call.enqueue(new Callback<InfoUsuario>() {
            @Override
            public void onResponse(Call<InfoUsuario> call, Response<InfoUsuario> response) {
                if (response.isSuccessful()){
                    InfoUsuario infoUsuario = response.body();
                    Toast.makeText(getApplicationContext(),infoUsuario.getNombreUsuario(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InfoUsuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"la conexion no se ha producido",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Pulsame2(View v) {

    }
}
