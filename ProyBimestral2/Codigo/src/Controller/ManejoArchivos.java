package Controller;

import Model.EscribirFactura;
import Model.EscribirContribuyente;
import Model.LeerFactura;
import Model.LeerContribuyente;
import java.util.ArrayList;
public class ManejoArchivos {

    public static String guardarFacturasEnArchivo(ArrayList<Factura> facturas, int contadorUsuarios, Contribuyente usuario) {
        EscribirFactura escribirFactura = new EscribirFactura(facturas);
        String archivoFacturas = "factura" + contadorUsuarios + "-" + usuario.getName() + ".dat";
        escribirFactura.guardarFacturasEnArchivo(archivoFacturas);
        System.out.println("Factura guardada en " + archivoFacturas);
        return archivoFacturas;
    }

    public static ArrayList<Factura> leerFacturasDeArchivo(String archivoFacturas) {
        LeerFactura leerFactura = new LeerFactura();
        ArrayList<Factura> facturasLeidas = leerFactura.leerFacturasDeArchivo(archivoFacturas);
        System.out.println("Facturas leídas de " + archivoFacturas);
        return facturasLeidas;
    }

    public static void guardarContribuyente(Contribuyente usuario) {
        EscribirContribuyente escribirContribuyente = new EscribirContribuyente(usuario);
        String archivoUsuario = usuario.getName() + ".dat";
        escribirContribuyente.guardarUsuarioEnArchivo(archivoUsuario);
        System.out.println("Usuario guardado en " + archivoUsuario);
    }

    public static Contribuyente leerContribuyente(String nombreArchivo) {
        LeerContribuyente leerContribuyente = new LeerContribuyente();
        return leerContribuyente.leerUsuarioDesdeArchivo(nombreArchivo + ".dat");
    }
}
