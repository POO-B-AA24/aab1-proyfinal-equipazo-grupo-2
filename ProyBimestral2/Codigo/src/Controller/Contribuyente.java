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
    private String mensaje;

    public Contribuyente(int id, String name, double[] sueldosMensuales, String direccion, String cedula) {
        this.id = id;
        this.name = name;
        this.sueldosMensuales = sueldosMensuales;
        this.direccion = direccion;
        this.cedula = cedula;
        this.facturas = new ArrayList<Factura>();
    }

    public void calcularImpuestos() {
        double totalSueldos = 0;
        for (double sueldo : sueldosMensuales) {
            totalSueldos += sueldo;
        }

        double totalDeducciones = 0;
        for (Factura factura : this.facturas) { // POLIMORFISMO
            factura.setContribuyenteId(this.getId());
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
            this.mensaje = crearMensaje(1);
        } else if (totalIngresosAnuales <= 14930) {
            this.impuestos = (totalIngresosAnuales - 11722) * 0.05;
            this.mensaje = crearMensaje(2);
        } else if (totalIngresosAnuales <= 19385) {
            this.impuestos = 160 + (totalIngresosAnuales - 14930) * 0.1; // Basico y fraccion excedente ya contemplado
            this.mensaje = crearMensaje(3);
        } else if (totalIngresosAnuales <= 25638) {
            this.impuestos = 606 + (totalIngresosAnuales - 19385) * 0.12;
            this.mensaje = crearMensaje(4);
        } else if (totalIngresosAnuales <= 33738) {
            this.impuestos = 1356 + (totalIngresosAnuales - 25638) * 0.15;
            this.mensaje = crearMensaje(5);
        } else if (totalIngresosAnuales <= 44721) {
            this.impuestos = 2571 + (totalIngresosAnuales - 33738) * 0.2;
            this.mensaje = crearMensaje(6);
        } else if (totalIngresosAnuales <= 59537) {
            this.impuestos = 4768 + (totalIngresosAnuales - 44721) * 0.25;
            this.mensaje = crearMensaje(7);
        } else if (totalIngresosAnuales <= 79388) {
            this.impuestos = 8472 + (totalIngresosAnuales - 59537) * 0.3;
            this.mensaje = crearMensaje(8);
        } else if (totalIngresosAnuales <= 105580) {
            this.impuestos = 14427 + (totalIngresosAnuales - 79388) * 0.35;
            this.mensaje = crearMensaje(9);
        } else {
            this.impuestos = 23594 + (totalIngresosAnuales - 105580) * 0.37;
            this.mensaje = crearMensaje(10);
        }
        this.impuestosAPagar = this.impuestos - this.totalDeduccionesAnuales;
    }

    private String crearMensaje(int caso) {
        switch (caso) {
            case 1:
                return "No está sujeto al pago de impuesto a la renta debido a que su base imponible es menor o igual a 11,722 USD.";
            case 2:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 5% sobre la fracción excedente de 14,930 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 160$ (Básico) +" + (totalIngresosAnuales - 14930) * 0.05 + "$ (Excedente)(5% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 3:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 10% sobre la fracción excedente de 19,385 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 606$ (Básico) +" + (totalIngresosAnuales - 19385) * 0.1 + "$ (Excedente)(10% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 4:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 12% sobre la fracción excedente de 25,638 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 1 356$ (Básico) +" + (totalIngresosAnuales - 25638) * 0.12 + "$ (Excedente)(12%aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 5:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 15% sobre la fracción excedente de 33,738 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 2 571$ (Básico) +" + (totalIngresosAnuales - 33738) * 0.15 + "$ (Excedente)(15% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 6:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 20% sobre la fracción excedente de 44,721 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 4 768$ (Básico) +" + (totalIngresosAnuales - 44721) * 0.2 + "$ (Excedente)(20% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 7:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 25% sobre la fracción excedente de 59,537 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 8 472$ (Básico) +" + (totalIngresosAnuales - 59537) * 0.25 + "$ (Excedente)(25% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 8:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 30% sobre la fracción excedente de 79,388 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 14 427$ (Básico) +" + (totalIngresosAnuales - 79388) * 0.3 + "$ (Excedente)(30% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 9:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 35% sobre la fracción excedente de 105,580 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 23 594$ (Básico) +" + (totalIngresosAnuales - 105580) * 0.35 + "$ (Excedente)(35% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
            case 10:
                return "Queridísimo usuario, usted pagará $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 37% sobre la fracción excedente de 105,580 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 23 594$ (Básico) +" + (totalIngresosAnuales - 105580) * 0.37 + "$ (Excedente)(37% aplicado a Excedente) = $" + (this.impuestos - this.totalDeduccionesAnuales - this.refund - this.retornoInversor - totalIngresosAnuales * 0.1145) + "$.";
        }
        return "error al establecer mensaje";
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

    public double getSueldoAnual() {
        double suel = 0;
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

    public String getMensaje() {
        return mensaje;
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
