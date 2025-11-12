package com.example.listatareas;

public class Tarea {

    String nombre;
    String descripcion;
    float valoracion;

    public Tarea(String nombre, String descripcion) {
        // Constructor original, la valoración es 0 por defecto
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valoracion = 0; // Valor por defecto
    }

    // Nuevo constructor para crear con prioridad (valoración)
    public Tarea(String nombre, String descripcion, float valoracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valoracion = valoracion;
    }
}