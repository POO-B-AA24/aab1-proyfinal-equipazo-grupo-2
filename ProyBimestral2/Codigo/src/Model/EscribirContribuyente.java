package Model;

import Controller.Contribuyente;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
public class EscribirContribuyente  implements Serializable{
        private Contribuyente usuarito;

    public EscribirContribuyente(Contribuyente usuarito) {
        this.usuarito = usuarito;
    }

    
        public void guardarUsuarioEnArchivo(String nombreArchivo) {
        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.usuarito);
            out.close();
            fileOut.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

