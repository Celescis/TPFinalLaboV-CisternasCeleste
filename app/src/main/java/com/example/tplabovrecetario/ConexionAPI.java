package com.example.tplabovrecetario;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConexionAPI {

    //public String obtener(String url) para string
    public byte[] obtener(String urlS) {
        try {
            //PRIMERO CREAR OBJETO DE URL
            URL url = new URL(urlS);

            //SEGUNDO CREAR UN OBJETO HTTP URL CONNECTION Y HAY QUE CASTEARLO
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            //hacer inputs y outputs si quisieramos pero antes de hacer el connect (si es post)
            //HACER REQUEST
            urlConnection.connect();

            //verificar el response code para ver si hubo error o no
            if (urlConnection.getResponseCode() == 200) {
                InputStream is = urlConnection.getInputStream();
                //con esto leemos la informacion
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];

                //cant != -1 cuando no haya mas respuestas me devuelve -1
                int cant=0;

                while ((cant = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, cant);
                }

                is.close();

                //String s = new String(baos.toByteArray());
                //Log.d("NOTICIAS", s);
                //return s;
                return baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Log.d("ERROR", e.getMessage());
        }

        return null;
    }
}
