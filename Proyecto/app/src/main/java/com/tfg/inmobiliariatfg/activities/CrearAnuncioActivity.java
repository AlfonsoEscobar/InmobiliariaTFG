package com.tfg.inmobiliariatfg.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.tfg.inmobiliariatfg.utiles.RecyclerViewCrearAnuncioAdapter;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearAnuncioActivity extends AppCompatActivity {
    private List<InfoAnuncio> listaExiste;
    private RecyclerView recyclerViewCrearAnuncio;
    private RecyclerViewCrearAnuncioAdapter adaptadorCrearAnuncio;
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(getApplicationContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Procesando...");
        Call<List<Inmueble>> listCall = ApiAdapter.getApiService().getInmueblePropietario(idUsuario);
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
                final String localidadInmueble = inmueble.getLocalidad();
                final CharSequence[] opciones = {"Vender", "Alquilar", "Cancelar"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getApplicationContext());
                alertOpciones.setTitle("Seleccione una opción");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("Vender")) {
                            String tipoVender = "comprar";
                            callComprobarAnuncioExistente(localidadInmueble, tipoVender);
                            if (listaExiste != null) {
                                dialogInsertarAnuncio(tipoVender);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "ya ha sido creado un anuncio de este tipo y con este inmueble", Toast.LENGTH_LONG);
                            }
                        } else {
                            if (opciones[which].equals("Alquilar")) {
                                String tipoComprar = "alquilar";
                                callComprobarAnuncioExistente(localidadInmueble, tipoComprar);
                                if (listaExiste != null) {
                                    dialogInsertarAnuncio(tipoComprar);
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "ya ha sido creado un anuncio de este tipo y con este inmueble", Toast.LENGTH_LONG);
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

    private void callComprobarAnuncioExistente(String localidad, String tipoAnuncio) {
        progressDialog = new ProgressDialog(getApplicationContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Procesando...");
        Call<List<InfoAnuncio>> listCall = ApiAdapter.getApiService().getAnuncioLocalidad(tipoAnuncio, localidad);
        listCall.enqueue(new Callback<List<InfoAnuncio>>() {
            @Override
            public void onResponse(Call<List<InfoAnuncio>> call, Response<List<InfoAnuncio>> response) {
                if (response.isSuccessful()) {
                    listaExiste = response.body();
                } else {
                    listaExiste = null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<InfoAnuncio>> call, Throwable t) {

            }
        });
    }

    private void dialogInsertarAnuncio(final String tipoAnuncio) {
        final AlertDialog.Builder alertPrecioAnuncio = new AlertDialog.Builder(getApplicationContext());
        alertPrecioAnuncio.setTitle("introduzca un precio");

        final EditText input = new EditText(getApplicationContext());
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
                        double precio = Integer.parseInt(input.getText().toString());
                        Date fechaCreacion = new Date();

                        nuevoAnuncio.setTipo_anuncio(tipoAnuncio);
                        nuevoAnuncio.setPrecio(precio);
                        nuevoAnuncio.setFecha_anuncio(fechaCreacion);
                        nuevoAnuncio.setFecha_ultima_actualizacion(fechaCreacion);

                        Call<Void> call = ApiAdapter.getApiService().createAnuncio(nuevoAnuncio);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext(), "El anuncio ha sido creado.", Toast.LENGTH_LONG);
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
    }

}
