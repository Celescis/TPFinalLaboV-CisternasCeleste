package com.example.tplabovrecetario;


import android.icu.util.Measure;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

//EL HILO QUE VA A ESCRIBIR VA A RECIBIR COMO PARAMETRO EL OBJETO HANDLER
public class HiloConexion extends Thread {
    public static final int IMG = 10;
    public static final int NOTI=1;
    Handler handler;
    String ruta;
    boolean esImagen;

    public HiloConexion(Handler handler, String ruta, boolean esImagen)
    {
        this.handler = handler;
        this.ruta = ruta;
        this.esImagen = esImagen;
    }

    public void run() {
        ConexionAPI c = new ConexionAPI();
        //ESCRIBO LOS MENSAJES
        Message message = new Message();

        byte[] respuesta = c.obtener(ruta);
        //verifico si es una imagen o un string
        if(!esImagen)
        {
            message.arg1 = NOTI;
            message.obj = new String(respuesta);
        }else
        {
            message.arg1 = IMG;
            message.obj = respuesta;
        }

        //ENVIO LOS MENSAJES
        handler.sendMessage(message);
    }
}
