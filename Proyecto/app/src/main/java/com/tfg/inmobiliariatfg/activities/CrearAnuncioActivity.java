package com.tfg.inmobiliariatfg.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Anuncio;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.adapters.RecyclerViewCrearAnuncioAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearAnuncioActivity extends AppCompatActivity {

    private List<InfoAnuncio> listaExiste = null;
    private RecyclerView recyclerViewCrearAnuncio;
    private RecyclerViewCrearAnuncioAdapter adaptadorCrearAnuncio;

    private Bundle datos;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        recyclerViewCrearAnuncio = findViewById(R.id.recyclerCrearAnuncio);
        recyclerViewCrearAnuncio.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        datos = getIntent().getExtras();
        if (datos != null) {
            idUsuario = datos.getInt("idUsuario");
        }
        ObtenerInmuebles();
    }

    public void ObtenerInmuebles() {
        final ProgressDialog progressDialog = new ProgressDialog(CrearAnuncioActivity.this);
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Procesando...");
        Call<List<Inmueble>> listCall = ApiAdapter.getApiService(getPref()).getInmueblePropietario(idUsuario);
        listCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ShowIt(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });
    }

    private void ShowIt(final List<Inmueble> response) {
        adaptadorCrearAnuncio = new RecyclerViewCrearAnuncioAdapter(response);
        adaptadorCrearAnuncio.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Inmueble inmueble = response.get(recyclerViewCrearAnuncio.getChildAdapterPosition(v));
                final int idInmueble = inmueble.getId_inmueble();
                Call<List<InfoAnuncio>> listComprobarCall = ApiAdapter.getApiService(getPref()).getFavortiosUsuario(idUsuario);
                listComprobarCall.enqueue(new Callback<List<InfoAnuncio>>() {
                    @Override
                    public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                        existeLista(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {

                    }
                });

                final String localidadInmueble = inmueble.getLocalidad();
                final CharSequence[] opciones = {"Vender", "Alquilar", "Cancelar"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(CrearAnuncioActivity.this);
                alertOpciones.setTitle("Seleccione una opción");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean existe = true;
                        if (opciones[which].equals("Vender")) {
                            String tipoVender = "comprar";
                            if (listaExiste != null) {
                                for (int i = 0; i < listaExiste.size(); i++) {
                                    if (listaExiste.get(i).getTipo_anuncio().equals("comprar") && listaExiste.get(i).getInmueble().getId_inmueble() == idInmueble) {
                                        existe = false;
                                    }
                                }
                            }
                            if (existe) {
                                dialogInsertarAnuncio(tipoVender, idInmueble);
                            } else {
                                Toast.makeText(CrearAnuncioActivity.this,
                                        "ya ha sido creado un anuncio de este tipo y con este inmueble", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (opciones[which].equals("Alquilar")) {
                                String tipoAlquilar = "alquilar";
                                if (listaExiste != null) {
                                    for (int i = 0; i < listaExiste.size(); i++) {
                                        if (listaExiste.get(i).getTipo_anuncio().equals("alquilar") && listaExiste.get(i).getInmueble().getId_inmueble() == idInmueble) {
                                            existe = false;
                                        }
                                    }
                                }

                                if (existe) {
                                    dialogInsertarAnuncio(tipoAlquilar, idInmueble);
                                } else {
                                    Toast.makeText(CrearAnuncioActivity.this,
                                            "ya ha sido creado un anuncio de este tipo y con este inmueble", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }
                });
                alertOpciones.show();
            }
        });
        recyclerViewCrearAnuncio.setAdapter(adaptadorCrearAnuncio);
    }

    public void existeLista(List<InfoAnuncio> response) {
        listaExiste = response;
    }

    private void dialogInsertarAnuncio(final String tipoAnuncio, final int idInmueble) {
        final AlertDialog.Builder alertPrecioAnuncio = new AlertDialog.Builder(CrearAnuncioActivity.this);
        alertPrecioAnuncio.setTitle("introduzca un precio");

        final EditText input = new EditText(CrearAnuncioActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertPrecioAnuncio.setView(input);
        alertPrecioAnuncio.setPositiveButton("Crear",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Anuncio nuevoAnuncio = new Anuncio();
                        double precio = Double.parseDouble(input.getText().toString());

                        nuevoAnuncio.setTipo_anuncio(tipoAnuncio);
                        nuevoAnuncio.setId_inmueble(idInmueble);
                        nuevoAnuncio.setPrecio(precio);

                        Call<Void> call = ApiAdapter.getApiService(getPref()).createAnuncio(nuevoAnuncio);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(CrearAnuncioActivity.this, "El anuncio ha sido creado.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        alertPrecioAnuncio.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }
                });
        alertPrecioAnuncio.show();
    }



    public String getPref() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }
}
