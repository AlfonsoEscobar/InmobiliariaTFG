package com.tfg.inmobiliariatfg.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.inmobiliariatfg.R;

import java.util.ArrayList;

public class ImagenesHorizontalAdapter extends RecyclerView.Adapter<ImagenesHorizontalAdapter.HorizontalViewHolder> {
    Context context;
    ArrayList<Uri> arrayList;

    public ImagenesHorizontalAdapter(Context context, ArrayList<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_imagen, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        Uri uri = arrayList.get(position);
        holder.ivInmuebleHorizontal.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivInmuebleHorizontal;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            ivInmuebleHorizontal = itemView.findViewById(R.id.ivInmuebleHorizontal);
        }
    }
}
