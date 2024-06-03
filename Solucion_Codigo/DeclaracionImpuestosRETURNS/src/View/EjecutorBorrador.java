package View;
import Controller.Factura;
import Controller.Persona;
import java.util.ArrayList;
import java.util.Random;
public class EjecutorBorrador {

    public static void main(String[] args) {
        ArrayList<Persona> personas = new ArrayList<>();

        for (Persona persona : personas) {
            persona.setFactura(new Factura("Pablo", 1050.0, "", "Quito"));
        }
    }

    public static String generarDireccion() {
        Random random = new Random();
        String[] direcciones = {"Loja", "El oro", "Quito", "Cuenca", "Guayaquil", "Zapotillo", "Ambato", "Manabí", "Esmeraldas", "Zamora"};
        return direcciones[random.nextInt(10)];
    }

    public static String generarCedulas() {
        Random random = new Random();
        String[] ced = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        String cedula = ced[random.nextInt(24)];
        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            cedula += digito;
        }
        return cedula;
    }
}

/*
Codigo java donde: El objetivo del proyecto es desarrollar un sistema que facilite la declaración de impuestos a la renta para calcular la rebaja correspondiente. Este sistema consultará la tabla de impuestos a la renta del 2023 para personas naturales. La información de entrada serán los sueldos mensuales y el arrayList de facturas leidas por el archivo.dat de las facturas, considerando categorias como Vivienda, Educación, Alimentación, Vestimenta, Salud y Turismo, donde un usuario puede tener multiples facturas; el programa debe seguir las reglas y montos máximos establecidos por la normativa fiscal (ver tabla de impuestos).

Características por considerar:

· Registro de información: Diseñe el modelado de metodos que permitan a la clase usuario ingresar los sueldos mensuales y lean desde multiples archivos serializados  las facturas generadas y de acuerdo a la categoría (atributos de la clase factura), como Vivienda, Educación, Alimentación, Vestimenta, Salud y Turismo se debe de efectuar la debida deducción de impuestos (4% para Vivienda, para Educación 3%, para alimentación 0.5%, para Vestimenta nada, para Salud 10%, para turismo 5%).

· Cálculo de impuestos: Desarrollar un sistema que consulte la tabla de impuestos a la renta del 2023 para personas naturales y realice el cálculo de los impuestos anuales considerando las deducciones permitidas por las facturas generadas y los sueldos mensuales.

· Generación de declaración hacia multiples archivos serializados (cada uno será de un contribuyente con sus respectivas facturas): Crear la funcionalidad que genere una declaración de impuestos detallada que muestre el cálculo de los impuestos a pagar o la devolución correspondiente, en base a las deducciones y la tabla de impuestos.
La tabla de impuesto es la siguiente:

Para el año 2023 la tabla de Impuesto a la Renta es la siguiente:					
-------------------------------------------------------------------------------------------------------------------					
AÑO 2023 - En Dolares					
Fraccion Basica	Exceso hasta	Impuesto Fraccion Basica	% Impuesto Fraccion Excedente		
$ 0.00	$ 11.722	$ 0	0%		
$ 11.722	$ 14.930	$ 0	5%		
$ 14.930	$ 19.385	$ 160	10%		
$ 19.385	$ 25.638	$ 606	12%		
$ 25.638	$ 33.738	$ 1.356	15%		
$ 33.738	$ 44.721	$ 2.571	20%		
$ 44.721	$ 59.537	$ 4.768	25%		
$ 59.537	$ 79.388	$ 8.472	30%		
$ 79.388	$ 105.580	$ 14.427	35%		
$ 105.580	en adelante	$ 23.594	37%

· Validación de datos: Implementar validaciones para garantizar que los sueldos mensuales y las facturas ingresadas por el usuario estén dentro de los límites establecidos y cumplan con las reglas fiscales.
*/

/*
import java.io.*;
import java.util.ArrayList;

class Usuario implements Serializable {
    private double[] sueldosMensuales;
    private ArrayList<Factura> facturas;

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
                    totalDeducciones += factura.getMonto() * 0.04;
                    break;
                case "Educación":
                    totalDeducciones += factura.getMonto() * 0.03;
                    break;
                case "Alimentación":
                    totalDeducciones += factura.getMonto() * 0.005;
                    break;
                case "Vestimenta":
                    // No se aplica deducción
                    break;
                case "Salud":
                    totalDeducciones += factura.getMonto() * 0.1;
                    break;
                case "Turismo":
                    totalDeducciones += factura.getMonto() * 0.05;
                    break;
            }
        }

        double totalIngresosAnuales = totalSueldos * 12;
        double totalDeduccionesAnuales = totalDeducciones * 12;

        // Calcular impuestos según la tabla de impuestos del 2023
        double impuestos = 0;
        if (totalIngresosAnuales <= 11722) {
            impuestos = 0;
        } else if (totalIngresosAnuales <= 14930) {
            impuestos = (totalIngresosAnuales - 11722) * 0.05;
        } else if (totalIngresosAnuales <= 19385) {
            impuestos = 160 + (totalIngresosAnuales - 14930) * 0.1;
        } else if (totalIngresosAnuales <= 25638) {
            impuestos = 606 + (totalIngresosAnuales - 19385) * 0.12;
        } else if (totalIngresosAnuales <= 33738) {
            impuestos = 1356 + (totalIngresosAnuales - 25638) * 0.15;
        } else if (totalIngresosAnuales <= 44721) {
            impuestos = 2571 + (totalIngresosAnuales - 33738) * 0.2;
        } else if (totalIngresosAnuales <= 59537) {
            impuestos = 4768 + (totalIngresosAnuales - 44721) * 0.25;
        } else if (totalIngresosAnuales <= 79388) {
            impuestos = 8472 + (totalIngresosAnuales - 59537) * 0.3;
        } else if (totalIngresosAnuales <= 105580) {
            impuestos = 14427 + (totalIngresosAnuales - 79388) * 0.35;
        } else {
            impuestos = 23594 + (totalIngresosAnuales - 105580) * 0.37;
        }

        return impuestos - totalDeduccionesAnuales;
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

    public double getMonto() {
        return monto;
    }
}

public class DeclaracionImpuestos implements Serializable {
    public static void main(String[] args) {
        double[] sueldos = {3000, 3500, 4000};
        ArrayList<Factura> facturas = new ArrayList<>();
        facturas.add(new Factura("Vivienda", 500));
        facturas.add(new Factura("Educación", 300);

        Usuario usuario = new Usuario(sueldos, facturas);

        double impuestosAPagar = usuario.calcularImpuestos();
        System.out.println("Impuestos a pagar: $" + impuestosAPagar);
    }
}

*/