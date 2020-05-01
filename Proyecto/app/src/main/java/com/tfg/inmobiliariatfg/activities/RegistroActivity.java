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
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.ApiService;

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


        btnConfirmarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCorreoRegistro.equals("") || etNomRegistro.equals("") || etTelRegistro.equals("") || etPassRegistro.equals("") || etPass2Registro.equals("")) {
                    Toast.makeText(getApplicationContext(), "Por favor introduzca todos los campos con un asterisco", Toast.LENGTH_LONG);
                } else {

                }
            }
        });
    }
}
