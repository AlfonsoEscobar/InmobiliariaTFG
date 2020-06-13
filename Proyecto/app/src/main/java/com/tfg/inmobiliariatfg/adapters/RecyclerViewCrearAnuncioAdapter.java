package com.tfg.inmobiliariatfg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Inmueble;

import java.util.List;

public class RecyclerViewCrearAnuncioAdapter extends RecyclerView.Adapter<RecyclerViewCrearAnuncioAdapter.ViewHolderAnuncios> implements View.OnClickListener {

    public List<Inmueble> inmuebleLista;
    private View.OnClickListener listener;

    public static class ViewHolderAnuncios extends RecyclerView.ViewHolder {
    private TextView tvLocalidadCrearAnuncio, tvCalleCrearAnuncio;

        public ViewHolderAnuncios(View itemView) {
            super(itemView);
            tvLocalidadCrearAnuncio = itemView.findViewById(R.id.tvLocalidadCreacionAnuncio);
            tvCalleCrearAnuncio = itemView.findViewById(R.id.tvCalleItemCreacionAnuncio);
        }
    }

    public RecyclerViewCrearAnuncioAdapter(List<Inmueble> inmuebleLista) {
        this.inmuebleLista = inmuebleLista;
    }

    @NonNull
    @Override
    public ViewHolderAnuncios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_crear_anuncio, parent, false);
        ViewHolderAnuncios viewHolder = new ViewHolderAnuncios(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnuncios holder, int position) {
        holder.tvCalleCrearAnuncio.setText(inmuebleLista.get(position).getCalle() + ", Nº" +
                inmuebleLista.get(position).getNumero() + ", " + inmuebleLista.get(position).getPiso() + "º" +
                inmuebleLista.get(position).getPuerta() + " Escalera: " + inmuebleLista.get(position).getEscalera());
        holder.tvLocalidadCrearAnuncio.setText(inmuebleLista.get(position).getLocalidad());
    }

    @Override
    public int getItemCount() {
        return inmuebleLista.size();
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
