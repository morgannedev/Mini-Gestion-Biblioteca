package model;

import model.enums.Estado;

import java.time.LocalDate;

public class Libro {
    private int id;
    private String titulo;
    private String isbn;
    private String autor;
    private LocalDate fechaPublicacion;
    private Estado estado;

    public Libro() {
    }

    public Libro(int id, String titulo, String isbn, String autor, LocalDate fechaPublicacion, Estado estado) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        String fecha = (fechaPublicacion != null) ? fechaPublicacion.toString() : "N/D";
        return String.format(
                "ID: %d | Título: %-25s | Autor: %-25s | ISBN: %-15s | Fecha: %s | Estado: %s",
                id,
                titulo != null ? titulo : "N/D",
                autor != null ? autor : "N/D",
                isbn != null ? isbn : "N/D",
                fecha,
                estado != null ? estado : "N/D"
        );
    }
}
