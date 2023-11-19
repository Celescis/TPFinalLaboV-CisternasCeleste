package com.example.tplabovrecetario;

import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tplabovrecetario.RecetaActivity.RecetaActivity;

public class ViewHolderReceta extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvNombre;
    TextView tvTipo;
    ImageView ivFoto;
    MainActivity activity;


    public ViewHolderReceta(@NonNull View itemView, MainActivity ac) {
        super(itemView);
        this.tvTipo = this.itemView.findViewById(R.id.tvTipo);
        this.tvNombre = this.itemView.findViewById(R.id.tvNombre);
        this.ivFoto = this.itemView.findViewById(R.id.ivFoto);

        //CLICK EN ELEMENTO DE LA VISTA
        itemView.setOnClickListener(this);
        //CLICK EN EL BOTON EL ELEMENTO
        //this.btnImage.setOnClickListener(this);
        this.activity = ac;
    }

    @Override
    public void onClick(View view) {
        //DEL VIEWHOLDER VOY A PASAR A MI OTRA ACTIVITY CON LA INFO DEL OBJETO TOCADO

        Intent i = new Intent(view.getContext(), RecetaActivity.class);

        if (super.getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION) {

            //LE PASO EL ID DEL OBJETO
            i.putExtra("id", activity.recetaList.get(super.getAbsoluteAdapterPosition()).getId());

            view.getContext().startActivity(i);
        }


    }
}