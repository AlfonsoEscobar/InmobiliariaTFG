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

public class RecyclerViewInfoAnuncioAdapter extends RecyclerView.Adapter<RecyclerViewInfoAnuncioAdapter.ViewHolderAnuncios> implements View.OnClickListener {

    public List<InfoAnuncio> AnuncioLista;
    private View.OnClickListener listener;

    public static class ViewHolderAnuncios extends RecyclerView.ViewHolder {

        private TextView tvBusquedaCalle, tvBusquedaFechIng, tvBusquedaPrecio, tvBusquedaHab, tvBusquedaMetros2, tvBusquedaPiso;
        private ImageView ivBusquedaImagen;

        public ViewHolderAnuncios(View itemView) {
            super(itemView);

            tvBusquedaCalle = itemView.findViewById(R.id.tvBusquedaCalle);
            tvBusquedaFechIng = itemView.findViewById(R.id.tvBusquedaFechIng);
            tvBusquedaPrecio = itemView.findViewById(R.id.tvBusquedaPrecio);
            tvBusquedaHab = itemView.findViewById(R.id.tvBusquedaHab);
            tvBusquedaMetros2 = itemView.findViewById(R.id.tvBusquedaMetros2);
            tvBusquedaPiso = itemView.findViewById(R.id.tvBusquedaPiso);
            /*ivBusquedaImagen = itemView.findViewById(R.id.ivBusquedaImagen);*/
        }
    }


    //Falta añadir el paso de las fotos al arrayAdapter
    public RecyclerViewInfoAnuncioAdapter(List<InfoAnuncio> AnuncioLista) {
        this.AnuncioLista = AnuncioLista;
    }

    @NonNull
    @Override
    public ViewHolderAnuncios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_anuncio, parent, false);
        ViewHolderAnuncios viewHolder = new ViewHolderAnuncios(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnuncios holder, int position) {
        holder.tvBusquedaCalle.setText(AnuncioLista.get(position).getInmueble().getCalle());
        holder.tvBusquedaFechIng.setText(String.valueOf(AnuncioLista.get(position).getFeha_anunciado()));
        if (AnuncioLista.get(position).getTipo_anuncio().equalsIgnoreCase("venta")) {
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

    public void setOnClicklistener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }
}
