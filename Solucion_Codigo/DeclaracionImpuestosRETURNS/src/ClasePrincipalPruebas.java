
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

class Factura {

    private String categoria;
    private double monto;

    public Factura(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getMonto() {
        return monto;
    }
}

class Persona {

    private String nombre;
    private ArrayList<Factura> facturas;
    private double[] sueldos;
    private double totalIngresos;
    private double totalDeducciones;
    private double impBasico;
    private double impExcedente;
    private double impExcedentePagar;
    private double impTotal;
    private double iess;
    private double retornoImpuestos;
    private double refund;

    public Persona(String nombre) {
        this.nombre = nombre;
        this.facturas = new ArrayList<>();
        this.sueldos = new double[12];
    }

    public void ingresarSueldos() {
        double totalIngresos = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Sueldos.dat"))) {
            for (int mes = 0; mes < 12; mes++) {
                sueldos[mes] = ois.readDouble();
                totalIngresos += sueldos[mes];
                System.out.println("El sueldo de este mes es de: " + sueldos[mes]);
            }
            iess = totalIngresos * 0.1145;
            this.totalIngresos = totalIngresos - iess;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ingresarFacturas(String[] categoria, double maxDeductRate) {
        double totalDeducciones = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Facturas.dat"))) {
            for (int mes = 0; mes < 12; mes++) {
                for (int cat = 0; cat < 6; cat++) {
                    System.out.println("Ingrese el total en costo de facturas de " + categoria[cat] + " del mes " + (mes + 1) + ": ");
                    double monto = ois.readDouble();
                    facturas.add(new Factura(categoria[cat], monto));
                    totalDeducciones += monto;
                    System.out.println("El costo de la factura de este mes es de: " + monto);
                }
            }
            if (totalDeducciones > 5352.97) {
                totalDeducciones = 5352.97;
            }
            this.totalDeducciones = totalDeducciones * maxDeductRate;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calcularImpuesto(double baseImponible) {
        if (baseImponible > 33738 && baseImponible <= 44721) {
            impBasico = 2571;
            impExcedente = 0.2;
        } else if (baseImponible > 44721 && baseImponible <= 59537) {
            impBasico = 4768;
            impExcedente = 0.25;
        } else if (baseImponible > 59537 && baseImponible <= 79388) {
            impBasico = 8472;
            impExcedente = 0.3;
        } else if (baseImponible > 79388 && baseImponible <= 105580) {
            impBasico = 14427;
            impExcedente = 0.35;
        } else if (baseImponible > 105580) {
            impBasico = 23594;
            impExcedente = 0.37;
        }
        impExcedentePagar = baseImponible * impExcedente;
        impTotal = impBasico + impExcedentePagar;
    }

    public void generarDeclaracion() {
        System.out.println("Estimado/a " + nombre);
        System.out.println("Total de ingresos: " + totalIngresos);
        System.out.println("Total de deducciones: " + totalDeducciones);
        System.out.println("");
        System.out.println("Sus ingresos netos son: " + (totalIngresos - totalDeducciones));
        System.out.println("*");
        System.out.println("Porcentaje que usted pagara de impuesto: " + impExcedente);
        System.out.println("-------------------------------------------------");
        System.out.println("Impuesto de Fraccion Excedente a pagar: " + impExcedentePagar);
        System.out.println("Impuesto de Fraccion Basica a pagar: " + impBasico);
        System.out.println("");
        System.out.println("Total de Impuesto a pagar: " + impTotal);
        System.out.println("-");
        System.out.println("Informacion adicional:");
        System.out.println("Aporte al IESS: " + iess);
        System.out.println("Credito tributario o devolucion (por dividendos Corporativos): " + retornoImpuestos + "$");
        System.out.println("Devolucion por Excedente de impuestos: (Ingresos netos negativos) $" + refund);
        System.out.println("");
    }

    public void guardarReporte(DecimalFormat df) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Reporte.dat"))) {
            oos.writeUTF("Estimado/a;" + nombre);
            oos.writeUTF("Total de ingresos:;" + df.format(totalIngresos));
            oos.writeUTF("Total de deducciones:;" + df.format(totalDeducciones));
            oos.writeUTF("-------------------------------------------------");
            oos.writeUTF("Sus ingresos netos son:;" + df.format(totalIngresos - totalDeducciones));
            oos.writeUTF("*");
            oos.writeUTF("Porcentaje que usted pagara de impuesto:;" + df.format(impExcedente));
            oos.writeUTF("-------------------------------------------------");
            oos.writeUTF("Impuesto de Fraccion Excedente a pagar:;" + df.format(impExcedentePagar));
            oos.writeUTF("Impuesto de Fraccion Basica a pagar:;" + df.format(impBasico));
            oos.writeUTF("");
            oos.writeUTF("Total de Impuesto a pagar:;" + df.format(impTotal));
            oos.writeUTF("-------------------------------------------------");
            oos.writeUTF("Informacion adicional:");
            oos.writeUTF("Aporte al IESS:;" + df.format(iess));
            oos.writeUTF("Credito tributario o devolucion (por dividendos Corporativos):;" + df.format(retornoImpuestos) + "$");
            oos.writeUTF("Devolucion por Excedente de impuestos: (Ingresos netos negativos); $" + df.format(refund));
            oos.writeUTF("-------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarTaxTable(int año) {
        System.out.println("Tabla de Impuesto a la Renta para Personas Naturales (" + año + "):");
        System.out.println("Fracción Básica - Impuesto Fracción Básica - Impuesto Fracción Excedente");
        System.out.println("Hasta 11.722 - 0 - 0%");
        System.out.println("11.722 - 14.930 - 0 - 5%");
        System.out.println("14.930 - 19.385 - 160 - 10%");
        System.out.println("19.385 - 25.638 - 606 - 12%");
        System.out.println("25.638 - 33.738 - 1.356 - 15%");
        System.out.println("33.738 - 44.721 - 2.571 - 20%");
        System.out.println("44.721 - 59.537 - 4.768 - 25%");
        System.out.println("59.537 - 79.388 - 8.472 - 30%");
        System.out.println("79.388 - 105.580 - 14.427 - 35%");
        System.out.println("Más de 105.580 - 23.594 - 37%");
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(double totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }
    
    
}
class execute{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();
        Persona persona = new Persona(nombre);

        persona.ingresarSueldos();
        persona.ingresarFacturas(new String[]{"Vivienda", "Educacion", "Alimentacion", "Vestimenta", "Salud", "Turismo"}, 0.18);

        double baseImponible = persona.getTotalIngresos() - persona.getTotalDeducciones();
        persona.calcularImpuesto(baseImponible);

        persona.generarDeclaracion();

        System.out.println("Desea obtener y guardar su reporte de la declaracion actual");
        boolean decision = scanner.nextBoolean();
        if (decision) {
            persona.guardarReporte(df);
        }

        System.out.println("Desea conocer la tabla de Impuesto a la Renta para Personas Naturales (2023)? (true o false)");
        boolean showTaxTable = scanner.nextBoolean();
        if (showTaxTable) {
            persona.mostrarTaxTable(2023);
        }
    }
}
