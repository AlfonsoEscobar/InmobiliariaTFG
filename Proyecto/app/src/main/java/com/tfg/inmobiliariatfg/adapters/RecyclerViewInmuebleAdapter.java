package com.tfg.inmobiliariatfg.adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.RegistrarInmuebleActivity;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.utiles.ApiAdapter;
import com.tfg.inmobiliariatfg.utiles.Metodos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewInmuebleAdapter extends RecyclerView.Adapter<RecyclerViewInmuebleAdapter.ViewHolder> {

    private List<Inmueble> inmuebleLista;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProvinciaMisInmuebles, tvLocalidadMisInmuebles, tvDireccionMisInmuebles, tvDescripcionMisInmuebles,
                tvHabitacionesMisInmuebles, tvBanosMisInmuebles, tvMetrosMisInmuebles, tvTObraMisInmuebles, tvTEdificacionMisInmuebles,
                tvEquipamientoMisInmuebles, tvExterioresMisInmuebles;
        private ImageView ivUltPlantaMisInmuebles, ivGarajeMisInmuebles, ivTrasteroMisInmuebles, ivAscensorMisInmuebles, ivMascotasMisInmuebles;
        private Button btnOpcionesInmueble;
        private ScrollView svMisInmuebles;
        private RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            svMisInmuebles = itemView.findViewById(R.id.svMisInmuebles);
            recyclerView = itemView.findViewById(R.id.recyclerViewImagenesAnuncio);
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
            btnOpcionesInmueble = itemView.findViewById(R.id.btnOpcionesInmuebles);

            svMisInmuebles.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Disallow the touch request for parent scroll on touch of child view

                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    view.onTouchEvent(motionEvent);
                    return true;
                }
            });
        }
    }

    //Falta añadir el paso de las fotos al arrayAdapter
    public RecyclerViewInmuebleAdapter(Context context, List<Inmueble> inmuebleLista) {
        this.inmuebleLista = inmuebleLista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_inmueble, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
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

        holder.btnOpcionesInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDialog(context, inmuebleLista.get(position));
            }
        });

        ArrayList<Integer> imagenesInmuebleItem = inmuebleLista.get(position).getImagenesInmueble();

        ImagenesHorizontalAdapter imagenHorizontalAdapter = new ImagenesHorizontalAdapter(context, imagenesInmuebleItem);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(imagenHorizontalAdapter);

    }

    private void cargarDialog(final Context context, final Inmueble response) {

        final Inmueble inmueble = response;
        final CharSequence[] opciones = {"Modificar Inmueble", "Eliminar Inmueble", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(context);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Modificar Inmueble")) {
                    Intent intent = new Intent(context, RegistrarInmuebleActivity.class);
                    intent.putExtra("inmueble", response);
                    context.startActivity(intent);

                } else {
                    if (opciones[which].equals("Eliminar Inmueble")) {
                        eliminarInmueble(context, inmueble);
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void eliminarInmueble(final Context context, Inmueble inmueble){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        Metodos.mostrarDialogo(progressDialog);
        progressDialog.setMessage("Eliminando inmueble...");
        Call<Void> call = ApiAdapter.getApiService(String.valueOf(R.string.baseURL)).eliminarInmueble(inmueble.getId_inmueble());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    progressDialog.dismiss();
                    Toast.makeText(context,
                            "Inmueble Eliminado", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (inmuebleLista != null) {
            return inmuebleLista.size();
        }
        return 0;
    }
}
