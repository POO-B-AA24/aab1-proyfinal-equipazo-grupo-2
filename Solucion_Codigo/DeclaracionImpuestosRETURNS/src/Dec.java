
import java.io.*;
import java.util.ArrayList;

class Usuario implements Serializable {

    private double[] sueldosMensuales;
    private ArrayList<Factura> facturas;
    private double totalIngresosAnuales;
    private double totalDeduccionesAnuales;
    private double impuestos;
    private String reporte;

    public Usuario(double[] sueldosMensuales, ArrayList<Factura> facturas) {
        this.sueldosMensuales = sueldosMensuales;
        this.facturas = facturas;
    }

    public double calcularImpuestos() {
        double totalSueldos = 0;
        for (double sueldo : sueldosMensuales) {
            totalSueldos += sueldo;
        }

        double totalDeducciones = 0;
        for (Factura factura : facturas) {
            switch (factura.getCategoria()) {
                case "Vivienda":
                    totalDeducciones += factura.getMonto() * 0.04; // Segun lo consultado 4% de descuento cuando es vivienda.
                    break;
                case "Educación":
                    totalDeducciones += factura.getMonto() * 0.03;// 3% educacion
                    break;
                case "Alimentación":
                    totalDeducciones += factura.getMonto() * 0.005;// 0.5% cuando es alimentacion
                    break;
                case "Vestimenta":
                    // No se aplica deducción
                    break;
                case "Salud":
                    totalDeducciones += factura.getMonto() * 0.1; // 10% para la Salud
                    break;
                case "Turismo":
                    totalDeducciones += factura.getMonto() * 0.05; //5% para turismo
                    break;
            }
        }

        this.totalIngresosAnuales = totalSueldos * 12;
        this.totalDeduccionesAnuales = totalDeducciones * 12;

        // Calcular impuestos según la tabla de impuestos del 2023
        if (totalIngresosAnuales <= 11722) {
            this.impuestos = 0;
        } else if (totalIngresosAnuales <= 14930) {
            this.impuestos = (totalIngresosAnuales - 11722) * 0.05;
        } else if (totalIngresosAnuales <= 19385) {
            this.impuestos = 160 + (totalIngresosAnuales - 14930) * 0.1;
        } else if (totalIngresosAnuales <= 25638) {
            this.impuestos = 606 + (totalIngresosAnuales - 19385) * 0.12;
        } else if (totalIngresosAnuales <= 33738) {
            this.impuestos = 1356 + (totalIngresosAnuales - 25638) * 0.15;
        } else if (totalIngresosAnuales <= 44721) {
            this.impuestos = 2571 + (totalIngresosAnuales - 33738) * 0.2;
        } else if (totalIngresosAnuales <= 59537) {
            this.impuestos = 4768 + (totalIngresosAnuales - 44721) * 0.25;
        } else if (totalIngresosAnuales <= 79388) {
            this.impuestos = 8472 + (totalIngresosAnuales - 59537) * 0.3;
        } else if (totalIngresosAnuales <= 105580) {
            this.impuestos = 14427 + (totalIngresosAnuales - 79388) * 0.35;
        } else {
            impuestos = 23594 + (totalIngresosAnuales - 105580) * 0.37;
        }

        return impuestos - totalDeduccionesAnuales;
    }

    public void guardarUsuarioEnArchivo(String nombreArchivo) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Usuario guardado en " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarFacturasEnArchivo(String nombreArchivo) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.facturas);
            out.close();
            fileOut.close();
            System.out.println("Factura guardada en " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteImpuestos() {
        this.reporte= "Reporte de impuestos:\n"
                + "Ingresos anuales: $" + this.totalIngresosAnuales + "\n"
                + "Deducciones anuales: $" + this.totalDeduccionesAnuales + "\n"
                + "Impuestos a pagar: $"+this.impuestos;
    }

     public ArrayList<Factura> leerFacturasDeArchivo(String nombreArchivo) {
        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Factura> facturas = (ArrayList<Factura>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Facturas leídas de " + nombreArchivo);
            return facturas;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    

    public void setFacturas(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public String getReporteImpuestos() {
        return reporte;
    }

    public double[] getSueldosMensuales() {
        return sueldosMensuales;
    }

    public void setSueldosMensuales(double[] sueldosMensuales) {
        this.sueldosMensuales = sueldosMensuales;
    }

}

class Factura implements Serializable {

    private String categoria;
    private double monto;

    public Factura(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}

public class Dec implements Serializable {

    public static void main(String[] args) {
        double[] ingresos = {3000, 3500, 4000};
        int numUsuarios = 3;
        ArrayList<Usuario> usuarios = new ArrayList<>();

        for (int i = 0; i < numUsuarios; i++) {
            ArrayList<Factura> facturas = new ArrayList<>();
            facturas.add(new Factura("Vivienda", 5000));
            facturas.add(new Factura("Educación", 2000));
            facturas.add(new Factura("Alimentación", 1000));
            facturas.add(new Factura("Salud", 3000));
            facturas.add(new Factura("Turismo", 1500));
            // escribir facturas
            Usuario usuario = new Usuario(ingresos, facturas);
            usuario.guardarFacturasEnArchivo("factura" + i + ".ser");
            // leer facturas
            ArrayList<Factura> facturasLeidas = usuario.leerFacturasDeArchivo("factura" + i + ".ser");
            usuario.setFacturas(facturasLeidas);
            // calcular impuestos
            double impuestosAPagar = usuario.calcularImpuestos();
            System.out.println("Impuestos a pagar: $" + impuestosAPagar);
            usuario.guardarUsuarioEnArchivo("contribuyente" + i + ".ser"); // escribimos contribuyente con su respectivo reporte
            usuarios.add(usuario); 
// resumen: Salida, Entrada, Salida
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getReporteImpuestos());
        }
    }
}
