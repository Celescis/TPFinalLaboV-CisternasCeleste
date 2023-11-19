package com.example.tplabovrecetario.RecetaActivity;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tplabovrecetario.R;
import com.squareup.picasso.Picasso;

public class ViewReceta {
    TextView tvIngredientes;
    TextView tvInstrucciones;
    ImageView ivFoto;
    WebView wvYoutube;
    Activity ac;
    ModelReceta model;
    ControllerReceta controller;

    public ViewReceta(Activity ac, ModelReceta model) {
        this.ac = ac;
        this.model = model;
    }

    public void setController(ControllerReceta controller) {
        this.controller = controller;
        //this.btnGuardar = ac.findViewById(R.id.btnGuardar);
        //this.btnGuardar.setOnClickListener(controller);
        this.cargarElementos();
    }

    public void cargarElementos() {
        if (this.tvIngredientes == null) {
            this.tvIngredientes = this.ac.findViewById(R.id.tvIngredientes);
        }

        if (this.tvInstrucciones == null) {
            this.tvInstrucciones = this.ac.findViewById(R.id.tvInstrucciones);
        }

        if (this.ivFoto == null) {
            this.ivFoto = this.ac.findViewById(R.id.ivFoto);
        }

        if (this.wvYoutube == null) {
            this.wvYoutube = this.ac.findViewById(R.id.wvYoutube);
        }
    }

    public void mostrarModelo() {
        //ARMO UN STRING DE LA LISTA
        StringBuilder listaIngredientes = new StringBuilder();

        for (String ingrediente : model.getIngredientes()) {
            listaIngredientes.append(ingrediente).append("\n");
        }
        this.tvIngredientes.setText(listaIngredientes);
        this.tvInstrucciones.setText(this.model.getInstrucciones());
        Picasso.get().load(this.model.getImg()).into(this.ivFoto);

        //HABILITO JS
        WebSettings webSettings = wvYoutube.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //CREO HTML IFRAME
        String videoUrl = this.model.getLinkYoutube().replace("watch?v=", "embed/");
        String html = "<html><body><iframe width=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        //Configurar WebViewClient
        wvYoutube.setWebViewClient(new WebViewClient());

        //CARGAR HTML EN WEB VIEW
        wvYoutube.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);


    }
}
