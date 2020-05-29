package com.tfg.inmobiliariatfg.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RegistrarInmuebleActivity;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.adapters.RecyclerViewInmuebleAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewMisInmueblesFragment extends Fragment {

    private RecyclerView recyclerMisInmuebles;
    private RecyclerViewInmuebleAdapter adaptadorMisInmuebles;
    private Bundle extras;
    private Button btnRegistrarInmueble;
    private Usuario usuario;
    private int idUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_recycler_view_mis_inmuebles, container, false);

        btnRegistrarInmueble = view.findViewById(R.id.btnRegistrarInmueble);
        btnRegistrarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrarInmuebleActivity.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        recyclerMisInmuebles = view.findViewById(R.id.recyclerMisInmuebles);

        recyclerMisInmuebles.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        obtenerInmuebles();

        return view;
    }

    public void obtenerInmuebles() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Accediendo a tus inmuebles...");

        Call<List<Inmueble>> listCall = ApiAdapter.getApiService(getPref()).getInmueblePropietario(idUsuario);
        listCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    ShowIt(response.body());
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), "No tienes inmuebles todavia.", Toast.LENGTH_LONG);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void ShowIt(final List<Inmueble> response){
        List<Inmueble> inmueblesLista = response;
        adaptadorMisInmuebles = new RecyclerViewInmuebleAdapter(getContext(), inmueblesLista);
        recyclerMisInmuebles.setAdapter(adaptadorMisInmuebles);

        setImagenes(inmueblesLista);
    }

    private void setImagenes(List<Inmueble> inmueblesLista) {

        for(int i = 0; i < inmueblesLista.size(); i++){
            int idInmueble = inmueblesLista.get(i).getId_inmueble();
            ArrayList<Integer> listUriImages = new ArrayList<>();
            //falta llamada a servidor donde cogera las fotos de cada inmuble mediante su id.
            // for del response
            for(int j = 0; j <=5; j++){

                listUriImages.add(R.drawable.ic_sin_imagen);
            }
            //salida del for
            inmueblesLista.get(i).setImagenesInmueble(listUriImages);
        }
        adaptadorMisInmuebles.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        obtenerInmuebles();
    }

    public String getPref() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.baseURL);
        String baseURL = sharedPref.getString(getString(R.string.baseURL), defaultValue);

        return baseURL;
    }

}
