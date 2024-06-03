package Controller;

import java.util.ArrayList;
import java.io.*;

public class Persona implements Serializable {

    String nombre;
    double gasto;
    double ingreso;
    double tazaImpositiva;
    double[] sueldosMensuales;
    ArrayList<Factura> facturas = new ArrayList<>();

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public void setFactura(Factura factura) {
        facturas.add(factura);
        // facturas.add(new Factura("Diego", 1150.0, "1102019256", "Guayaquil"));
    }

    public void generarIngresos() {

    }

    public void leerFacturas() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("facturas.dat"));
            facturas = (ArrayList<Factura>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSueldoMensual(double sueldo, int mes) {
        sueldosMensuales[mes] = sueldo;
    }
}

  

    void setSueldoMensual(double sueldo, int mes) {
        sueldosMensuales[mes] = sueldo;
    }

    .
}


