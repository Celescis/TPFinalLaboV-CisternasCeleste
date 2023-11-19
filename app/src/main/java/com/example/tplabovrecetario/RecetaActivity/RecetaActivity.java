package com.example.tplabovrecetario.RecetaActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.example.tplabovrecetario.HiloConexion;
import com.example.tplabovrecetario.R;
import com.example.tplabovrecetario.Receta;

public class RecetaActivity extends AppCompatActivity implements Handler.Callback{

    ActionBar actionBar;
    public static Bundle bundle;
    public Handler handler;
    Receta receta;
    ModelReceta model;
    ViewReceta view;
    ControllerReceta controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        // CREO EL HANDLER UNA VEZ EN LA CREACIÓN DE LA ACTIVIDAD
        handler = new Handler(this);

        //INICIO MI TOOLBAR CON EL BOTON BACK
        this.actionBar = super.getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);

        //AGARRO LOS EXTRA QUE PASO DEL MAIN ACTIVITY E INICIO EL HILO A LA API CON EL ID
        bundle = super.getIntent().getExtras();
        String idBundle = bundle.getString("id");

        String rutaDetalle = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idBundle;

        HiloConexion hiloDetalle = new HiloConexion(handler, rutaDetalle,idBundle, 1);
        hiloDetalle.start();

        model = new ModelReceta();
        view = new ViewReceta(this, model);
        controller = new ControllerReceta(view, model);
        view.setController(controller);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.arg1 == HiloConexion.COMIDA) {
            //ACTUALIZO EL MODELO CON LOS DATOS RECIBIDOS
            this.receta = (Receta) message.obj;
            actionBar.setTitle(receta.getNombre());

            //LE PASO LOS DATOS A MI CONTROLLER PARA GUARDARLO EN EL MODELO
            controller.cargarDatos(this.receta);

        }
        return false;
    }

    //SOBRESCRIBO EL METODO PARA DESTRUIR LA ACTIVITY AL TOCAR EL BOTON HOME
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}