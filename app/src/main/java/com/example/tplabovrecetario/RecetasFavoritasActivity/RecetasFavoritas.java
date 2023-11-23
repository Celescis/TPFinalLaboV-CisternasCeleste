package com.example.tplabovrecetario.RecetasFavoritasActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tplabovrecetario.AdapterReceta;
import com.example.tplabovrecetario.R;
import com.example.tplabovrecetario.Receta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecetasFavoritas extends AppCompatActivity implements Handler.Callback {
    public List<Receta> listaFavoritos;
    private AdapterReceta adapterReceta;
    private Handler handler;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        listaFavoritos = new ArrayList<>();

        // INICIO MI TOOLBAR CON EL BOTON BACK
        this.actionBar = super.getSupportActionBar();
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mis favoritos");

        // CREO EL HANDLER UNA VEZ EN LA CREACIÓN DE LA ACTIVIDAD
        handler = new Handler(this);

        // INICIALIZO LA LISTA DE FAVORITOS DESDE LAS PREFERENCIAS COMPARTIDAS
        listaFavoritos = obtenerSharedPreferences();

        // GENERO EL RECYCLE VIEW PARA PASARLE ID DEL RECYCLE VIEW DE LAYOUT
        RecyclerView recyclerView = findViewById(R.id.rvReceta);

        // GENERO EL ADAPTER Y LE PASO LA LISTA
        adapterReceta = new AdapterReceta(listaFavoritos, RecetasFavoritas.this);

        // LE PASO EL ADAPTER AL RECYCLER VIEW
        recyclerView.setAdapter(adapterReceta);

        // GENERO EL LINEARLAYOUT PARA CONTROLAR COMO SE VA A VER
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // LE PASO EL LINEARLAYOUT AL RECYCLER VIEW
        recyclerView.setLayoutManager(linearLayoutManager);

        // NOTIFICO AL ADAPTER QUE ACTUALICE LA LISTA
        this.adapterReceta.notifyDataSetChanged();
    }

    private List<Receta> obtenerSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("Favoritos", MODE_PRIVATE);
        String listaFavoritosJson = sharedPreferences.getString("listaFavoritos", null);

        if (listaFavoritosJson != null) {
            Type type = new TypeToken<List<Receta>>() {}.getType();
            return new Gson().fromJson(listaFavoritosJson, type);
        } else {
            return listaFavoritos;
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        return false;
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favoritos_menu, menu);

        return true;
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
