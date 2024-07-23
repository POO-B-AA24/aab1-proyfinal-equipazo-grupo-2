package Controller;

import java.io.Serializable;
import java.util.ArrayList;

public class Contribuyente implements Serializable {

    private int id;
    private String name;
    private double[] sueldosMensuales;
    private ArrayList<Factura> facturas;
    private double totalIngresosAnuales;
    private double totalDeduccionesAnuales;
    private double refund;
    private double dividend;
    private double dividendTaxRate;
    private double retornoInversor;
    private double impuestos;
    private double impuestosAPagar;
    private String reporte;
    private String direccion;
    private String cedula;

    public Contribuyente(int id, String name, double[] sueldosMensuales, String direccion, String cedula) {
        this.id = id;
        this.name = name;
        this.sueldosMensuales = sueldosMensuales;
        this.direccion = direccion;
        this.cedula = cedula;
    }

    public void calcularImpuestos() {
        double totalSueldos = 0;
        for (double sueldo : sueldosMensuales) {
            totalSueldos += sueldo;
        }

        double totalDeducciones = 0;
        for (Factura factura : this.facturas) {
            factura.calcularGasto();
        }

        this.totalIngresosAnuales = totalSueldos * 12;
        this.totalDeduccionesAnuales = totalDeducciones * 12;

        this.refund = (this.totalIngresosAnuales - this.totalDeduccionesAnuales < 0) ? Math.abs(this.totalIngresosAnuales - this.totalDeduccionesAnuales) : 0;
        // Le hacemos un retorno si esq su base imponible es negativa (es decir sus deducciones son mayores a sus ingresos)

        this.retornoInversor = this.dividend * dividendTaxRate;

        // Beneficio tributario: 1. refund (reembolso) 2. retorno de dividendo (inversor)
        // Calcular impuestos según la tabla de impuestos del 2023
        if (totalIngresosAnuales <= 11722) {
            this.impuestos = 0;
        } else if (totalIngresosAnuales <= 14930) {
            this.impuestos = (totalIngresosAnuales - 11722) * 0.05;
        } else if (totalIngresosAnuales <= 19385) {
            this.impuestos = 160 + (totalIngresosAnuales - 14930) * 0.1; // Basico y fraccion excedente ya contemplado
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
        this.impuestosAPagar = impuestos - totalDeduccionesAnuales;

    }

    public void generarReporteImpuestos() {
        this.reporte = "Reporte de impuestos:\n"
                + "Estimado/a " + this.name + "\n"
                + "Direccion: " + this.direccion + "\n"
                + "Cedula: " + this.cedula + "\n"
                + "Total de ingresos: " + this.totalIngresosAnuales + "\n"
                + "Total de deducciones: " + this.totalDeduccionesAnuales + "\n"
                + "-------------------------------------------------\n"
                + "Sus ingresos netos son: " + (this.totalIngresosAnuales - this.totalDeduccionesAnuales) + "\n"
                + "*\n"
                + "-------------------------------------------------\n"
                + "Informacion adicional (Contempla Empleados, Inversores y Reembolsos):\n"
                + "Aporte al IESS:\n" + totalIngresosAnuales * 0.1145 + "\n" // El aporte el iess es del 11.45% para personas normales
                + "Credito tributario o devolucion (por dividendos Corporativos, para Inversor):\n" + this.retornoInversor + "\n"
                + "Devolucion por Excedente de impuestos: (Ingresos netos negativos para persona Natural):\n" + this.refund + "\n"
                + "-------------------------------------------------" + "\n"
                + "Total de Impuesto a pagar: $" + (this.impuestosAPagar - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145); //subsidio por contribuir al IESS
    } // para evitar crear atributos innecesarios (son solo requerimientos adicionales, he procesado el aporte al IESS y el total de impuesto a pagar en la misma linea, sin embargo estos datos no son cruciales para ningun otro calculo mas que para presentar un reporte detallado.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacturas(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }
    

    public void addFactura(Factura factura) {
        if (this.facturas == null) {
            this.facturas = new ArrayList<>();
        }
        this.facturas.add(factura);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReporteImpuestos() {
        return reporte;
    }

    public double[] getSueldosMensuales() {
        return sueldosMensuales;
    }
    public double getSueldoAnual(){
        double suel=0;
        for (double sueldosMensuale : sueldosMensuales) {
            suel += sueldosMensuale;
        }
        return suel;
    }

    public void setSueldosMensuales(double[] sueldosMensuales) {
        this.sueldosMensuales = sueldosMensuales;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public void setDividendTaxRate(double dividend) {
        this.dividendTaxRate = dividend;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getReporte() {
        return reporte;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCedula() {
        return cedula;
    }
    

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contribuyente{");
        sb.append("Nombre=").append(name);
        sb.append("\n Sueldos Mensuales:\n {");
        for (double sueldosMensuale : sueldosMensuales) {
            sb.append(sueldosMensuale).append(",");
        }
        sb.append("}\n Facturas=:\n{");
        int i = 1;
        for (Factura facs : facturas) {
            sb.append(facs.toString(i)).append("\n");
            i++;
        }

        sb.append("\n}, Reporte de impuestos={").append(reporte);

        sb.append("}\n*************");
        return sb.toString();
    }

}
