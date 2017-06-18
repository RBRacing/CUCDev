package com.enav.cazaunchollo.cazaunchollo;

public class Comment{

    /* Variables */
    private String autor;
    private String mensaje;
    private String date;


    /* Constructor de Comentario */
    public Comment(String autor, String mensaje, String date) {
        this.autor = autor;
        this.mensaje = mensaje;
        this.date = date;
    }

    /* Constructor vacío */
    public Comment() {

    }

    /* Getters & Setters */
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
