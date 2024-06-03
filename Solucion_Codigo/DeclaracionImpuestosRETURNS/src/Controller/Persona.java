package Controller;
import java.util.ArrayList;
public class Persona {
   String nombre;
   double gasto;
   double ingreso;
   double tazaImpositiva;
   ArrayList<Factura> facturas = new ArrayList<>();
    public Persona(String nombre, ArrayList<Factura> facturas) {
        this.nombre = nombre;
        this.facturas = facturas;
    }
    public void setFactura(Factura factura){
            facturas.add(factura);
           // facturas.add(new Factura("Diego", 1150.0, "1102019256", "Guayaquil"));
    }
    public  void generarIngresos(){
        
    }
    
    public  void leerFacturas(){
        
    }
    
}