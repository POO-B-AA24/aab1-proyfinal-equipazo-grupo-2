package Controller;

import java.util.ArrayList;
import java.io.*;
import Model.*;

public class Persona implements Serializable {

    String nombre;
    double gasto;
    double ingresoAnual;
    double totalDeducciones;
    double totalIngresos;
    double impBasico, impExcedente, impExcedentePagar, impTotal;
    double tazaImpositiva;

    double[] sueldosMensuales;
    ArrayList<Factura> facturas = new ArrayList<>();

    public Persona(String nombre) {
        this.nombre = nombre;
        this.totalDeducciones = 0;
        this.totalIngresos = 0;
    }

    public void setFactura(Factura factura) {
        facturas.add(factura);
        // facturas.add(new Factura("Diego", 1150.0, "1102019256", "Guayaquil"));
    }

    public void setFacturasDesdeObjeto() {
        int i = 0;
        for (Factura fac : facturas) {
            fac.leerFactura(i);
            i++;
            this.facturas.add(fac);
        }
    }

    public void verificarFactura(Factura fact) {

        double deduccionesVivienda;
        double deduccionesAlimentacion;
        double deduccionesSalud;
        double deduccionesTurismo;
        double deduccionesEducacion;
        deduccionesVivienda += (fact.getCategoria().equals("Vivienda")) ? fact.getMonto() : 0;
        this.totalDeducciones
        
    }

    public void deducciones() {

        verificarFactura();
    }

    public void setSueldoMensual(double sueldo, int mes) {
        sueldosMensuales[mes] = sueldo;
    }

}
