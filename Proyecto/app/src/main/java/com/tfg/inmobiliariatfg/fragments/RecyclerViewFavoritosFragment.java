package com.tfg.inmobiliariatfg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.utiles.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFavoritosFragment extends Fragment {

    private RecyclerView recyclerViewBusqueda;
    private RecyclerViewAdapter adaptadorBusqueda;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view_favoritos, container, false);

        recyclerViewBusqueda = (RecyclerView) view.findViewById(R.id.recyclerFavorito);
        recyclerViewBusqueda.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptadorBusqueda = new RecyclerViewAdapter(ObtenerAnuncios());
        recyclerViewBusqueda.setAdapter(adaptadorBusqueda);
        return view;


    }

    public List<InfoAnuncio> ObtenerAnuncios() {
        List<InfoAnuncio> Anuncio = new ArrayList<>();
        // Aqui recibira los datos y los añadira al arrayList.
        return Anuncio;
    }
}
