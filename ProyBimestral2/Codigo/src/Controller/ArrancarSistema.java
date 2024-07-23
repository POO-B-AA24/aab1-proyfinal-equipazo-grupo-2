package Controller;

/**
 *
 * @author MRodzDirect 😉 <Organico>
 */
import java.util.ArrayList;
import java.util.Scanner;
import Model.DataBaseManager;

public class ArrancarSistema {

    public static void comienza() {
        ArrayList<Contribuyente> usuarios = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean continuar = true;
        int contadorUsuarios = 0;

        // Crear las tablas de la database si no existen
        DataBaseManager.createTables();

        while (continuar) {
            Contribuyente contribuyente = OperacionesContribuyente.crearYProcesarContribuyente(contadorUsuarios);
            usuarios.add(contribuyente);
            contadorUsuarios++;

            System.out.println("¿Desea ingresar otro Contribuyente? (Si / No)");
            if (!in.next().equalsIgnoreCase("Si")) {
                continuar = false;
            }
        }
        
        // Leer desde archivo
       // OperacionesContribuyente.leerYMostrarContribuyentes(usuarios);

        // ahora desde sql:
        // Retrieve Contribuyente and Factura data from the database. Oh yeah!
        OperacionesContribuyente.leerDesdeDBContribuyentes(usuarios);
    }

}
