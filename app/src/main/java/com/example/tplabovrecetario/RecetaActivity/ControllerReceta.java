package com.example.tplabovrecetario.RecetaActivity;

import android.os.Bundle;
import android.view.View;
import com.example.tplabovrecetario.Receta;

import java.io.Serializable;

public class ControllerReceta implements View.OnClickListener {
    private ModelReceta model;
    private ViewReceta view;

    public ControllerReceta(ViewReceta view, ModelReceta model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onClick(View v) {

        view.ac.finish();
    }

    public void cargarDatos(Receta r) {

        if (r != null) {
            //LE PASO MI OBJETO RECIBIDO
            this.model.setId(r.getId());
            this.model.setNombre(r.getNombre());
            this.model.setTipoCategoria(r.getTipoCategoria());
            this.model.setInstrucciones(r.getInstrucciones());
            this.model.setIngredientes(r.getIngredientes());
            this.model.setImg(r.getImg());
            this.model.setLinkYoutube(r.getLinkYoutube());

            //LO MUESTRO EN EL MODELO
            view.mostrarModelo();
        }
    }
}
