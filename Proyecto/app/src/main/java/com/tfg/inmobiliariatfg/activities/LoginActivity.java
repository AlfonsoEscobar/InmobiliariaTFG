package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreoLogin, etPassLogin;
    Button btnRegistrar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreoLogin = findViewById(R.id.etCorreoLogin);
        etPassLogin = findViewById(R.id.etPassLogin);
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
                String PassHash = Metodos.codificarPass(etPassLogin.getText().toString());
                String Correo = etPassLogin.getText().toString();

                Call<Usuario> call = ApiAdapter.getApiService().getUsuario(Correo, PassHash);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Usuario usuario = response.body();
                            int idUsuario = usuario.getId_usuario();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("idUsuario", idUsuario);
                            startActivity(i);
                        } else {
                            etCorreoLogin.setHint("Correo");
                            etPassLogin.setHint("Contraseña");
                            Toast.makeText(getApplicationContext(), "El correo o la contraseña no son correctos", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "La conexion con la API no se esta realizando", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}
