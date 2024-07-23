package Model;

import Controller.Contribuyente;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class LeerContribuyente implements Serializable{
    private Contribuyente contribuyentes;
  
    
    public LeerContribuyente() { 
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
