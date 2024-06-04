package View;

import java.io.*;
import java.util.ArrayList;
import Controller.Factura;
import Controller.Contribuyente;
import Model.*;
import java.util.Random;

public class SistemaDeDeclaracionAnualImpuestos implements Serializable {

    public static void main(String[] args) {
        double[] ingresos = {3000, 3500, 4000};
        int numContribuyentes = 3;
        ArrayList<Contribuyente> usuarios = new ArrayList<>();
        boolean obtieneDividendos; //requerimiento adicional
        Random r = new Random();
        for (int i = 0; i < numContribuyentes; i++) {
            ArrayList<Factura> facturas = new ArrayList<>();
            for (int j = 0; j < 12; j++) { // 12 meses contienen facturas de cualquier categoria
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
            }
            // escribir facturas
            Contribuyente usuario = new Contribuyente(generarNombres(), generarSueldosDeUnAnio(), generarDireccion(), generarCedulas()); // El usuario nos da sueldo anual. Asi no tiene que molestarse con colocar cada elemento de sueldo mensual
            EscribirFactura veEscribe = new EscribirFactura(facturas);
            veEscribe.guardarFacturasEnArchivo("factura" + i + "-" + usuario.getName() + ".ser");
            // leer facturas
            LeerFactura veLee = new LeerFactura();
            ArrayList<Factura> facturasLeidas = veLee.leerFacturasDeArchivo("factura" + i + "-" + usuario.getName() + ".ser");
            usuario.setFacturas(facturasLeidas);
            // calcular impuestos
            obtieneDividendos = r.nextBoolean();
            
            if (obtieneDividendos) { // Como ventaja fiscal se permite que una persona pueda deducir la tasa impositiva de dividendo(depende de la compania) sobre lo que esta le paga
                usuario.setDividend(generarDividendos());
                usuario.setDividendTaxRate(generarTasasImpositivasDividendos());
            }
            usuario.calcularImpuestos(); // NOTA: Las deducciones son solo fracciiones del gasto por cada categoria de factura. Esto es establecido segun nuestra investigacion
            //System.out.println("Impuestos a pagar: $" + impuestosAPagar);
            usuario.generarReporteImpuestos();
            EscribirContribuyente guardarContrib = new EscribirContribuyente(usuario);
            guardarContrib.guardarUsuarioEnArchivo(usuario.getName() + ".ser"); // escribimos contribuyente con su respectivo reporte
            usuarios.add(usuario);
// resumen: Facturas son Salida, Entrada,
// Contribuyentes son: Entrada, Salida
        }
        ArrayList<Contribuyente> clientes = new ArrayList<>();
        for (int i = 0; i < numContribuyentes; i++) {
            LeerContribuyente leeContrib = new LeerContribuyente();
            clientes.add(leeContrib.leerUsuarioDesdeArchivo(usuarios.get(i).getName() + ".ser"));
            System.out.println(clientes.get(i).getReporteImpuestos());

        }
    }

    // METODOS AUTOGENERADORES
    public static String generarNombres() {
        Random random = new Random();
        String[] nombres = {"Juan Pablo Landi", "Marco Abarca", "Juan Diego Guerrero", "Ricardo Espinosa", "Alejandro Alvarrez", "David Velez", "Daniel Bustamante", "Nicolas Gallegos", "Miguel Valverde", "Xavier Gonzales"};
        return nombres[random.nextInt(nombres.length)];
    }

    public static double[] generarSueldosDeUnAnio() {
        Random random = new Random();
        double sueldo[] = new double[12];
        for (int i = 0; i < sueldo.length; i++) {
            sueldo[i] = 500 + (1500 - 500) * random.nextDouble();
        }
        return sueldo;
    }
    

    public static double generarDividendos() {
        Random random = new Random();
        double[] dividendos = {3000,2400,2600,4000,1000,200,150,100,135};
        return dividendos[random.nextInt(dividendos.length)];
    }
    public static double generarTasasImpositivasDividendos() {
        Random random = new Random();
        double[] dividendos = {0.1,0.15,0.2,0.3,0.4,0.5,0.6};
        return dividendos[random.nextInt(dividendos.length)];
    }

    public static double generarGasto() {
        Random random = new Random();
        double gasto;
        gasto = 100 + (100 - 500) * random.nextDouble();
        return gasto;
    }

    public static String generarCategorias() {
        Random random = new Random();
        String[] categorias = {"Alimentacion", "Vivienda", "Educacion", "Salud", "Turismo"};
        return categorias[random.nextInt(categorias.length)];
    }

    public static String generarDireccion() {
        Random random = new Random();
        String[] direcciones = {"Loja", "El oro", "Quito", "Cuenca", "Guayaquil", "Zapotillo", "Ambato", "Manabí", "Esmeraldas", "Zamora"};
        return direcciones[random.nextInt(direcciones.length)];
    }

    public static String generarCedulas() {
        Random random = new Random();
        String[] ced = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        String cedula = ced[random.nextInt(ced.length)];
        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            cedula += digito;
        }
        return cedula;
    }
}
