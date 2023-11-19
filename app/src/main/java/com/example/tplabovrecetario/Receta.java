package com.example.tplabovrecetario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Receta implements Serializable {

    private String id;
    private String nombre;
    private String instrucciones;
    private List<String> ingredientes;
    private ETipoCategoria tipoCategoria;
    private String linkYoutube;
    private String img;

    public Receta(){
        ingredientes = new ArrayList<>();
    }

    public Receta(String id,String nombre, String instrucciones, List<String> ingredientes, ETipoCategoria tipoCategoria, String linkYoutube, String img) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.ingredientes = ingredientes;
        this.tipoCategoria = tipoCategoria;
        this.linkYoutube = linkYoutube;
        this.img = img;
    }
    //mandar a un componente webview
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ETipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(ETipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", ingredientes=" + ingredientes +
                ", tipoCategoria=" + tipoCategoria +
                ", linkYoutube='" + linkYoutube + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}