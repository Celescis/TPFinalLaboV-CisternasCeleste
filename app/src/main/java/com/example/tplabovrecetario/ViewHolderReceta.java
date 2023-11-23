package com.example.tplabovrecetario;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tplabovrecetario.RecetaActivity.RecetaActivity;
import com.example.tplabovrecetario.RecetasFavoritasActivity.RecetasFavoritas;

public class ViewHolderReceta extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvNombre;
    TextView tvTipo;
    ImageView ivFoto;
    Context activity;
    ImageButton btnImage;

    public ViewHolderReceta(@NonNull View itemView, Context ac) {
        super(itemView);
        this.tvTipo = this.itemView.findViewById(R.id.tvTipo);
        this.tvNombre = this.itemView.findViewById(R.id.tvNombre);
        this.ivFoto = this.itemView.findViewById(R.id.ivFoto);
        this.btnImage = this.itemView.findViewById(R.id.btnSacarFav);

        //CLICK EN ELEMENTO DE LA VISTA
        itemView.setOnClickListener(this);
        //CLICK EN EL BOTON EL ELEMENTO
        if (btnImage != null) {
            btnImage.setOnClickListener(this);
        }
        this.activity = ac;
    }

    @Override
    public void onClick(View view) {
        //DEL VIEWHOLDER VOY A PASAR A MI OTRA ACTIVITY CON LA INFO DEL OBJETO TOCADO

        Intent i = new Intent(view.getContext(), RecetaActivity.class);

        if (super.getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION) {

            if (view.getId() == R.id.btnSacarFav) {
                Log.d("hola", "onClick: entro");
                Toast.makeText(activity,"Elimino la receta de favoritos", Toast.LENGTH_SHORT).show();
            } else {
                //LE PASO EL ID DEL OBJETO
                i.putExtra("id", MainActivity.recetaList.get(super.getAbsoluteAdapterPosition()).getId());
                view.getContext().startActivity(i);
            }
        }
    }
}