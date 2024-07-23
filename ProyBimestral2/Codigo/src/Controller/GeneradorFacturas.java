package Controller;
import java.util.ArrayList;
import View.*;
public class GeneradorFacturas {
    public static ArrayList<Factura> generarFacturas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        for (int j = 0; j < 12; j++) {
            facturas.add(new Salud(GeneradorDatos.generarGasto()));
            facturas.add(new Vivienda(GeneradorDatos.generarGasto()));
            facturas.add(new Educacion(GeneradorDatos.generarGasto()));
            facturas.add(new Turismo(GeneradorDatos.generarGasto()));
            facturas.add(new Alimentacion(GeneradorDatos.generarGasto()));
        }
        return facturas;
    }
}