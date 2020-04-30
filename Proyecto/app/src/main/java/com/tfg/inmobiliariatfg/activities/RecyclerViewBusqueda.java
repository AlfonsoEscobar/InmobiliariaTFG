package com.tfg.inmobiliariatfg.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tfg.inmobiliariatfg.utiles.RecyclerViewAdapter;
import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBusqueda extends AppCompatActivity {

    private RecyclerView recyclerViewBusqueda;
    private RecyclerViewAdapter adaptadorBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_busqueda);

        recyclerViewBusqueda = (RecyclerView) findViewById(R.id.recyclerBusqueda);
        recyclerViewBusqueda.setLayoutManager(new LinearLayoutManager(this));

        adaptadorBusqueda = new RecyclerViewAdapter(ObtenerAnuncios());
        recyclerViewBusqueda.setAdapter(adaptadorBusqueda);
    }

    public List<InfoAnuncio> ObtenerAnuncios(){
        List<InfoAnuncio> Anuncio = new ArrayList<>();
        // Aqui recibira los datos y los añadira al arrayList.
        return Anuncio;
    }
}
