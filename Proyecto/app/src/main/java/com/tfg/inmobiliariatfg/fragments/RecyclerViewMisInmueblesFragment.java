package com.tfg.inmobiliariatfg.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.AnuncioCompletoActivity;
import com.tfg.inmobiliariatfg.activities.RegistrarInmuebleActivity;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInfoAnuncioAdapter;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewInmuebleAdapter;

import java.util.List;

import kotlin.reflect.KCallable;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfg.inmobiliariatfg.R.id.image;
import static com.tfg.inmobiliariatfg.R.id.recyclerMisInmuebles;

public class RecyclerViewMisInmueblesFragment extends Fragment {

    private static final int NUEVO_INMUEBLE = 10;
    private static final int MODIFICAR_INMUEBLE = 20;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerMisInmuebles;
    private RecyclerViewInmuebleAdapter adaptadorMisInmuebles;
    private Bundle extras;
    Button btnRegistrarInmueble;
    Usuario usuario;
    int idUsuario;

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
                startActivityForResult(intent, NUEVO_INMUEBLE);
            }
        });
        recyclerMisInmuebles = view.findViewById(R.id.recyclerMisInmuebles);
        recyclerMisInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));

        extras = getArguments();
        if (extras != null) {
            usuario = (Usuario) extras.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }

        ObtenerInmuebles();

        return view;
    }

    public void ObtenerInmuebles() {

        progressDialog = new ProgressDialog(getContext());
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Accediendo a tus inmuebles...");

        Call<List<Inmueble>> listCall = ApiAdapter.getApiService().getInmueblePropietario(idUsuario);
        listCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    ShowIt(response.body());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });

    }

    private void ShowIt(final List<Inmueble> response){
        adaptadorMisInmuebles = new RecyclerViewInmuebleAdapter(response);
        adaptadorMisInmuebles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            cargarDialog(response, view);
            }
        });
        recyclerMisInmuebles.setAdapter(adaptadorMisInmuebles);
    }

    private void cargarDialog(List<Inmueble> response, View view) {
        final Inmueble inmueble = response.get(recyclerMisInmuebles.getChildAdapterPosition(view));
        final CharSequence[] opciones = {"Modificar Inmueble", "Eliminar Inmueble", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Modificar Inmueble")) {
                    Intent intent = new Intent(getContext(), RegistrarInmuebleActivity.class);
                    intent.putExtra("inmueble", inmueble);
                    startActivityForResult(intent,MODIFICAR_INMUEBLE);

                } else {
                    if (opciones[which].equals("Eliminar Inmueble")) {
                        progressDialog = new ProgressDialog(getContext());
                        Metodos.mostrarDialogo(progressDialog);
                        progressDialog.setMessage("Eliminando inmueble...");
                        Metodos.mostrarDialogo(progressDialog);
                        Call<Void> call = ApiAdapter.getApiService().eliminarInmueble(inmueble.getId_inmueble());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code() == 200){
                                    Toast.makeText(getContext(),
                                            "Inmueble Eliminado", Toast.LENGTH_LONG).show();
                                    ObtenerInmuebles();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NUEVO_INMUEBLE) {
            ObtenerInmuebles();
        }

        if (requestCode == MODIFICAR_INMUEBLE){
            ObtenerInmuebles();
        }
    }
}
