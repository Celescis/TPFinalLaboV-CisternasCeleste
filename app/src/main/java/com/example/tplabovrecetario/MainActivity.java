package com.example.tplabovrecetario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

//PARA CONECTAR LOS HILOS: EL HILO QUE BUSCA INFO EN INTERNET Y EL HILO DE LA GRAFICA
//HAY QUE DETECTAR CUAL VA A SER EL HILO QUE LEA Y CUAL EL QUE ESCRIBA
//EL QUE LEA VA A IMPLEMENTAR EL HANDLER VA A SER EL ENCARGADO DE CREAR LA COLA DE MENSAJERIA
public class MainActivity extends AppCompatActivity implements Handler.Callback {
    private static final String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREO EN HANDLER PARA LA COLA DE MENSAJERIA
        Handler handler = new Handler(this);

        String rutaS = "https://www.themealdb.com/api/json/v1/1/search.php?f=a";
        //se debe poner los datos en otro hilo distinto del principal, le paso el handler para que escriban los mensajes
        HiloConexion h = new HiloConexion(handler, rutaS, false);
        h.start();
        String rutaImg = "https://www.clarin.com/img/2023/11/09/K2_jZF4bo_1200x630__1.jpg";

        //hilo para imagenes
        HiloConexion hImg = new HiloConexion(handler, rutaImg, true);
        hImg.start();

        /*
        for (String letra : letras) {
            String rutaS = "https://www.themealdb.com/api/json/v1/1/search.php?f=" + letra;

            //se debe poner los datos en otro hilo distinto del principal, le paso el handler para que escriban los mensajes
            HiloConexion h = new HiloConexion(handler, rutaS, false);
            h.start();

        }*/


    }

    //ACA PODEMOS MANEJAR LOS TEXTVIEW PORQUE ESTAMOS CON EL HILO QUE LEE LOS MENSAJES
    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.arg1 == HiloConexion.NOTI) {
            TextView tv = this.findViewById(R.id.TvReceta);
            tv.setText(message.obj.toString());
        } else if (message.arg1 == HiloConexion.IMG) {
            ImageView iv = this.findViewById(R.id.IvImagen);
            byte[] imgByte = (byte[]) message.obj;
            iv.setImageBitmap(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
        }

        return false;
    }
}