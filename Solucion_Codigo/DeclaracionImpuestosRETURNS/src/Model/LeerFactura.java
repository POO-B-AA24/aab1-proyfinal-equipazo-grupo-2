
// ál metodaso MAIN:¨writeFactura.close();

package Model;
import Controller.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LeerFactura {
    ObjectInputStream readFactura; // Clase para leer objetos
    ArrayList<Controller.Factura> listaFacturas;
    FileOutputStream path; // Clase para obtener ruta de archivos
    public LeerFactura(FileOutputStream path) {
        this.path = path;
    }
}