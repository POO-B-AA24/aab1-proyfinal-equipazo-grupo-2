package view;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import Controller.*;
import Controller.Factura;

public class Ejec2 {

    public static void main(String[] args) {
        // Generar archivo de facturas
        ArrayList<Factura> tempFacturas = new ArrayList<>();
        int numFact = 0;
        boolean continuar = true;
        Scanner in = new Scanner(System.in);
        int mes = 0;

        ArrayList<Persona> personas = new ArrayList<>();
        do {
            // Cambia 10 a la cantidad de Personas que quieras crear
            Persona persona = new Persona(generarNombres());
            persona.setSueldoMensual(generarSueldo(), mes);
            personas.add(persona);
            mes++;
            System.out.println("¿Desea ingresar otra persona? (Si / No)");
            if (!in.next().equalsIgnoreCase("Si")) { //nextLine
                continuar = false;
                break;
            }
        } while (continuar);

        do {
            System.out.println("Ingrese los datos de la factura:");
            Factura fac = new Factura(generarCategorias(), generarGasto(), generarDireccion());

            fac.escribirPorPrimeraVez(numFact);

            numFact++;
            System.out.println("¿Desea ingresar otra factura? (Si / No)");
            if (!in.next().equalsIgnoreCase("Si")) { //nextLine
                continuar = false;
                break;
            }
        } while (continuar);
        // una vez  el usuario ingreso n facturas, estan son estaticas.
        //Factura[] facturas = new Factura[tempFacturas.size()]; 
        //facturas = tempFacturas.toArray(facturas);
        //

        try {

            // Asignar facturas a cada persona
            for (Persona persona : personas) {
                persona.setFacturasDesdeObjeto();
                //persona.setSueldoMensual(3000.0, 0);
            }
            for (Persona persona : personas) {
                
            }
            
            // verificar ingresos y deducciones
            for (Persona persona : personas) {
                if (totalIngresos < 0 || totalDeducciones < 0) {
                    System.out.println("Los ingresos y las deducciones no pueden ser negativos.");
                    return;
                }
            }

            // Escribir el reporte de cada persona a un archivo .dat
            ObjectOutputStream oosPersonas = new ObjectOutputStream(new FileOutputStream("personas.dat"));
            oosPersonas.writeObject(personas);
            oosPersonas.close();

            // Leer el reporte de cada persona desde el archivo .dat
            ObjectInputStream oisPersonas = new ObjectInputStream(new FileInputStream("personas.dat"));
            ArrayList<Persona> personasLeidas = (ArrayList<Persona>) oisPersonas.readObject();
            oisPersonas.close();

            // Ahora personasLeidas contiene la información de las declaraciones de impuestos
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

// METODOS AUTOGENERADORES
    public static String generarNombres() {
        Random random = new Random();
        String[] nombres = {"Juan Pablo Landi", "Marco Abarca", "Juan Diego Guerrero", "Ricardo Espinosa", "Alejandro Alvarrez", "David Velez", "Daniel Bustamante", "Nicolas Gallegos", "Miguel Valverde", "Xavier Gonzales"};
        return nombres[random.nextInt(10)];
    }

    public static double generarSueldo() {
        Random random = new Random();
        double sueldo;
        sueldo = 500 + (1500 - 500) * random.nextDouble();
        return sueldo;
    }

    public static double generarGasto() {
        Random random = new Random();
        double sueldo;
        sueldo = 100 + (100 - 500) * random.nextDouble();
        return sueldo;
    }

    public static String generarCategorias() {
        Random random = new Random();
        String[] categorias = {"Alimentacion", "Vivienda", "Educacion", "Salud", "Turismo"};
        return categorias[random.nextInt(5)];
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
