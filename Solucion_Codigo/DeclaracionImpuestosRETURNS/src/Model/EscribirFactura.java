package Model;

import Controller.Factura;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EscribirFactura{

    private ObjectOutputStream writeFactura; // Clase para escribir objetos

    private String path; // Clase para obtener ruta de archivos

    private ArrayList<Factura> facturas;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
