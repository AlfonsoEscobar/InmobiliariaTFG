package com.tfg.inmobiliariatfg.utiles;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.modelos.Inmueble;

import java.util.List;

public class RecyclerViewInmuebleAdapter extends RecyclerView.Adapter<RecyclerViewInmuebleAdapter.ViewHolder> implements View.OnClickListener {

    public List<Inmueble> inmuebleLista;
    private View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProvinciaMisInmuebles, tvLocalidadMisInmuebles, tvDireccionMisInmuebles, tvDescripcionMisInmuebles,
                tvHabitacionesMisInmuebles, tvBanosMisInmuebles, tvMetrosMisInmuebles, tvTObraMisInmuebles, tvTEdificacionMisInmuebles,
                tvEquipamientoMisInmuebles, tvExterioresMisInmuebles;
        private ImageView ivUltPlantaMisInmuebles, ivGarajeMisInmuebles, ivTrasteroMisInmuebles, ivAscensorMisInmuebles, ivMascotasMisInmuebles;


        public ViewHolder(View itemView) {
            super(itemView);

            tvProvinciaMisInmuebles = itemView.findViewById(R.id.tvProvinciaMisInmuebles);
            tvLocalidadMisInmuebles = itemView.findViewById(R.id.tvLocalidadMisInmuebles);
            tvDireccionMisInmuebles = itemView.findViewById(R.id.tvDireccionMisInmuebles);
            tvDescripcionMisInmuebles = itemView.findViewById(R.id.tvDescripcionMisInmuebles);
            tvHabitacionesMisInmuebles = itemView.findViewById(R.id.tvHabitacionesMisInmuebles);
            tvBanosMisInmuebles = itemView.findViewById(R.id.tvBanosMisInmuebles);
            tvMetrosMisInmuebles = itemView.findViewById(R.id.tvMetrosMisInmuebles);
            tvTObraMisInmuebles = itemView.findViewById(R.id.tvTObraMisInmuebles);
            tvTEdificacionMisInmuebles = itemView.findViewById(R.id.tvTEdificacionMisInmuebles);
            tvEquipamientoMisInmuebles = itemView.findViewById(R.id.tvEquipamientoMisInmuebles);
            tvExterioresMisInmuebles = itemView.findViewById(R.id.tvExterioresMisInmuebles);
            ivUltPlantaMisInmuebles = itemView.findViewById(R.id.ivUltPlantaMisInmuebles);
            ivGarajeMisInmuebles = itemView.findViewById(R.id.ivGarajeMisInmuebles);
            ivTrasteroMisInmuebles = itemView.findViewById(R.id.ivTrasteroMisInmuebles);
            ivAscensorMisInmuebles = itemView.findViewById(R.id.ivAscensorMisInmuebles);
            ivMascotasMisInmuebles = itemView.findViewById(R.id.ivMascotasMisInmuebles);
        }
    }

    //Falta añadir el paso de las fotos al arrayAdapter
    public RecyclerViewInmuebleAdapter(List<Inmueble> inmuebleLista) {
        this.inmuebleLista = inmuebleLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_inmueble, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProvinciaMisInmuebles.setText(inmuebleLista.get(position).getProvincia());
        holder.tvLocalidadMisInmuebles.setText(inmuebleLista.get(position).getLocalidad());
        holder.tvDireccionMisInmuebles.setText(inmuebleLista.get(position).getCalle() + ", Nº" +
                inmuebleLista.get(position).getNumero() + ", " + inmuebleLista.get(position).getPiso() + "º" +
                inmuebleLista.get(position).getPuerta() + " Escalera: " + inmuebleLista.get(position).getEscalera());
        if (inmuebleLista.get(position).getDescripcion().equals("")) {
            holder.tvDescripcionMisInmuebles.setText("No se ha añadido descripción");
        } else {
            holder.tvDescripcionMisInmuebles.setText(inmuebleLista.get(position).getDescripcion());
        }
        holder.tvHabitacionesMisInmuebles.append(" " + inmuebleLista.get(position).getNum_habitaciones());
        holder.tvBanosMisInmuebles.append(" " + inmuebleLista.get(position).getNum_banos());
        holder.tvMetrosMisInmuebles.setText(inmuebleLista.get(position).getMetros2() + " /m²");
        holder.tvTObraMisInmuebles.append(" " + inmuebleLista.get(position).getTipo_obra());
        holder.tvTEdificacionMisInmuebles.append(" " + inmuebleLista.get(position).getTipo_edificacion());
        holder.tvEquipamientoMisInmuebles.append(" " + inmuebleLista.get(position).getEquipamiento());
        holder.tvExterioresMisInmuebles.append(" " + inmuebleLista.get(position).getExteriores());
        if (inmuebleLista.get(position).isUltima_planta()) {
            holder.ivUltPlantaMisInmuebles.setImageResource(R.drawable.ic_checked);
        } else {
            holder.ivUltPlantaMisInmuebles.setImageResource(R.drawable.ic_not_checked);
        }
        if (inmuebleLista.get(position).isGaraje()) {
            holder.ivGarajeMisInmuebles.setImageResource(R.drawable.ic_checked);
        } else {
            holder.ivGarajeMisInmuebles.setImageResource(R.drawable.ic_not_checked);
        }
        if (inmuebleLista.get(position).isTrastero()) {
            holder.ivTrasteroMisInmuebles.setImageResource(R.drawable.ic_checked);
        } else {
            holder.ivTrasteroMisInmuebles.setImageResource(R.drawable.ic_not_checked);
        }
        if (inmuebleLista.get(position).isAscensor()) {
            holder.ivAscensorMisInmuebles.setImageResource(R.drawable.ic_checked);
        } else {
            holder.ivAscensorMisInmuebles.setImageResource(R.drawable.ic_not_checked);
        }
        if (inmuebleLista.get(position).isMascotas()) {
            holder.ivMascotasMisInmuebles.setImageResource(R.drawable.ic_checked);
        } else {
            holder.ivMascotasMisInmuebles.setImageResource(R.drawable.ic_not_checked);
        }
    }

    @Override
    public int getItemCount() {
        return inmuebleLista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }
}
