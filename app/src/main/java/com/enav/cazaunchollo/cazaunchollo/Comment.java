package com.enav.cazaunchollo.cazaunchollo;

/**
 * Created by Edgar on 13/04/2017.
 */

public class Comment{

    private String autor;
    private String mensaje;

    public Comment(String autor, String mensaje) {
        this.autor = autor;
        this.mensaje = mensaje;
    }

    public Comment() {

    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
