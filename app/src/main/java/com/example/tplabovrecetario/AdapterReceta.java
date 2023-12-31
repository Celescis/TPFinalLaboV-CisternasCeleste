package com.example.tplabovrecetario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterReceta extends RecyclerView.Adapter<ViewHolderReceta> {
    List<Receta> recetasAdapter;
    Context activity;

    public AdapterReceta(List<Receta> recetasMain, Context ac) {
        this.recetasAdapter = recetasMain;
        this.activity = ac;
    }

    @NonNull
    @Override
    public ViewHolderReceta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (this.activity.getClass() == MainActivity.class) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
            return new ViewHolderReceta(v, this.activity);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoritos, parent, false);
            return new ViewHolderReceta(v, this.activity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReceta holder, int position) {
        Receta r = this.recetasAdapter.get(position);
        holder.tvNombre.setText(r.getNombre());
        Picasso.get().load(r.getImg()).into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return recetasAdapter.size();
    }
}