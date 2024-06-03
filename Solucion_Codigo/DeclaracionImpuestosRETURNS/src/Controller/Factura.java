package Controller;

import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import Model.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factura implements Serializable {

    String categoria;
    double monto;
    String direccion;

    public Factura(String categoria, double monto, String direccion) {
        this.categoria = categoria;
        this.monto = monto;
        this.direccion = direccion;
    }

    public void escribirPorPrimeraVez(int i) {
        EscribirFactura write = new EscribirFactura(this, i);
        write.escribirFacturasa();
    }

    public Factura leerFactura(int i) {
       Factura t = null;
        try {
            FileInputStream path = new FileInputStream("Factura Contribuyente" + i + ".dat");
            LeerFactura read = new LeerFactura(path);
            t= read.leerFacturasa();
            return t;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
        
    }

    public String getCategoria() {
        return categoria;
    }

    public double getMonto() {
        return monto;
    }

   
    
    
    
}
