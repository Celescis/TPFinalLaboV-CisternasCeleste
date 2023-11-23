package com.example.tplabovrecetario.RecetaActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tplabovrecetario.HiloConexion;
import com.example.tplabovrecetario.R;
import com.example.tplabovrecetario.Receta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecetaActivity extends AppCompatActivity implements Handler.Callback {

    ActionBar actionBar;
    public static Bundle bundle;
    public Handler handler;
    Receta receta;
    ModelReceta model;
    ViewReceta view;
    ControllerReceta controller;
    List<Receta> listaFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        listaFavoritos = new ArrayList<>();

        // CREO EL HANDLER UNA VEZ EN LA CREACIÓN DE LA ACTIVIDAD
        handler = new Handler(this);

        //INICIO MI TOOLBAR CON EL BOTON BACK
        this.actionBar = super.getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);

        //AGARRO LOS EXTRA QUE PASO DEL MAIN ACTIVITY E INICIO EL HILO A LA API CON EL ID
        bundle = super.getIntent().getExtras();
        String idBundle = bundle.getString("id");

        String rutaDetalle = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + idBundle;

        HiloConexion hiloDetalle = new HiloConexion(handler, rutaDetalle, idBundle, 1);
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

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receta_menu, menu);

        return true;
    }

    //SOBRESCRIBO EL METODO PARA DESTRUIR LA ACTIVITY AL TOCAR EL BOTON HOME
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            super.finish();
        }

        // ITEM FAVORITOS
        if (item.getItemId() == R.id.btnFavorito) {

            guardarSharedPreferences(this.receta);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarSharedPreferences(Receta recetaFav) {
        // Obtener la lista actual de favoritos desde SharedPreferences
        List<Receta> listaFavoritos = obtenerSharedPreferences();
        if (!listaFavoritos.contains(recetaFav)) {
            // Agregar la nueva receta a la lista
            listaFavoritos.add(recetaFav);
            String listaFavoritosJson = new Gson().toJson(listaFavoritos);

            SharedPreferences sharedPreferences = getSharedPreferences("Favoritos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("listaFavoritos", listaFavoritosJson);
            editor.commit();
            Toast.makeText(this, "Receta agregada a favoritos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Esta receta ya está en favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Receta> obtenerSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("Favoritos", MODE_PRIVATE);
        String listaFavoritosJson = sharedPreferences.getString("listaFavoritos", null);

        if (listaFavoritosJson != null) {
            Type type = new TypeToken<List<Receta>>() {
            }.getType();
            return new Gson().fromJson(listaFavoritosJson, type);
        } else {
            return new ArrayList<>();
        }
    }
}