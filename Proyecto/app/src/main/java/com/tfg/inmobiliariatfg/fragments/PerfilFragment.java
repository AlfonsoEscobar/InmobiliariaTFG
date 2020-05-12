package com.tfg.inmobiliariatfg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Usuario;

public class PerfilFragment extends Fragment {

    TextView tvNomPerfil, tvCorreoPerfil, tvTelPerfil;
    ImageView ivPerfil;
    Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_perfil, container, false);

        Bundle datos = getArguments();

        tvNomPerfil = cl.findViewById(R.id.tvNomPerfil);
        tvCorreoPerfil = cl.findViewById(R.id.tvCorreoPerfil);
        tvTelPerfil = cl.findViewById(R.id.tvTelPerfil);
        ivPerfil = cl.findViewById(R.id.ivPerfil);

        if (datos != null) {
            usuario = (Usuario) datos.getSerializable("usuario");
            tvNomPerfil.setText(usuario.getNombre());
            tvCorreoPerfil.setText(usuario.getCorreo());
            tvTelPerfil.setText(usuario.getTelefono1() + "\n " + usuario.getTelefono2());
        }
        return cl;
    }
}
