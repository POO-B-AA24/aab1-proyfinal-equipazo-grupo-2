import java.io.*;
import java.util.ArrayList;

class Usuario implements Serializable {
    private double[] sueldosMensuales;
    private ArrayList<Factura> facturas;

    public Usuario(double[] sueldosMensuales, ArrayList<Factura> facturas) {
        this.sueldosMensuales = sueldosMensuales;
        this.facturas = facturas;
    }

    public double calcularImpuestos() {
        // Calculations for taxes based on income and deductions
    }

    public void guardarFacturasEnArchivo(String nombreArchivo) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(facturas);
            out.close();
            fileOut.close();
            System.out.println("Facturas guardadas en " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leerFacturasDeArchivo(String nombreArchivo) {
        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            facturas = (ArrayList<Factura>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Facturas leídas de " + nombreArchivo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Factura implements Serializable {
    private String categoria;
    private double monto;

    public Factura(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    // Getters and setters
}

public class Dec implements Serializable {
    public static void main(String[] args) {
        double[] sueldos = {3000, 3500, 4000};
        ArrayList<Factura> facturas = new ArrayList<>();
        
        // Guardar facturas en un archivo .ser
        Usuario usuario = new Usuario(sueldos, facturas);
        usuario.guardarFacturasEnArchivo("facturas.ser");

        // Leer facturas de un archivo .ser
        Usuario usuarioLeido = new Usuario(sueldos, new ArrayList<>());
        usuarioLeido.leerFacturasDeArchivo("facturas.ser");

        double impuestosAPagar = usuarioLeido.calcularImpuestos();
        System.out.println("Impuestos a pagar: $" + impuestosAPagar);
    }
}
