package Controller;

import java.util.Random;

public class Factura {

    String nombre;
    String tipo;
    double sueldoMensual;
    String numCed;
    String direccion;

    public Factura(String nombre, double sueldoMensual, String numCed, String direccion) {
        this.nombre = nombre;
        this.sueldoMensual = sueldoMensual;
        this.numCed = numCed;
        this.direccion = direccion;
    }
}