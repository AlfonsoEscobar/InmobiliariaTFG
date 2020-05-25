package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.fragments.RecyclerViewMisInmueblesFragment;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.ValoresPredeterminadosInmueble;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarInmuebleActivity extends AppCompatActivity {

    EditText etProvinciaRegistrarInmueble, etLocalidadRegistrarInmueble, etCalleRegistrarInmueble, etNumeroRegistrarInmueble, etPisoRegistrarInmueble,
            etPuertaRegistrarInmueble, etDescripcionRegistrarInmueble, etMetros2RegistrarInmueble, etNumHabRegistrarInmueble, etNumBanosRegistrarInmueble,
            etEquipamientoRegistrarInmueble;
    Spinner sEscaleraRegistrarInmueble, sTipoObraRegistrarInmueble, sTipoEdificacionRegistrarInmueble, sExterioresRegistrarInmueble;
    CheckBox cbGarajeRegistrarInmueble, cbTrasteroRegistrarInmueble, cbAscensorRegistrarInmueble, cbUltPlantaRegistrarInmueble, cbMascotasRegistrarInmueble;
    Button btnConfirmarRegistrarInmueble;

    Bundle datos;
    Inmueble inmueble, inmuebleModificar;
    int idUsario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_inmueble);

        etProvinciaRegistrarInmueble = findViewById(R.id.etProvinciaRegistrarInmueble);
        etLocalidadRegistrarInmueble = findViewById(R.id.etLocalidadRegistrarInmueble);
        etCalleRegistrarInmueble = findViewById(R.id.etCalleRegistrarInmueble);
        etNumeroRegistrarInmueble = findViewById(R.id.etNumeroRegistrarInmueble);
        etPisoRegistrarInmueble = findViewById(R.id.etPisoRegistrarInmueble);
        etPuertaRegistrarInmueble = findViewById(R.id.etPuertaRegistrarInmueble);
        etDescripcionRegistrarInmueble = findViewById(R.id.etDescripcionRegistrarInmueble);
        etMetros2RegistrarInmueble = findViewById(R.id.etMetros2RegistrarInmueble);
        etNumHabRegistrarInmueble = findViewById(R.id.etNumHabRegistrarInmueble);
        etNumBanosRegistrarInmueble = findViewById(R.id.etNumBanosRegistrarInmueble);
        etEquipamientoRegistrarInmueble = findViewById(R.id.etEquipamientoRegistrarInmueble);
        sEscaleraRegistrarInmueble = findViewById(R.id.sEscaleraRegistrarInmueble);
        sTipoObraRegistrarInmueble = findViewById(R.id.sTipoObraRegistrarInmueble);
        sTipoEdificacionRegistrarInmueble = findViewById(R.id.sTipoEdificacionRegistrarInmueble);
        sExterioresRegistrarInmueble = findViewById(R.id.sExterioresRegistrarInmueble);
        cbGarajeRegistrarInmueble = findViewById(R.id.cbGarajeRegistrarInmueble);
        cbTrasteroRegistrarInmueble = findViewById(R.id.cbTrasteroRegistrarInmueble);
        cbAscensorRegistrarInmueble = findViewById(R.id.cbAscensorRegistrarInmueble);
        cbUltPlantaRegistrarInmueble = findViewById(R.id.cbUltPlantaRegistrarInmueble);
        cbMascotasRegistrarInmueble = findViewById(R.id.cbMascotasRegistrarInmueble);
        btnConfirmarRegistrarInmueble = findViewById(R.id.btnConfirmarRegistrarInmueble);

        getDatosSpinners();

        datos = getIntent().getExtras();
        if (datos != null) {
            idUsario = datos.getInt("idUsuario");
            if (datos.getSerializable("inmueble") != null) {
                inmuebleModificar = (Inmueble) datos.getSerializable("inmueble");

            }
        }
    }

    public void getDatosSpinners() {
        Call<ValoresPredeterminadosInmueble> call = ApiAdapter.getApiService().getSpinnersRegistrarInmueble();
        call.enqueue(new Callback<ValoresPredeterminadosInmueble>() {
            @Override
            public void onResponse(Call<ValoresPredeterminadosInmueble> call, Response<ValoresPredeterminadosInmueble> response) {

                if (response.isSuccessful()) {
                    ValoresPredeterminadosInmueble valoresSpinners = response.body();

                    List<String> listaEscalera = valoresSpinners.getListaEscalera();
                    List<String> listaExteriores = valoresSpinners.getListaExteriores();
                    List<String> listaTipoEdificacion = valoresSpinners.getListaTipoEdificacion();
                    List<String> listaTipoObra = valoresSpinners.getListaTipoObra();
                    //Para indicar el tipo de foto a subir
                    List<String> listaTipoHabitacion = valoresSpinners.getListaTipoHabitacion();

                    ArrayAdapter<String> adapterEscalera = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaEscalera);
                    ArrayAdapter<String> adapterExteriores = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaExteriores);
                    ArrayAdapter<String> adapterTipoEdificacion = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaTipoEdificacion);
                    ArrayAdapter<String> adapterTipoObra = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaTipoObra);

                    sEscaleraRegistrarInmueble.setAdapter(adapterEscalera);
                    sExterioresRegistrarInmueble.setAdapter(adapterExteriores);
                    sTipoEdificacionRegistrarInmueble.setAdapter(adapterTipoEdificacion);
                    sTipoObraRegistrarInmueble.setAdapter(adapterTipoObra);

                }
            }

            @Override
            public void onFailure(Call<ValoresPredeterminadosInmueble> call, Throwable t) {

            }
        });
    }

    public void OnClickRegistrarInmueble(View v) {
        if (etProvinciaRegistrarInmueble.getText().toString().equals("") || etLocalidadRegistrarInmueble.getText().toString().equals("") || etCalleRegistrarInmueble.getText().toString().equals("")
                || etNumeroRegistrarInmueble.getText().toString().equals("") || etPisoRegistrarInmueble.getText().toString().equals("") || etPuertaRegistrarInmueble.getText().toString().equals("")
                || etDescripcionRegistrarInmueble.getText().toString().equals("") || etMetros2RegistrarInmueble.getText().toString().equals("") || etNumHabRegistrarInmueble.getText().toString().equals("")
                || etNumBanosRegistrarInmueble.getText().toString().equals("") || etEquipamientoRegistrarInmueble.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "Rellena todos los campos por favor", Toast.LENGTH_LONG).show();

        } else {
            inmueble = new Inmueble();
            inmueble.setProvincia(etProvinciaRegistrarInmueble.getText().toString());
            inmueble.setLocalidad(etLocalidadRegistrarInmueble.getText().toString());
            inmueble.setCalle(etCalleRegistrarInmueble.getText().toString());
            inmueble.setNumero(Integer.parseInt(etNumeroRegistrarInmueble.getText().toString()));
            inmueble.setPiso(Integer.parseInt(etPisoRegistrarInmueble.getText().toString()));
            inmueble.setPuerta(etPuertaRegistrarInmueble.getText().toString());
            inmueble.setDescripcion(etDescripcionRegistrarInmueble.getText().toString());
            inmueble.setMetros2(Integer.parseInt(etMetros2RegistrarInmueble.getText().toString()));
            inmueble.setNum_habitaciones(Integer.parseInt(etNumHabRegistrarInmueble.getText().toString()));
            inmueble.setNum_banos(Integer.parseInt(etNumBanosRegistrarInmueble.getText().toString()));
            inmueble.setEquipamiento(etEquipamientoRegistrarInmueble.getText().toString());
            inmueble.setEscalera(sEscaleraRegistrarInmueble.getSelectedItem().toString());
            inmueble.setExteriores(sExterioresRegistrarInmueble.getSelectedItem().toString());
            inmueble.setTipo_edificacion(sTipoEdificacionRegistrarInmueble.getSelectedItem().toString());
            inmueble.setTipo_obra(sTipoObraRegistrarInmueble.getSelectedItem().toString());
            if (cbGarajeRegistrarInmueble.isChecked()) {
                inmueble.setGaraje(true);
            } else {
                inmueble.setGaraje(false);
            }
            if (cbTrasteroRegistrarInmueble.isChecked()) {
                inmueble.setTrastero(true);
            } else {
                inmueble.setTrastero(false);
            }
            if (cbAscensorRegistrarInmueble.isChecked()) {
                inmueble.setAscensor(true);
            } else {
                inmueble.setAscensor(false);
            }
            if (cbUltPlantaRegistrarInmueble.isChecked()) {
                inmueble.setUltima_planta(true);
            } else {
                inmueble.setUltima_planta(false);
            }
            if (cbMascotasRegistrarInmueble.isChecked()) {
                inmueble.setMascotas(true);
            } else {
                inmueble.setMascotas(false);
            }
            inmueble.setPropietario(idUsario);
            if (inmuebleModificar == null) {
                Call<Void> callNuevoInmueble = ApiAdapter.getApiService().createInmueble(inmueble);
                callNuevoInmueble.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 201) {
                            Toast.makeText(getApplicationContext(), "El inmueble ha sido creado correctamente", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), RecyclerViewMisInmueblesFragment.class);
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
                Call<Void> callModificarInmueble = ApiAdapter.getApiService().putModificarInmueblePerfil(inmuebleModificar.getId_inmueble(), inmueble);
                callModificarInmueble.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200){
                            Toast.makeText(getApplicationContext(), "Inmueble modificado con exito", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        }
    }
}
