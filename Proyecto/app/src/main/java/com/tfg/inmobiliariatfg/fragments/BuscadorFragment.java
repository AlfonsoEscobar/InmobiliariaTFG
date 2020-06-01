package com.tfg.inmobiliariatfg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RecyclerViewBusquedaActivity;
import com.tfg.inmobiliariatfg.modelos.Usuario;

public class BuscadorFragment extends Fragment {

    private Bundle datos;
    private Usuario usuario;
    private int idUsuario;
    private EditText etLocalidadBuscador;
    private ImageView ivBuscador;
    private RadioButton rbComprarBuscador, rbAlquilarBuscador;
    private Button btnBuscarBuscador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_buscador, container, false);

        etLocalidadBuscador = cl.findViewById(R.id.etLocalidadBuscador);
        ivBuscador = cl.findViewById(R.id.ivBuscador);
        rbComprarBuscador = cl.findViewById(R.id.rbComprarBuscador);
        rbAlquilarBuscador = cl.findViewById(R.id.rbAlquilerBuscador);
        btnBuscarBuscador = cl.findViewById(R.id.btnBuscarBuscador);

        btnBuscarBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RecyclerViewBusquedaActivity.class);
                String localidad = etLocalidadBuscador.getText().toString();
                if (localidad.equals("")) {
                    Log.i("Error", "No ha sido introducido una localidad");
                } else {
                    String localidadPut = etLocalidadBuscador.getText().toString();
                    String tipoPut;
                    i.putExtra("idUsuario",idUsuario);
                    i.putExtra("localidad", localidadPut);
                    if (rbAlquilarBuscador.isChecked()) {
                        tipoPut = "alquilar";
                        i.putExtra("tipo", tipoPut);
                    } else {
                        tipoPut = "comprar";
                        i.putExtra("tipo", tipoPut);
                    }
                    etLocalidadBuscador.setText("");
                    etLocalidadBuscador.setHint("Localidad");
                    rbComprarBuscador.isChecked();
                    startActivity(i);
                }
            }
        });
        datos = getArguments();
        if(datos != null){
            usuario = (Usuario) datos.getSerializable("usuario");
            idUsuario = usuario.getId_usuario();
        }
        return cl;
    }
}
