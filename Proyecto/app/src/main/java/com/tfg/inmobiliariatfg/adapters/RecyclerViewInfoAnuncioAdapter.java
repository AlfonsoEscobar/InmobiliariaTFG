package com.tfg.inmobiliariatfg.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Anuncio;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerViewInfoAnuncioAdapter extends RecyclerView.Adapter<RecyclerViewInfoAnuncioAdapter.ViewHolderAnuncios> implements View.OnClickListener {

    public List<InfoAnuncio> AnuncioLista;
    public List<Favorito> anunciosFavoritos;
    private View.OnClickListener listener;

    public static class ViewHolderAnuncios extends RecyclerView.ViewHolder {

        private TextView tvBusquedaCalle, tvBusquedaFechIng, tvBusquedaPrecio, tvBusquedaHab, tvBusquedaMetros2, tvBusquedaPiso;
        private ImageView ivPrincipal;
        ConstraintLayout clItemAnuncioDestacado;

        public ViewHolderAnuncios(View itemView) {
            super(itemView);

            clItemAnuncioDestacado = itemView.findViewById(R.id.clItemAnuncioDestacado);
            tvBusquedaCalle = itemView.findViewById(R.id.tvBusquedaCalle);
            tvBusquedaFechIng = itemView.findViewById(R.id.tvBusquedaFechIng);
            tvBusquedaPrecio = itemView.findViewById(R.id.tvBusquedaPrecio);
            tvBusquedaHab = itemView.findViewById(R.id.tvBusquedaHab);
            tvBusquedaMetros2 = itemView.findViewById(R.id.tvBusquedaMetros2);
            tvBusquedaPiso = itemView.findViewById(R.id.tvBusquedaPiso);
            ivPrincipal = itemView.findViewById(R.id.ivBusquedaImagen);
        }
    }

    public RecyclerViewInfoAnuncioAdapter(List<InfoAnuncio> AnuncioLista, List<Favorito> datosAComparar) {
        this.AnuncioLista = AnuncioLista;
        this.anunciosFavoritos = datosAComparar;
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
        if (anunciosFavoritos != null) {
            for (int x = 0; x < anunciosFavoritos.size(); x++) {
                if (AnuncioLista.get(position).getId_inmueble() == anunciosFavoritos.get(x).getInmueble_favorito() &&
                        AnuncioLista.get(position).getTipo_anuncio().equals(anunciosFavoritos.get(x).getTipo_anuncio())) {
                    holder.clItemAnuncioDestacado.setBackgroundResource(R.color.colorAccentDegradado);
                    break;
                } else {
                    holder.clItemAnuncioDestacado.setBackgroundResource(R.color.design_default_color_background);
                }
            }
        }
        holder.tvBusquedaCalle.setText(AnuncioLista.get(position).getInmueble().getCalle());
        holder.tvBusquedaFechIng.setText(new SimpleDateFormat("dd-MM-yyyy").format(AnuncioLista.get(position).getFecha_anunciado()));
        if (AnuncioLista.get(position).getTipo_anuncio().equalsIgnoreCase("comprar")) {
            holder.tvBusquedaPrecio.setText(AnuncioLista.get(position).getPrecio() + " €");
        } else {
            holder.tvBusquedaPrecio.setText(AnuncioLista.get(position).getPrecio() + " €/mes");
        }
        holder.tvBusquedaHab.setText(AnuncioLista.get(position).getInmueble().getNum_habitaciones() + " habitaciones");
        holder.tvBusquedaMetros2.setText(AnuncioLista.get(position).getInmueble().getMetros2() + " /m²");
        holder.tvBusquedaPiso.setText(AnuncioLista.get(position).getInmueble().getPiso() + "º planta");
        if (!AnuncioLista.get(position).getrutasFileAnuncio().isEmpty()) {
            Uri uri = AnuncioLista.get(position).getrutasFileAnuncio().get(0);
            holder.ivPrincipal.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return AnuncioLista.size();
    }

    public void setOnClicklistener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}
