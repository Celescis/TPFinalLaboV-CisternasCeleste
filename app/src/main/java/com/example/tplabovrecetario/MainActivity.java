package com.example.tplabovrecetario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tplabovrecetario.RecetaActivity.RecetaActivity;
import com.example.tplabovrecetario.RecetasFavoritasActivity.RecetasFavoritas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, SearchView.OnQueryTextListener {
    public static List<Receta> recetaList;
    AdapterReceta adapterReceta;
    Handler handler;
    private String categoriaSeleccionada = "American";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CREO EL HANDLER UNA VEZ EN LA CREACIÓN DE LA ACTIVIDAD
        handler = new Handler(this);

        // Inicialmente, carga las recetas americanas
        cambiarValorURL(categoriaSeleccionada);
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //OBTENGO EL SEARCH VIEW
        MenuItem searchItem = menu.findItem(R.id.svBuscar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //CONFIGURO EL LISTENER
        searchView.setOnQueryTextListener(this);

        return true;
    }

    //MENU SEARCHVIEW
    @Override
    public boolean onQueryTextSubmit(String query) {
        //CUANDO TOCA ENTER CON EL INGREDIENTE

        String ingrediente = query.replace(" ", "_");

        String rutaS = "https://www.themealdb.com/api/json/v1/1/filter.php?i=" + ingrediente;

        //LE PASO AL HILO LOS VALORES
        HiloConexion hIngrediente = new HiloConexion(handler, rutaS, recetaList, 2);
        hIngrediente.start();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //PARA CAMBIOS EN EL TEXTO DEL BUSCADOR
        return true;
    }

    //MENU OPCIONES
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // CAMBIO EL VALOR DE SELECCION SEGÚN LA OPCIÓN DE MENU
        if (id == R.id.op1) {
            categoriaSeleccionada = "American";
        } else if (id == R.id.op2) {
            categoriaSeleccionada = "Italian";
        } else if (id == R.id.op3) {
            categoriaSeleccionada = "Mexican";
        }else if (id == R.id.btnFavorito) {
            Intent i = new Intent(this, RecetasFavoritas.class);
            startActivity(i);
            return true;
        }
        setTitle("Comida tipo " + categoriaSeleccionada);
        cambiarValorURL(categoriaSeleccionada);
        return super.onOptionsItemSelected(item);
    }

    private void cambiarValorURL(String seleccion) {
        // CAMBIO LA CATEGORIA DE COMIDA
        String rutaS = "https://www.themealdb.com/api/json/v1/1/filter.php?a=" + seleccion;

        // INICIALIZO LA LISTA DE RECETA
        recetaList = new ArrayList<>();

        //LE PASO AL HILO LOS VALORES
        HiloConexion h = new HiloConexion(handler, rutaS, recetaList, seleccion, 0);
        h.start();
    }

    //MANEJAR HILO
    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.arg1 == HiloConexion.CATEGORIAS) {
            // ACTUALIZO LA LISTA QUE VIENE DEL OTRO HILO
            this.recetaList = (List<Receta>) message.obj;

            if (!this.recetaList.isEmpty()) {
                // GENERO EL RECYCLE VIEW PARA PASARLE ID DEL RECYCLE VIEW DE LAYOUT
                RecyclerView recyclerView = findViewById(R.id.rvReceta);

                // GENERO EL ADAPTER Y LE PASO LA LISTA
                this.adapterReceta = new AdapterReceta(this.recetaList, this);

                // LE PASO EL ADAPTER AL RECYCLER VIEW
                recyclerView.setAdapter(this.adapterReceta);

                // GENERO EL LINEARLAYOUT PARA CONTROLAR COMO SE VA A VER
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

                // LE PASO EL LINEARLAYOUT AL RECYCLER VIEW
                recyclerView.setLayoutManager(linearLayoutManager);

                // NOTIFICO AL ADAPTER QUE ACTUALICE LA LISTA
                this.adapterReceta.notifyDataSetChanged();
            }
        } else if (message.arg1 == HiloConexion.INGREDIENTES) {

            //CHEQUEO SI ME TRAE UNA LISTA
            if (message.obj != null && message.obj.getClass().equals(ArrayList.class)) {

                // ACTUALIZO LA LISTA QUE VIENE DEL OTRO HILO
                this.recetaList = (List<Receta>) message.obj;

                if (!this.recetaList.isEmpty()) {
                    // GENERO EL RECYCLE VIEW PARA PASARLE ID DEL RECYCLE VIEW DE LAYOUT
                    RecyclerView recyclerView = findViewById(R.id.rvReceta);

                    // GENERO EL ADAPTER Y LE PASO LA LISTA
                    this.adapterReceta = new AdapterReceta(this.recetaList, this);

                    // LE PASO EL ADAPTER AL RECYCLER VIEW
                    recyclerView.setAdapter(this.adapterReceta);

                    // GENERO EL LINEARLAYOUT PARA CONTROLAR COMO SE VA A VER
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

                    // LE PASO EL LINEARLAYOUT AL RECYCLER VIEW
                    recyclerView.setLayoutManager(linearLayoutManager);

                    // NOTIFICO AL ADAPTER QUE ACTUALICE LA LISTA
                    this.adapterReceta.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "No se encontraron recetas con ese ingrediente", Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }

}