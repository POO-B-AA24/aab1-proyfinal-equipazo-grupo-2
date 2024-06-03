import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class error2 {
    private static final String[] CATEGORIAS = {"Vivienda", "Educación", "Alimentación", "Vestimenta", "Salud", "Turismo"};
    private static final double MAX_DEDUCCION = 0.18;
    private static final int ANIO = 2023;

    private static ArrayList<Contribuyente> contribuyentes = new ArrayList<>();

 
    private static int obtenerNumeroContribuyentes() {
        System.out.println("¿Cuántos contribuyentes desea registrar?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error al leer el número de contribuyentes: " + e.getMessage());
            return 0;
        }
    }

    private static void generarDatosContribuyentes(int numContribuyentes) {
        for (int i = 0; i < numContribuyentes; i++) {
            String nombre = "Contribuyente " + (i + 1);
            double[] sueldos = generarSueldos();
            double[][] facturas = generarFacturas();
            contribuyentes.add(new Contribuyente(nombre, sueldos, facturas));
        }
    }

    private static double[] generarSueldos() {
        double[] sueldos = new double[12];
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            sueldos[i] = random.nextDouble() * 4000 + 500;
        }
        return sueldos;
    }

    private static double[][] generarFacturas() {
        double[][] facturas = new double[12][6];
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                facturas[i][j] = random.nextDouble() * 250 + 20;
            }
        }
        return facturas;
    }

    private static void calcularImpuestos() {
        for (Contribuyente contribuyente : contribuyentes) {
            double totalIngresos = calcularTotalIngresos(contribuyente.getSueldos());
            double totalDeducciones = calcularTotalDeducciones(contribuyente.getFacturas());
            double baseImponible = totalIngresos - totalDeducciones;
            double[] impuestos = calcularImpuestos(baseImponible);
            contribuyente.setBaseImponible(baseImponible);
            contribuyente.setImpuestoBasico(impuestos[0]);
            contribuyente.setImpuestoExcedente(impuestos[1]);
            contribuyente.setImpuestoTotal(impuestos[2]);
        }
    }

    private static double calcularTotalIngresos(double[] sueldos) {
        double total = 0;
        for (double sueldo : sueldos) {
            total += sueldo;
        }
        return total;
    }

private static double calcularTotalDeducciones(double[][] facturas) {
    double total = 0;
    for (int i = 0; i < 12; i++) {
        for (int j = 0; j < 6; j++) {
            total += facturas[i][j];
        }
    }
    if (total > 5352.97) {
        total = 5352.97;
    }
    return total * MAX_DEDUCCION;
}

private static double[] calcularImpuestos(double baseImponible) {
    double impuestoBasico = 0;
    double impuestoExcedente = 0;
    double impuestoTotal = 0;

    if (baseImponible > 0 && baseImponible <= 11722) {
        impuestoBasico = 0;
        impuestoExcedente = 0;
    } else if (baseImponible > 11722 && baseImponible <= 14930) {
        impuestoBasico = 0;
        impuestoExcedente = 0.05;
    } else if (baseImponible > 14930 && baseImponible <= 19385) {
        impuestoBasico = 160;
        impuestoExcedente = 0.1;
    } else if (baseImponible > 19385 && baseImponible <= 25638) {
        impuestoBasico = 606;
        impuestoExcedente = 0.12;
    } else if (baseImponible > 25638 && baseImponible <= 33738) {
        impuestoBasico = 1356;
        impuestoExcedente = 0.15;
    } else if (baseImponible > 33738 && baseImponible <= 44721) {
        impuestoBasico = 2571;
        impuestoExcedente = 0.2;
    } else if (baseImponible > 44721 && baseImponible <= 59537) {
        impuestoBasico = 4768;
        impuestoExcedente = 0.25;
    } else if (baseImponible > 59537 && baseImponible <= 79388) {
        impuestoBasico = 8472;
        impuestoExcedente = 0.3;
    } else if (baseImponible > 79388 && baseImponible <= 105580) {
        impuestoBasico = 14427;
        impuestoExcedente = 0.35;
    } else if (baseImponible > 105580) {
        impuestoBasico = 23594;
        impuestoExcedente = 0.37;
    }

    impuestoTotal = impuestoBasico + (baseImponible * impuestoExcedente);
    return new double[]{impuestoBasico, impuestoExcedente, impuestoTotal};
}

private static void generarDeclaraciones() {
    for (Contribuyente contribuyente : contribuyentes) {
        System.out.println("Estimado/a " + contribuyente.getNombre());
        System.out.println("Total de ingresos: " + contribuyente.getTotalIngresos());
        System.out.println("Total de deducciones: " + contribuyente.getTotalDeducciones());
        System.out.println("-------------------------------------------------");
        System.out.println("Sus ingresos netos son: " + contribuyente.getBaseImponible());
        System.out.println("*");
        System.out.println("Porcentaje que usted pagará de impuesto: " + contribuyente.getImpuestoExcedente() * 100 + "%");
        System.out.println("-------------------------------------------------");
        System.out.println("Impuesto de Fracción Excedente a pagar: " + contribuyente.getImpuestoExcedente());
        System.out.println("Impuesto de Fracción Básica a pagar")
                }
private static void guardarDeclaraciones() {
    try {
        FileOutputStream fileOut = new FileOutputStream("declaraciones.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(contribuyentes);
        out.close();
        fileOut.close();
        System.out.println("Declaraciones guardadas en el archivo 'declaraciones.ser'.");
    } catch (IOException e) {
        System.out.println("Error al guardar las declaraciones: " + e.getMessage());
    }
}

private static void cargarDeclaraciones() {
    try {
        FileInputStream fileIn = new FileInputStream("declaraciones.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        contribuyentes = (ArrayList<Contribuyente>) in.readObject();
        in.close();
        fileIn.close();
        System.out.println("Declaraciones cargadas desde el archivo 'declaraciones.ser'.");
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al cargar las declaraciones: " + e.getMessage());
    }
}

private static class Contribuyente implements Serializable {
    private String nombre;
    private double[] sueldos;
    private double[][] facturas;
    private double baseImponible;
    private double impuestoBasico;
    private double impuestoExcedente;
    private double impuestoTotal;

    public Contribuyente(String nombre, double[] sueldos, double[][] facturas) {
        this.nombre = nombre;
        this.sueldos = sueldos;
        this.facturas = facturas;
    }

    public String getNombre() {
        return nombre;
    }

    public double[] getSueldos() {
        return sueldos;
    }

    public double[][] getFacturas() {
        return facturas;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public double getImpuestoBasico() {
        return impuestoBasico;
    }

    public void setImpuestoBasico(double impuestoBasico) {
        this.impuestoBasico = impuestoBasico;
    }

    public double getImpuestoExcedente() {
        return impuestoExcedente;
    }

    public void setImpuestoExcedente(double impuestoExcedente) {
        this.impuestoExcedente = impuestoExcedente;
    }

    public double getImpuestoTotal() {
        return impuestoTotal;
    }

    public void setImpuestoTotal(double impuestoTotal) {
        this.impuestoTotal = impuestoTotal;
    }

    public double getTotalIngresos() {
        return calcularTotalIngresos(sueldos);
    }

    public double getTotalDeducciones() {
        return calcularTotalDeducciones(facturas);
    }
}

public static void main(String[] args) {
    int numContribuyentes = obtenerNumeroContribuyentes();
    generarDatosContribuyentes(numContribuyentes);
    calcularImpuestos();
    generarDeclaraciones();
    guardarDeclaraciones();
    cargarDeclaraciones();
}

}





