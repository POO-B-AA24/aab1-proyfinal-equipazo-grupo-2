import java.io.*;
import java.util.ArrayList;
import java.util.Random;

class Contribuyente {
    private String nombre;
    private double[] sueldos;
    private double[][] facturas;
    private double baseImponible;
    private double impuestoBasico;
    private double impuestoExcedente;
    private double impuestoExcedentePagar;
    private double impuestoTotal;
    private double iess;
    private double retornoImpuestos;
    private double devolucion;

    public Contribuyente(String nombre) {
        this.nombre = nombre;
        this.sueldos = generarSueldos();
        this.facturas = generarFacturas();
    }

    private double[] generarSueldos() {
        double[] sueldos = new double[12];
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            sueldos[i] = random.nextDouble() * (500 + (4000 - 500));
        }
        return sueldos;
    }

    private double[][] generarFacturas() {
        double[][] facturas = new double[12][6];
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                facturas[i][j] = random.nextDouble() * (20 + (270 - 20));
            }
        }
        return facturas;
    }

    public void calcularImpuestos(double[] impuestosPorRango) {
        double totalIngresos = 0;
        double totalDeducciones = 0;
        for (double sueldo : sueldos) {
            totalIngresos += sueldo;
        }
        for (double[] mes : facturas) {
            for (double gasto : mes) {
                totalDeducciones += gasto;
            }
        }
        if (totalDeducciones > 5352.97) {
            totalDeducciones = 5352.97;
        }
        totalDeducciones *= 0.18;
        baseImponible = totalIngresos - totalDeducciones;
        iess = totalIngresos * 0.1145;
        baseImponible -= iess;

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
        }         } else if (baseImponible > 33738 && baseImponible <= 44721) {
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

        impuestoExcedentePagar = baseImponible * impuestoExcedente;
        impuestoTotal = impuestoBasico + impuestoExcedentePagar;

        if (baseImponible < 0) {
            devolucion = Math.abs(baseImponible);
        } else {
            devolucion = 0;
        }
    }

    public void generarDeclaracion() {
        System.out.println("Estimado/a " + nombre);
        System.out.println("Total de ingresos: " + totalIngresos());
        System.out.println("Total de deducciones: " + totalDeducciones());
        System.out.println("-------------------------------------------------");
        System.out.println("Sus ingresos netos son: " + baseImponible);
        System.out.println("*");
        System.out.println("Porcentaje que usted pagará de impuesto: " + impuestoExcedente);
        System.out.println("-------------------------------------------------");
        System.out.println("Impuesto de Fracción Excedente a pagar: " + impuestoExcedentePagar);
        System.out.println("Impuesto de Fracción Básica a pagar: " + impuestoBasico);
        System.out.println("");
        System.out.println("Total de Impuesto a pagar: " + impuestoTotal);
        System.out.println("-------------------------------------------------");
        System.out.println("Información adicional:");
        System.out.println("Aporte al IESS: " + iess);
        System.out.println("Crédito tributario o devolución (por dividendos Corporativos): " + retornoImpuestos + "$");
        System.out.println("Devolución por Excedente de impuestos: (Ingresos netos negativos) $" + devolucion);
        System.out.println("-------------------------------------------------");
    }

    private double totalIngresos() {
        double total = 0;
        for (double sueldo : sueldos) {
            total += sueldo;
        }
        return total;
    }

    private double totalDeducciones() {
        double total = 0;
        for (double[] mes : facturas) {
            for (double gasto : mes) {
                total += gasto;
            }
        }
        if (total > 5352.97) {
            total = 5352.97;
        }
        return total * 0.18;
    }
}

public class error1 {
    public static void main(String[] args) {
        ArrayList<Contribuyente> contribuyentes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("¿Cuántos contribuyentes desea registrar?");
        int numContribuyentes = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numContrib

