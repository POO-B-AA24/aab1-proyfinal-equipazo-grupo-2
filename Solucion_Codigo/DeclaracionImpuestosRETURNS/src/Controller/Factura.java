package Controller;

import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import Model.*;

public class Factura implements Serializable {
    String categoria;
    double monto;
    String direccion;

    public Factura(String categoria, double monto, String direccion) {
        this.categoria = categoria;
        this.monto = monto;
        this.direccion = direccion;
    }
    

    public void escribirADat(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("facturas.dat"));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



