package com.tfg.inmobiliariatfg.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.ValoresBusqueda;
import com.tfg.inmobiliariatfg.modelos.ValoresPredeterminadosInmueble;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltrosBusquedaFragment extends Fragment {
    private EditText etLocalidadFiltros, etCalleFiltros, etPisoFiltros, etMinMetrosFiltros,
            etMaxMetrosFiltros, etMinHabFiltros, etNumHabFiltros, etNumBanosFiltros,
            etMinBanosFiltros, etMinPrecioFiltros, etMaxPrecioFiltros;
    private RadioButton rbComprarFiltros, rbAlquilarFiltros;
    private Spinner sTipoEdificacionFiltros, sTipoObraFiltros, sExterioresFiltros;
    private RadioButton rbNoGarajeFiltros,rbSiGarajeFiltros ,rbIgualGarajeFiltros, rbIgualTrasteroFiltros,
            rbSiTrasteroFiltros, rbNoTrasteroFiltros, rbIgualAscensorFiltros, rbSiAscensorFiltros, rbNoAscensorFiltros,
            rbIgualUltPlantFiltros, rbSiUltPlantFiltros, rbNoUltPlantFiltros, rbIgualMascotasFiltros, rbSiMascotasFiltros,
            rbNoMascotasFiltros;
    private Bundle bArguments;
    private String localidad, tipo;
    private Button btnConfirmarFiltros;

    private ArrayAdapter<String> adapterExteriores;
    private ArrayAdapter<String> adapterTipoEdificacion;
    private ArrayAdapter<String> adapterTipoObra;

    ValoresBusqueda valoresBusqueda;
    Activity activity;

    public interface FragmentCallback {
        void actualizarActividad(ValoresBusqueda valoresBusqueda);
    }

    FragmentCallback mCallback;

    public void setInterfaz(FragmentCallback interfaz) {
        this.mCallback = interfaz;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_filtros_busqueda, container, false);
        etLocalidadFiltros = cl.findViewById(R.id.etLocalidadFiltros);
        etCalleFiltros = cl.findViewById(R.id.etCalleFiltros);
        etPisoFiltros = cl.findViewById(R.id.etPisoFiltros);
        etMinMetrosFiltros = cl.findViewById(R.id.etMinMetrosFiltros);
        etMaxMetrosFiltros = cl.findViewById(R.id.etMaxMetrosFiltros);
        etNumHabFiltros = cl.findViewById(R.id.etNumHabFiltros);
        etMinHabFiltros = cl.findViewById(R.id.etMinHabFiltros);
        etNumBanosFiltros = cl.findViewById(R.id.etNumBanosFiltros);
        etMinBanosFiltros = cl.findViewById(R.id.etMinBanosFiltros);
        etMinPrecioFiltros = cl.findViewById(R.id.etMinPrecioFiltros);
        etMaxPrecioFiltros = cl.findViewById(R.id.etMaxPrecioFiltros);
        rbComprarFiltros = cl.findViewById(R.id.rbComprarFiltros);
        rbAlquilarFiltros = cl.findViewById(R.id.rbAlquilarFiltros);
        sTipoEdificacionFiltros = cl.findViewById(R.id.sTipoEdificacionFiltros);
        sTipoObraFiltros = cl.findViewById(R.id.sTipoObraFiltros);
        sExterioresFiltros = cl.findViewById(R.id.sExterioresFiltros);
        rbNoGarajeFiltros = cl.findViewById(R.id.rbNoGarajeFiltros);
        rbSiGarajeFiltros = cl.findViewById(R.id.rbSiGarajeFiltros);
        rbIgualGarajeFiltros = cl.findViewById(R.id.rbIgualGarajeFiltros);
        rbNoTrasteroFiltros = cl.findViewById(R.id.rbNoTrasteroFiltros);
        rbIgualTrasteroFiltros = cl.findViewById(R.id.rbIgualTrasteroFiltros);
        rbSiTrasteroFiltros = cl.findViewById(R.id.rbSiTrasteroFiltros);
        rbIgualAscensorFiltros = cl.findViewById(R.id.rbIgualAscensorFiltros);
        rbSiAscensorFiltros = cl.findViewById(R.id.rbSiAscensorFiltros);
        rbNoAscensorFiltros = cl.findViewById(R.id.rbNoAscensorFiltros);
        rbIgualUltPlantFiltros = cl.findViewById(R.id.rbIgualUltPlantFiltros);
        rbSiUltPlantFiltros = cl.findViewById(R.id.rbSiUltPlantFiltros);
        rbNoUltPlantFiltros = cl.findViewById(R.id.rbNoUltPlantFiltros);
        rbIgualMascotasFiltros = cl.findViewById(R.id.rbIgualMascotasFiltros);
        rbSiMascotasFiltros = cl.findViewById(R.id.rbSiMascotasFiltros);
        rbNoMascotasFiltros = cl.findViewById(R.id.rbNoMascotasFiltros);
        btnConfirmarFiltros = cl.findViewById(R.id.btnConfirmarFiltros);
        btnConfirmarFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valoresBusqueda = guardarFiltros();
                mCallback.actualizarActividad(valoresBusqueda);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.clBusqueda))
                        .commit();
            }
        });

        bArguments = getArguments();
        if (bArguments != null) {
            tipo = bArguments.getString("tipoAnuncio");
            localidad = bArguments.getString("localidad");

            etLocalidadFiltros.setText(localidad);
            if (tipo.equals("comprar")) {
                rbComprarFiltros.setChecked(true);
            }

            if (tipo.equals("alquilar")) {
                rbAlquilarFiltros.setChecked(true);
            }
        }

        callSpinners();


        return cl;
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void callSpinners() {
        Call<ValoresPredeterminadosInmueble> spinnerCall = ApiAdapter.getApiService(getPref()).getSpinnersRegistrarInmueble();
        spinnerCall.enqueue(new Callback<ValoresPredeterminadosInmueble>() {
            @Override
            public void onResponse(Call<ValoresPredeterminadosInmueble> call, Response<ValoresPredeterminadosInmueble> response) {
                final ValoresPredeterminadosInmueble valoresSpinners = response.body();
                cargarSpinners(valoresSpinners);
            }

            @Override
            public void onFailure(Call<ValoresPredeterminadosInmueble> call, Throwable t) {

            }
        });

    }

    private void cargarSpinners(ValoresPredeterminadosInmueble valoresSpinners) {
        if (valoresSpinners != null) {
            List<String> listaExteriores = new LinkedList<>();
            List<String> listaTipoEdificacion = new LinkedList<>();
            List<String> listaTipoObra = new LinkedList<>();
            listaExteriores.add("Seleccione:");
            listaTipoEdificacion.add("Seleccione:");
            listaTipoObra.add("Seleccione:");
            listaExteriores.addAll(valoresSpinners.getListaExteriores());
            listaTipoEdificacion.addAll(valoresSpinners.getListaTipoEdificacion());
            listaTipoObra.addAll(valoresSpinners.getListaTipoObra());

            adapterExteriores = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaExteriores);
            adapterExteriores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTipoEdificacion = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaTipoEdificacion);
            adapterTipoEdificacion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapterTipoObra = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaTipoObra);
            adapterTipoObra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sExterioresFiltros.setAdapter(adapterExteriores);
            sTipoEdificacionFiltros.setAdapter(adapterTipoEdificacion);
            sTipoObraFiltros.setAdapter(adapterTipoObra);
        }

    }


    public ValoresBusqueda guardarFiltros() {
        ValoresBusqueda valoresBusqueda = new ValoresBusqueda();
        if(!etLocalidadFiltros.getText().toString().equals("")) {
            valoresBusqueda.setLocalidad(etLocalidadFiltros.getText().toString());
        }else {
            valoresBusqueda.setLocalidad(localidad);
        }
        if(rbAlquilarFiltros.isChecked()) {
            valoresBusqueda.setTipo_anuncio(rbAlquilarFiltros.getText().toString());
        }
        if(rbComprarFiltros.isChecked()){
            valoresBusqueda.setTipo_anuncio(rbComprarFiltros.getText().toString());
        }
        if(!etCalleFiltros.getText().toString().equals("")) {
            valoresBusqueda.setCalle(etCalleFiltros.getText().toString());
        }

        if (!etPisoFiltros.getText().toString().equals("")) {
            valoresBusqueda.setPiso(Integer.parseInt(etPisoFiltros.getText().toString()));
        }
        if (!etMinMetrosFiltros.getText().toString().equals("")) {
            valoresBusqueda.setMin_metros2(Integer.parseInt(etMinMetrosFiltros.getText().toString()));
        }
        if (!etMaxMetrosFiltros.getText().toString().equals("")) {
            valoresBusqueda.setMax_metros2(Integer.parseInt(etMaxMetrosFiltros.getText().toString()));
        }
        if (!etNumBanosFiltros.getText().toString().equals("")) {
            valoresBusqueda.setNum_banos(Integer.parseInt(etNumBanosFiltros.getText().toString()));
        }
        if (!etMinBanosFiltros.getText().toString().equals("")) {
            valoresBusqueda.setMin_num_banos(Integer.parseInt(etMinBanosFiltros.getText().toString()));
        }
        if (!etMinHabFiltros.getText().toString().equals("")) {
            valoresBusqueda.setMin_num_habitaciones(Integer.parseInt(etMinHabFiltros.getText().toString()));
        }
        if (!etNumHabFiltros.getText().toString().equals("")) {
            valoresBusqueda.setNum_habitaciones(Integer.parseInt(etNumHabFiltros.getText().toString()));
        }
        if (!sTipoEdificacionFiltros.getSelectedItem().toString().equals("Seleccione:")) {
            valoresBusqueda.setTipo_edificacion(sTipoEdificacionFiltros.getSelectedItem().toString());
        }
        if (!sTipoObraFiltros.getSelectedItem().toString().equals("Seleccione:")) {
            valoresBusqueda.setTipo_obra(sTipoObraFiltros.getSelectedItem().toString());
        }
        if (!sExterioresFiltros.getSelectedItem().toString().equals("Seleccione:")) {
            valoresBusqueda.setExteriores(sExterioresFiltros.getSelectedItem().toString());
        }
        if (rbSiGarajeFiltros.isChecked()) {
            valoresBusqueda.setGaraje("true");
        } else if(rbSiGarajeFiltros.isChecked()) {
            valoresBusqueda.setGaraje("false");
        }else if(rbIgualGarajeFiltros.isChecked()){
            valoresBusqueda.setGaraje(null);
        }
        if (rbSiTrasteroFiltros.isChecked()) {
            valoresBusqueda.setTrastero("true");
        } else if(rbNoTrasteroFiltros.isChecked()) {
            valoresBusqueda.setTrastero("false");
        }else if(rbIgualTrasteroFiltros.isChecked()){
            valoresBusqueda.setTrastero(null);
        }
        if (rbSiAscensorFiltros.isChecked()) {
            valoresBusqueda.setAscensor("true");
        } else if(rbNoAscensorFiltros.isChecked()) {
            valoresBusqueda.setAscensor("false");
        }else if(rbIgualAscensorFiltros.isChecked()){
            valoresBusqueda.setAscensor(null);
        }
        if (rbSiUltPlantFiltros.isChecked()) {
            valoresBusqueda.setUltima_planta("true");
        } else if(rbNoUltPlantFiltros.isChecked()) {
            valoresBusqueda.setUltima_planta("false");
        }else if(rbIgualUltPlantFiltros.isChecked()){
            valoresBusqueda.setUltima_planta(null);
        }
        if (rbSiMascotasFiltros.isChecked()) {
            valoresBusqueda.setMascotas("true");
        } else if(rbNoMascotasFiltros.isChecked()) {
            valoresBusqueda.setMascotas("false");
        }else if(rbIgualMascotasFiltros.isChecked()){
            valoresBusqueda.setMascotas(null);
        }
        return valoresBusqueda;
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("rutaURL",Context.MODE_PRIVATE);
        String baseURL = sharedPref.getString("baseUrl","https://34af4e85d798.ngrok.io/Restful_Inmo/servicios/");
        return baseURL;
    }
}
