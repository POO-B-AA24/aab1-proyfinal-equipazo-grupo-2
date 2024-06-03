package view;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import Controller.*;
import Controller.Factura;

public class Ejec2 {

    public static void main(String[] args) {
        // Generar archivo de facturas
          // Generar archivo de facturas
        ArrayList<Factura> tempFacturas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Factura fac = new Factura(generarCategorias(),generarGasto(),generarDireccion());
            
            fac.escribirADat();
        }
        //
        ArrayList<Persona> personas = new ArrayList<>();

        for (int i = 0; i < 10; i++) { // Cambia 10 a la cantidad de Personas que quieras crear
            Persona persona = new Persona(generarNombres());
            personas.add(persona);
        }

        try {
            // Leer facturas desde un archivo .dat
            ObjectInputStream oisFacturas = new ObjectInputStream(new FileInputStream("facturas.dat"));
            ArrayList<Factura> facturas = (ArrayList<Factura>) oisFacturas.readObject();
            oisFacturas.close();

            // Asignar facturas a cada persona
            for (Persona persona : personas) {
                for (Factura factura : facturas) {
                    persona.setFactura(factura);
                }
                //persona.setSueldoMensual(3000.0, 0);
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
        String[] categorias = {"Alimentacion","Vivienda","Educacion","Salud","Turismo"};
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
