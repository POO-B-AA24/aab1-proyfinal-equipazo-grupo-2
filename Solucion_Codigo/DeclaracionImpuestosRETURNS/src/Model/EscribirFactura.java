package Model;

import Controller.Factura;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EscribirFactura {

    String categorias;
    double monto;
    String direccion;
    int facturaActual; // contador de facturas
    
    ObjectOutputStream writeFactura; // Clase para escribir objetos

    FileOutputStream path; // Clase para obtener ruta de archivos

    ArrayList<Factura> facturas;
    Factura facturaTemp;
    
//ObjectOutputStream writeFactura
    public EscribirFactura(Factura factu, int facturaActual) {
        this.facturaTemp=factu;
        this.facturaActual=facturaActual;
        
    }

    public void escribirFacturasa() {

        try(FileOutputStream fos = new FileOutputStream("Factura Contribuyente"+facturaActual+".dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(facturaTemp);
            oos.close();
        } catch (Exception e) {
        }

    }

}
