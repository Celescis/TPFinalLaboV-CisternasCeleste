package com.example.tplabovrecetario;


import android.icu.util.Measure;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//EL HILO QUE VA A ESCRIBIR VA A RECIBIR COMO PARAMETRO EL OBJETO HANDLER
public class HiloConexion extends Thread {
    Handler handler;
    String ruta;
    List<Receta> recetaList;
    public static final int CATEGORIAS = 0;
    public static final int COMIDA = 1;
    String seleccion;
    Integer esDetalle;
    String idReceta;
    Receta receta;

    public HiloConexion(Handler handler, String ruta, String idReceta, Integer esDetalle) {
        this.handler = handler;
        this.ruta = ruta;
        this.idReceta = idReceta;
        this.esDetalle = esDetalle;
    }

    public HiloConexion(Handler handler, String ruta, List<Receta> lista, String seleccion, Integer esDetalle){
        this.handler = handler;
        this.ruta = ruta;
        this.recetaList = lista;
        this.seleccion=seleccion;
        this.esDetalle=esDetalle;
    }

    public void run() {
        ConexionAPI c = new ConexionAPI();
        //OBTENGO EL RESULTADO DE LA URL
        byte[] resultado = c.obtener(this.ruta);

        //CREO MENSAJE PARA PASARLE LOS MENSAJES A LA COLA
        Message message = new Message();

        ETipoCategoria seleccionado = ETipoCategoria.American;
        if ("Italian".equals(seleccion)) {
            seleccionado = ETipoCategoria.Italian;
        } else if ("Mexican".equals(seleccion)) {
            seleccionado = ETipoCategoria.Mexican;
        }
        if(esDetalle == CATEGORIAS)
        {
            message.arg1 = CATEGORIAS;
            try {
                JSONObject mainJson = new JSONObject(new String(resultado));
                JSONArray mealsArray = mainJson.getJSONArray("meals");

                for (int i = 0; i < mealsArray.length(); i++) {
                    JSONObject recetasJson = mealsArray.getJSONObject(i);

                    recetaList.add(new Receta(recetasJson.getString("idMeal"),
                            recetasJson.getString("strMeal"),
                            "",
                            Arrays.asList(""),
                            seleccionado,
                            "",
                            recetasJson.optString("strMealThumb", "")));
                }

                message.obj = this.recetaList;

            } catch (JSONException e) {
                Log.e("ERROR", "Error en el JSON: " + e.getMessage());
            }
        }else if (esDetalle == COMIDA){
            message.arg1 = COMIDA;
            try {
                JSONObject mainJson = new JSONObject(new String(resultado));
                JSONArray mealsArray = mainJson.getJSONArray("meals");

                for (int i = 0; i < mealsArray.length(); i++) {
                    JSONObject unaRecetaJson = mealsArray.getJSONObject(i);

                    List<String> ingredientesConMedida = new ArrayList<>();

                    for (int j = 1; j <= 20; j++) {
                        String ingrediente = unaRecetaJson.optString("strIngredient" + j, "");
                        String medida = unaRecetaJson.optString("strMeasure" + j, "");

                        if (!ingrediente.isBlank() && !medida.isBlank()) {
                            ingredientesConMedida.add(medida + " " + ingrediente);
                        }
                    }

                    Log.d("Lista", "list "+ingredientesConMedida);
                    receta = new Receta(unaRecetaJson.getString("idMeal"),
                            unaRecetaJson.getString("strMeal"),
                            unaRecetaJson.getString("strInstructions"),
                            ingredientesConMedida,
                            seleccionado,
                            unaRecetaJson.optString("strYoutube", ""),
                            unaRecetaJson.optString("strMealThumb", ""));
                }

                message.obj = this.receta;

            } catch (JSONException e) {
                Log.e("ERROR", "Error en el JSON2: " + e.getMessage());
            }
        }

        //ENVIO LOS MENSAJES
        handler.sendMessage(message);
    }
}