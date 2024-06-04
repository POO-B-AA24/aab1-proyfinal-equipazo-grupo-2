// leerEmp.close();
// ál metodaso MAIN:¨writeFactura.close();
package Model;
import Controller.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LeerFactura {
    private ArrayList<Factura> listFacturas;
    private ObjectInputStream leerFactura;
    private FileInputStream archivasoPath;
    
    public LeerFactura() { // Bob El constructor: Ahora no es necesario inicializar el array. 
        //this.archivasoPath = archivasoPath;
    }
    
     public ArrayList<Factura> leerFacturasDeArchivo(String nombreArchivo) {
        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Factura> facturas = (ArrayList<Factura>) in.readObject();
            in.close();
            fileIn.close();
            
            return facturas;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}