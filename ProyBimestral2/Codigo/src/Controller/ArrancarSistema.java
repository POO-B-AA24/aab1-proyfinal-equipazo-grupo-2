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
        ArrayList<Contribuyente> usuarios = new ArrayList<>(); // esto es solo para leer al final desde la base de datos
        Scanner in = new Scanner(System.in);
        boolean continuar = true;
        //int contadorUsuarios = (OperacionesContribuyente.getLastContribuyenteId() !=0 ) ?  OperacionesContribuyente.getLastContribuyenteId() + 1 : 0;
        // Crear las tablas de la database si no existen
        if (!DataBaseManager.databaseExists()) {
            DataBaseManager.createTables();
        }
        int contadorUsuarios = OperacionesContribuyente.getLastContribuyenteId() + 1;

        while (continuar) {
            Contribuyente contribuyente = OperacionesContribuyente.crearYProcesarContribuyente(contadorUsuarios);
            usuarios.add(contribuyente);
            contadorUsuarios++;

            System.out.println("¿Desea ingresar otro Contribuyente? (Si / No)");
            if (!in.next().equalsIgnoreCase("Si")) {
                continuar = false;
            }
        }
        //Todo eso desde SQL
        // Retrieve Contribuyente and Factura data from the database. Oh yeah!
        OperacionesContribuyente.leerDesdeDBContribuyentes(usuarios);
    }

}
