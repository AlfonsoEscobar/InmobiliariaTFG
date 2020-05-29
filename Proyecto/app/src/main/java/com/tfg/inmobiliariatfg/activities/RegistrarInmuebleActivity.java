package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private EditText etProvinciaRegistrarInmueble, etLocalidadRegistrarInmueble, etCalleRegistrarInmueble, etNumeroRegistrarInmueble, etPisoRegistrarInmueble,
            etPuertaRegistrarInmueble, etDescripcionRegistrarInmueble, etMetros2RegistrarInmueble, etNumHabRegistrarInmueble, etNumBanosRegistrarInmueble,
            etEquipamientoRegistrarInmueble;
    private Spinner sEscaleraRegistrarInmueble, sTipoObraRegistrarInmueble, sTipoEdificacionRegistrarInmueble, sExterioresRegistrarInmueble;
    private CheckBox cbGarajeRegistrarInmueble, cbTrasteroRegistrarInmueble, cbAscensorRegistrarInmueble, cbUltPlantaRegistrarInmueble, cbMascotasRegistrarInmueble;
    private Button btnConfirmarRegistrarInmueble;

    private Bundle datos;
    private Inmueble inmueble, inmuebleModificar;
    private int idUsario;

    private ArrayAdapter<String> adapterEscalera;
    private ArrayAdapter<String> adapterExteriores;
    private ArrayAdapter<String> adapterTipoEdificacion;
    private ArrayAdapter<String> adapterTipoObra;

    private String valueEscalera;
    private String valueEdificacion;
    private String valueObra;
    private String valueExteriores;

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
        getDatosBundle();
    }

    public void getDatosBundle() {
        datos = getIntent().getExtras();
        if (datos != null) {
            idUsario = datos.getInt("idUsuario");
            inmuebleModificar = (Inmueble) datos.getSerializable("inmueble");
            if (inmuebleModificar != null) {
                etProvinciaRegistrarInmueble.setText(inmuebleModificar.getProvincia());
                etLocalidadRegistrarInmueble.setText(inmuebleModificar.getLocalidad());
                etCalleRegistrarInmueble.setText(inmuebleModificar.getCalle());
                etNumeroRegistrarInmueble.setText(String.valueOf(inmuebleModificar.getNumero()));
                etPisoRegistrarInmueble.setText(String.valueOf(inmuebleModificar.getPiso()));
                etPuertaRegistrarInmueble.setText(inmuebleModificar.getPuerta());
                if (!inmuebleModificar.getDescripcion().equals("")) {
                    etDescripcionRegistrarInmueble.setText(inmuebleModificar.getDescripcion());
                } else {
                    etDescripcionRegistrarInmueble.setText(R.string.descripcionInmueble);
                }
                etMetros2RegistrarInmueble.setText(String.valueOf(inmuebleModificar.getMetros2()));
                etNumHabRegistrarInmueble.setText(String.valueOf(inmuebleModificar.getNum_habitaciones()));
                etNumBanosRegistrarInmueble.setText(String.valueOf(inmuebleModificar.getNum_banos()));
                if (!inmuebleModificar.getEquipamiento().equals("")) {
                    etEquipamientoRegistrarInmueble.setText(inmuebleModificar.getEquipamiento());
                } else {
                    etEquipamientoRegistrarInmueble.setText("");
                }
                if (inmuebleModificar.isGaraje()) {
                    cbGarajeRegistrarInmueble.setChecked(true);
                }

                if (inmuebleModificar.isTrastero()) {
                    cbTrasteroRegistrarInmueble.setChecked(true);
                }

                if (inmuebleModificar.isAscensor()) {
                    cbAscensorRegistrarInmueble.setChecked(true);
                }

                if (inmuebleModificar.isUltima_planta()) {
                    cbUltPlantaRegistrarInmueble.setChecked(true);
                }

                if (inmuebleModificar.isMascotas()) {
                    cbMascotasRegistrarInmueble.setChecked(true);
                }

                valueEscalera = inmuebleModificar.getEscalera();
                valueEdificacion = inmuebleModificar.getTipo_edificacion();
                valueObra = inmuebleModificar.getTipo_obra();
                valueExteriores = inmuebleModificar.getExteriores();
            }
        }
    }

    public void getDatosSpinners() {
        Call<ValoresPredeterminadosInmueble> call = ApiAdapter.getApiService(getPref()).getSpinnersRegistrarInmueble();
        call.enqueue(new Callback<ValoresPredeterminadosInmueble>() {
            @Override
            public void onResponse(Call<ValoresPredeterminadosInmueble> call, Response<ValoresPredeterminadosInmueble> response) {

                if (response.isSuccessful()) {
                    final ValoresPredeterminadosInmueble valoresSpinners = response.body();
                    setDatosSpinner(valoresSpinners);


                }
            }

            @Override
            public void onFailure(Call<ValoresPredeterminadosInmueble> call, Throwable t) {

            }
        });
    }

    public void setDatosSpinner(final ValoresPredeterminadosInmueble valoresSpinners) {

        List<String> listaEscalera = valoresSpinners.getListaEscalera();
        List<String> listaExteriores = valoresSpinners.getListaExteriores();
        List<String> listaTipoEdificacion = valoresSpinners.getListaTipoEdificacion();
        List<String> listaTipoObra = valoresSpinners.getListaTipoObra();
        //Para indicar el tipo de foto a subir
        List<String> listaTipoHabitacion = valoresSpinners.getListaTipoHabitacion();

        adapterEscalera = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaEscalera);
        adapterEscalera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterExteriores = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaExteriores);
        adapterExteriores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTipoEdificacion = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaTipoEdificacion);
        adapterTipoEdificacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTipoObra = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listaTipoObra);
        adapterTipoObra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sEscaleraRegistrarInmueble.setAdapter(adapterEscalera);
        sExterioresRegistrarInmueble.setAdapter(adapterExteriores);
        sTipoEdificacionRegistrarInmueble.setAdapter(adapterTipoEdificacion);
        sTipoObraRegistrarInmueble.setAdapter(adapterTipoObra);

        setValoresInmuebleModificar();
    }

    public void setValoresInmuebleModificar() {
        if (inmuebleModificar != null) {
            sEscaleraRegistrarInmueble.setSelection(obtenerPosicionItem(sEscaleraRegistrarInmueble,valueEscalera));
            sTipoEdificacionRegistrarInmueble.setSelection(obtenerPosicionItem(sTipoEdificacionRegistrarInmueble,valueEdificacion));
            sTipoObraRegistrarInmueble.setSelection(obtenerPosicionItem(sTipoObraRegistrarInmueble,valueObra));
            sExterioresRegistrarInmueble.setSelection(obtenerPosicionItem(sExterioresRegistrarInmueble,valueExteriores));
        }
    }

    public static int obtenerPosicionItem(Spinner spinner, String valor) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(valor)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void OnClickRegistrarInmueble(View v) {
        if (etProvinciaRegistrarInmueble.getText().toString().equals("") || etLocalidadRegistrarInmueble.getText().toString().equals("") || etCalleRegistrarInmueble.getText().toString().equals("")
                || etNumeroRegistrarInmueble.getText().toString().equals("") || etPisoRegistrarInmueble.getText().toString().equals("") || etPuertaRegistrarInmueble.getText().toString().equals("")
                || etMetros2RegistrarInmueble.getText().toString().equals("") || etNumHabRegistrarInmueble.getText().toString().equals("") || etNumBanosRegistrarInmueble.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "Rellena todos los campos por favor", Toast.LENGTH_LONG).show();

        } else {
            inmueble = new Inmueble();
            inmueble.setPropietario(idUsario);
            inmueble.setProvincia(etProvinciaRegistrarInmueble.getText().toString());
            inmueble.setLocalidad(etLocalidadRegistrarInmueble.getText().toString());
            inmueble.setCalle(etCalleRegistrarInmueble.getText().toString());
            inmueble.setNumero(Integer.parseInt(etNumeroRegistrarInmueble.getText().toString()));
            inmueble.setPiso(Integer.parseInt(etPisoRegistrarInmueble.getText().toString()));
            inmueble.setPuerta(etPuertaRegistrarInmueble.getText().toString());
            inmueble.setDescripcion(etDescripcionRegistrarInmueble.getText().toString());
            inmueble.setMetros2(Double.parseDouble(etMetros2RegistrarInmueble.getText().toString()));
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
                Call<Void> callNuevoInmueble = ApiAdapter.getApiService(getPref()).createInmueble(inmueble);
                callNuevoInmueble.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 201) {
                            finish();
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
                Call<Void> callModificarInmueble = ApiAdapter.getApiService(getPref()).putModificarInmueble(inmuebleModificar.getId_inmueble(), inmueble);
                callModificarInmueble.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            finish();
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

    public String getPref() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }
}
