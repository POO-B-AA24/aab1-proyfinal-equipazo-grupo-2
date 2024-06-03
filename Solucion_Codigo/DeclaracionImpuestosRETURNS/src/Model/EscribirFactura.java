package Model;

import Controller.Factura;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EscribirFactura {

    ObjectOutputStream writeFactura; // Clase para escribir objetos

    FileOutputStream path; // Clase para obtener ruta de archivos

    ArrayList<Factura> facturas;

    public EscribirFactura(ObjectOutputStream writeEmpleado, FileOutputStream path, ArrayList<Factura> facturas) {
        this.path = path;
        this.facturas = facturas;
    }

    public void escribirFacturasa() {

        try {

            this.writeFactura = new ObjectOutputStream(path);

            for (Factura fac : facturas) {
                writeFactura.writeObject(fac);
            }
        } catch (Exception e) {
        }

    }

}
