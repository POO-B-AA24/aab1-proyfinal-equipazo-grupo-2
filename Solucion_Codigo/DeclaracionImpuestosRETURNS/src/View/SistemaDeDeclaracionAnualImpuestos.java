package View;

import java.util.ArrayList;
import Controller.Factura;
import Controller.Contribuyente;
import Model.*;
import java.util.Random;
import java.util.Scanner;

public class SistemaDeDeclaracionAnualImpuestos {

    public static void main(String[] args) {
        ArrayList<Contribuyente> usuarios = new ArrayList<>();
        boolean obtieneDividendos; //requerimiento adicional
        // contadores y condicionales para crear varios contribuyentes
        Random r = new Random();
        int i = 0;
        boolean continuar = true;
        Scanner in = new Scanner(System.in);

        do {

            ArrayList<Factura> facturas = new ArrayList<>();
            for (int j = 0; j < 12; j++) { // 12 meses contienen facturas de cualquier categoria. Asi cada contribuyentes tiene al menos 60 facturas almacenadas en un solo archivo llamado: Facturas del  Contribuyente + contador(para mantener el orden de quien fue primero).
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
                facturas.add(new Factura(generarCategorias(), generarGasto()));
            }
            // escribir facturas
            Contribuyente usuario = new Contribuyente(generarNombres(), generarSueldosDeUnAnio(), generarDireccion(), generarCedulas()); // El usuario nos da sueldo anual. Asi no tiene que molestarse con colocar cada elemento de sueldo mensual
            EscribirFactura veEscribe = new EscribirFactura(facturas);
            veEscribe.guardarFacturasEnArchivo("factura" + i + "-" + usuario.getName() + ".dat");
            System.out.println("Factura guardada en " + "factura" + i + "-" + usuario.getName() + ".dat");

            // leer facturas → asignar facturas
            LeerFactura veLee = new LeerFactura();
            ArrayList<Factura> facturasLeidas = veLee.leerFacturasDeArchivo("factura" + i + "-" + usuario.getName() + ".dat");
            System.out.println("Facturas leídas de " + "factura" + i + "-" + usuario.getName() + ".dat");
            usuario.setFacturas(facturasLeidas); // 

            // resumen: Facturas son Autogeneradas , serializadas y al final deserializadas.
            
            // calcular impuestos
            obtieneDividendos = r.nextBoolean();

            if (obtieneDividendos) { // Como ventaja fiscal se permite que una persona pueda deducir la tasa impositiva de dividendo(depende de la compania) sobre lo que esta le paga
                usuario.setDividend(generarDividendos());
                usuario.setDividendTaxRate(generarTasasImpositivasDividendos());
            }
            usuario.calcularImpuestos(); // NOTA: Las deducciones son solo fracciiones del gasto por cada categoria de factura. Esto es establecido segun nuestra investigacion
            usuario.generarReporteImpuestos();
            EscribirContribuyente guardarContrib = new EscribirContribuyente(usuario);
            guardarContrib.guardarUsuarioEnArchivo(usuario.getName() + ".dat"); // escribimos contribuyente con su respectivo reporte
            System.out.println("Usuario guardado en " + usuario.getName() + ".dat");
            usuarios.add(usuario); // no es indispensable para nuestro programa
            i++;
            System.out.println("¿Desea ingresar otro Contribuyente? (Si / No)");
            if (!in.next().equalsIgnoreCase("Si")) { //nextLine
                continuar = false;
                break;
            }
        } while (continuar);

        // resumen : // Contribuyentes son: Escritos, luego Leidos
        ArrayList<Contribuyente> clientes = new ArrayList<>();
        for (int d = 0; d < i; d++) {
            LeerContribuyente leeContrib = new LeerContribuyente();
            clientes.add(leeContrib.leerUsuarioDesdeArchivo(usuarios.get(d).getName() + ".dat"));
            System.out.println("Contribuyente con su reporte leido desde " + usuarios.get(d).getName() + ".dat");
            System.out.println(clientes.get(d).toString()); // Reporte completaso y personalizado con cada factura que tiene un contribuyente (para nuestro caso empezamos con 5 facturas dentro de un archivo para cada contribuyente.
            // usuariso.get(posicion)
            // array[i]= elemento;
            
            /* 
            metodos para que el usuario agregue mas facturas. O para que nosotros agreguemos mas usuarios
            */
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
        double[] dividendos = {3000, 2400, 2600, 4000, 1000, 200, 150, 100, 135};
        return dividendos[random.nextInt(dividendos.length)];
    }

    public static double generarTasasImpositivasDividendos() {
        Random random = new Random();
        double[] dividendos = {0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6};
        return dividendos[random.nextInt(dividendos.length)];
    }

    public static double generarGasto() {
        Random random = new Random();
        double gasto;
        gasto = random.nextDouble(100, 500) + 1;
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
