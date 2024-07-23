package Controller;
import java.util.Random;

public class GeneradorDatos {
    
    private static Random random = new Random();

    public static String generarNombres() {
        String[] nombres = {"Juan Pablo Landi", "Marco Abarca", "Juan Diego Guerrero", "Ricardo Espinosa", "Alejandro Alvarrez", "David Velez", "Daniel Bustamante", "Nicolas Gallegos", "Miguel Valverde", "Xavier Gonzales"};
        return nombres[random.nextInt(nombres.length)];
    }

    public static double[] generarSueldosDeUnAnio() {
        double[] sueldo = new double[12];
        for (int i = 0; i < sueldo.length; i++) {
            sueldo[i] = 500 + (1500 - 500) * random.nextDouble();
        }
        return sueldo;
    }

    public static double generarDividendos() {
        double[] dividendos = {3000, 2400, 2600, 4000, 1000, 200, 150, 100, 135};
        return dividendos[random.nextInt(dividendos.length)];
    }

    public static double generarTasasImpositivasDividendos() {
        double[] tasas = {0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6};
        return tasas[random.nextInt(tasas.length)];
    }

    public static double generarGasto() {
        return random.nextDouble() * (500 - 100) + 100;
    }

    public static String generarCategorias() {
        String[] categorias = {"Alimentacion", "Vivienda", "Educacion", "Salud", "Turismo"};
        return categorias[random.nextInt(categorias.length)];
    }

    public static String generarDireccion() {
        String[] direcciones = {"Loja", "El oro", "Quito", "Cuenca", "Guayaquil", "Zapotillo", "Ambato", "Manabí", "Esmeraldas", "Zamora"};
        return direcciones[random.nextInt(direcciones.length)];
    }

    public static String generarCedulas() {
        String[] prefijos = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        StringBuilder cedula = new StringBuilder(prefijos[random.nextInt(prefijos.length)]);
        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            cedula.append(digito);
        }
        return cedula.toString();
    }
}