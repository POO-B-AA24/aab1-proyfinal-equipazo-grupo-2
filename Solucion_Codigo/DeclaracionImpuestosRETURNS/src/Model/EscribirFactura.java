package Model;

import Controller.Factura;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class EscribirFactura implements Serializable{

    ObjectOutputStream writeFactura; // Clase para escribir objetos

    String path; // Clase para obtener ruta de archivos

    ArrayList<Factura> facturas;

    public EscribirFactura(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }

      public void guardarFacturasEnArchivo(String nombreArchivo) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.facturas);
            out.close();
            fileOut.close();
            System.out.println("Factura guardada en " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
