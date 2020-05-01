package com.tfg.inmobiliariatfg.utiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBusquedaCalle, tvBusquedaFechIng, tvBusquedaPrecio, tvBusquedaHab, tvBusquedaMetros2, tvBusquedaPiso;
        private ImageView ivBusquedaImagen;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBusquedaCalle = (TextView) itemView.findViewById(R.id.tvBusquedaCalle);
            tvBusquedaFechIng = (TextView) itemView.findViewById(R.id.tvBusquedaFechIng);
            tvBusquedaPrecio = (TextView) itemView.findViewById(R.id.tvBusquedaPrecio);
            tvBusquedaHab = (TextView) itemView.findViewById(R.id.tvBusquedaHab);
            tvBusquedaMetros2 = (TextView) itemView.findViewById(R.id.tvBusquedaMetros2);
            tvBusquedaPiso = (TextView) itemView.findViewById(R.id.tvBusquedaPiso);
            /*ivBusquedaImagen = (ImageView) itemView.findViewById(R.id.ivBusquedaImagen);*/
        }
    }

    public List<InfoAnuncio> AnuncioLista;

    //Falta añadir el paso de las fotos al arrayAdapter
    public RecyclerViewAdapter(List<InfoAnuncio> AnuncioLista) {
        this.AnuncioLista = AnuncioLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_anuncio, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBusquedaCalle.setText(AnuncioLista.get(position).getInmueble().getCalle());
        holder.tvBusquedaFechIng.setText(String.valueOf(AnuncioLista.get(position).getFeha_anunciado()));
        if (AnuncioLista.get(position).getTipo_anuncio().equalsIgnoreCase("vender")) {
            holder.tvBusquedaPrecio.setText(AnuncioLista.get(position).getPrecio() + " €");
        } else {
            holder.tvBusquedaPrecio.setText(AnuncioLista.get(position).getPrecio() + " €/mes");
        }
        holder.tvBusquedaHab.setText(AnuncioLista.get(position).getInmueble().getNum_habitaciones() + " habitaciones");
        holder.tvBusquedaMetros2.setText(AnuncioLista.get(position).getInmueble().getMetros2() + " /m²");
        holder.tvBusquedaPiso.setText(AnuncioLista.get(position).getInmueble().getPiso() + "º planta");
    }

    @Override
    public int getItemCount() {

        return AnuncioLista.size();
    }
}
