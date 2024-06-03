// leerEmp.close();
// ál metodaso MAIN:¨writeFactura.close();
package Model;
import Controller.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
public class LeerFactura {
    public ArrayList<Factura> listFacturas;
    public ObjectInputStream leerFactura;
    public FileInputStream archivasoPath;
    public LeerFactura(FileInputStream archivasoPath) { // Bob El constructor: Ahora no es necesario inicializar el array. 
        this.archivasoPath = archivasoPath;
    }
    public ArrayList<Factura> leerSerial() {

        listFacturas = new ArrayList<Factura>();
        try {
            leerFactura = new ObjectInputStream(archivasoPath);

            while (true) { // Leemos no con for. Sino con un ciclo que desconoce la cantidad de objetos. BUSCAR UN METODO MAS EFICIENTE PARA FRENAR EL CICLO
                listFacturas.add((Factura) leerFactura.readObject()); // Deserializado desde aqui 
            }

        } catch (EOFException error) { // Este metodo acaba el ciclo, es un hotfix o workaround temporal.

            return listFacturas; // Fin del archivo, devuelve la lista
        } catch (Exception e) {

        } 
        return listFacturas; // Por defecto hay q colcar esto. Pero no es correcto supongo
    }

}