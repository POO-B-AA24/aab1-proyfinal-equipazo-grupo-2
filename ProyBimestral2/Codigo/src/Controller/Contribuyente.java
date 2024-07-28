package Controller;

import java.io.Serializable;
import java.util.ArrayList;

public class Contribuyente implements Serializable {

    private int id;
    private String name;
    private double[] sueldosMensuales;
    private ArrayList<Factura> facturas;
    private double totalIngresosAnuales;
    private double totalGastosAnuales; // gastos es solo un dato informativo 
    private double totalIngresosNetos; // gastos es solo un dato informativo 
    private double totalDeduccionesAnuales;
    private double aporteAlIess;
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
        int opc = 0;

        double totalSueldos = 0;
        for (double sueldo : sueldosMensuales) {
            totalSueldos += sueldo;
        }

        double totalDeducciones = 0;
        double totalGastos = 0;

        for (Factura factura : this.facturas) { // POLIMORFISMO
            factura.setContribuyenteId(this.getId());
            totalGastos += factura.getMonto();
            totalDeducciones += factura.calcularGasto();
        }

        this.totalIngresosAnuales = totalSueldos * 12;
        this.totalGastosAnuales = totalGastos * 12;
        this.aporteAlIess = (GeneradorDatos.generarDecision().equalsIgnoreCase("Si") ? totalIngresosAnuales * 0.1145 : 0);

        // Esta linea es para interrogar si el contribuyente le paga o no al IESS. En caso no, no tendra el subsidio del IESS. ( El aporte al IESS es: totalIngresosAnuales * 0.1145)
        this.totalIngresosNetos = this.totalIngresosAnuales - this.totalGastosAnuales - this.aporteAlIess;

        this.totalDeduccionesAnuales = totalDeducciones * 12;

        this.retornoInversor = this.dividend * dividendTaxRate;
        // Beneficio tributario: 1. refund (reembolso) 2. retorno de dividendo (inversor)
        // Calcular impuestos según la tabla de impuestos del 2023
        if ((totalIngresosNetos) <= 11722) { // Basico y fraccion excedente ya contemplado en cada caso
            this.impuestos = 0;
            opc = 0;
        } else if (totalIngresosNetos <= 14930) {
            this.impuestos = ((totalIngresosNetos - 11722) * 0.05);
            opc = 1;
        } else if (totalIngresosNetos <= 19385) {
            this.impuestos = 160 + ((totalIngresosNetos - 14930) * 0.1);
            opc = 2;
        } else if (totalIngresosNetos <= 25638) {
            this.impuestos = 606 + ((totalIngresosNetos - 19385) * 0.12);
            opc = 3;
            this.mensaje = crearMensaje(3);
        } else if (totalIngresosNetos <= 33738) {
            this.impuestos = 1356 + ((totalIngresosNetos - 25638) * 0.15);
            opc = 4;
        } else if (totalIngresosNetos <= 44721) {
            this.impuestos = 2571 + ((totalIngresosNetos - 33738) * 0.2);
            opc = 5;
        } else if (totalIngresosNetos <= 59537) {
            this.impuestos = 4768 + ((totalIngresosNetos - 44721) * 0.25);
            opc = 6;
        } else if (totalIngresosNetos <= 79388) {
            this.impuestos = 8472 + ((totalIngresosNetos - 59537) * 0.3);
            opc = 7;
        } else if (totalIngresosNetos <= 105580) {
            this.impuestos = 14427 + ((totalIngresosNetos - 79388) * 0.35);
            opc = 8;
        } else {
            this.impuestos = 23594 + ((totalIngresosAnuales - 105580) * 0.37);
            opc = 9;
        }
        // Si los impuestos deducidos son menores a 0. Entonces no hay impuestos que pagar, han sido deducidos a full. De lo contrario, se paga la cantidad de impuestos sin deducciones.
        this.impuestosAPagar = (this.impuestos - this.totalDeduccionesAnuales < 0) ? 0 : this.impuestos - this.totalDeduccionesAnuales;
        this.refund = (this.totalIngresosNetos - this.impuestosAPagar < 0) ? Math.abs(this.impuestosAPagar) : 0;
        // Le hacemos una devolucion si esq su base imponible es negativa (es decir sus gastos son mayores a sus ingresos)
        this.impuestosAPagar = (this.refund >= this.impuestosAPagar) ? 0 : this.impuestosAPagar-this.refund;
        this.impuestosAPagar = (this.retornoInversor >= this.impuestosAPagar) ? 0 : this.impuestosAPagar- this.retornoInversor;
        // mas chequeos para que nunca los impuestos sean negativos
        
// Maybe there I've found a tax-loophole, where refund for people can advantage of not having its deductions offset the taxes. So that the refund is higher (as deductions are not considered).
        this.mensaje = crearMensaje(opc);

    }

    private String crearMensaje(int caso) {
        switch (caso) {
            case 0 -> {
                return "\n No está sujeto al pago de impuesto a la renta debido a que su base imponible es menor o igual a 11,722 USD. \n";
            }
            case 1 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar  ) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 5% sobre la fracción excedente de 11,722 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 0.0$ (Básico) + " + (totalIngresosNetos - 11722) * 0.05 + "$ (Excedente)(5% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 2 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 10% sobre la fracción excedente de 14,930 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 160$ (Básico) + " + (totalIngresosNetos - 14930) * 0.1 + "$ (Excedente)(10% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 3 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 12% sobre la fracción excedente de 19,385 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 606$ (Básico) + " + (totalIngresosNetos - 19385) * 0.12 + "$ (Excedente)(12% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 4 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 15% sobre la fracción excedente de 25,638 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 1 356$ (Básico) + " + (totalIngresosNetos - 25638) * 0.15 + "$ (Excedente)(15% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 5 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 20% sobre la fracción excedente de 33,738 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 2 571$ (Básico) + " + (totalIngresosNetos - 33738) * 0.2 + "$ (Excedente)(20% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 6 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 25% sobre la fracción excedente de 44,721 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 4 768$ (Básico) + " + (totalIngresosNetos - 44721) * 0.25 + "$ (Excedente)(25% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 7 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 30% sobre la fracción excedente de 59,537 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 8 472$ (Básico) + " + (totalIngresosNetos - 59537) * 0.3 + "$ (Excedente)(30% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 8 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 35% sobre la fracción excedente de 79,388 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 14 427$ (Básico) +" + (totalIngresosNetos - 79388) * 0.35 + "$ (Excedente)(35% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
            case 9 -> {
                return "\n Queridísimo usuario, usted pagará $" + (this.impuestosAPagar) + "\nPorque: IMPUESTO BÁSICO al rango de sus Ingresos + Impuesto EXCEDENTE a sus Ingresos (es decir, Sus ingresos - límite inferior del rango de sus ingresos) = IMPUESTO final\nNOTA: el monto final de IMPUESTOS EXCEDENTE respecto a sus ingresos es multiplicado a una tasa del 37% sobre la fracción excedente de 105,580 USD. Según la Tabla de Gravámenes a Servidores del Sector Público de la República del Ecuador Vigente al 2024\nEs decir: 23 594$ (Básico) +" + (totalIngresosNetos - 105580) * 0.37 + "$ (Excedente)(37% ya aplicado a Excedente) = $" + this.impuestos +" ➖ Beneficios tributarios=\n IMPUESTOS FINALES:\n "+this.impuestosAPagar + "$.\n";
            }
        }
        return "error al establecer mensaje";
    }

    public void generarReporteImpuestos() {
        this.reporte = """
                       REPORTE DE IMPUESTOS:
                       ***
                       Estimado/a """ + this.name + "\n"
                + "Direccion: " + this.direccion + "\n"
                + "Cedula: " + this.cedula + "\n"
                + "------------ DECLARACION INGRESOS ------------" + "\n"
                + "Total de ingresos: " + this.totalIngresosAnuales + "\n"
                + "➖ Total de gastos: " + this.totalGastosAnuales + "\n"
                + "➖ Aporte al IESS:\n" + this.aporteAlIess + "\n" // El aporte el iess es del 11.45% para personas normales
                + "-------------------------------------------------\n"
                + "Sus ingresos netos (sin impuestos) son: " + (totalIngresosNetos) + "\n"
                + "***\n"
                + "------------ PAGO DE IMPUESTOS ------------" + "\n"
                + "Total de impuestos: " + this.impuestos + "\n"
                + "Sus ingresos netos (debitando impuestos) son: " + (totalIngresosNetos - this.impuestos) + "\n"
                + "➖ Total de deducciones: " + this.totalDeduccionesAnuales + "\n"
                + "-------------------------------------------------\n"
                + "Sus ingresos pre-FINALES (con impuestos y deducciones): " + (this.totalIngresosNetos - (this.impuestos - this.totalDeduccionesAnuales)) + "\n"
                + "***\n"
                + "-------------------------------------------------\n"
                + "Deducciones adicionales (Contempla 🥩BENEFICIOS TRIBUTARIOS🥩 para: Empleados, Inversores y Reembolsos):\n"
                + "Credito tributario o devolucion (por dividendos Corporativos, para Inversor):\n" + this.retornoInversor + "\n"
                + "Devolucion por Excedente de impuestos: (Ingresos netos negativos para persona Natural):\n" + this.refund + "\n"
                + "Subsidio tributario (por contribuir al IESS): \n" + this.aporteAlIess + "\n"
                + "-------------------------------------------------" + "\n"
                + "-------------------------------------------------" + "\n"
                + "Total de Impuesto a pagar: $" + (this.impuestosAPagar ) + "\n" // lo ultimo es el subsidio por contribuir al IESS
                + "Nota: INGRESOS FINALES son: Sus ingresos ➖ Sus gastos ➖ Impuestos totales\n"
                + "Sus ingresos FINALES (contempla todo anteriormente en este reporte) " + (this.totalIngresosNetos - (this.impuestosAPagar ));
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

    /*public String getReporteImpuestos() {
        return reporte;
    }*/
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

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
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
