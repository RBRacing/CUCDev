package com.enav.cazaunchollo.cazaunchollo;

public class Comment{

    private String autor;
    private String mensaje;
    private String level;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
