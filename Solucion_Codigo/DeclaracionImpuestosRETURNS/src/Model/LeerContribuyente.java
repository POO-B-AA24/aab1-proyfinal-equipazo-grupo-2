// leerEmp.close();
// ál metodaso MAIN:¨writeFactura.close();
package Model;
import Controller.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class LeerContribuyente {
    private Contribuyente contribuyentes;
  
    
    public LeerContribuyente() { // Bob El constructor: Ahora no es necesario inicializar el array. 
        //this.archivasoPath = archivasoPath;
    }
    
     public Contribuyente leerUsuarioDesdeArchivo(String nombreArchivo) {
        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Contribuyente contrib = (Contribuyente) in.readObject();
            in.close();
            fileIn.close();
            
            return contrib;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contribuyentes;
    }


}