package Controller;
import java.util.ArrayList;

public class GeneradorFacturas {
public static ArrayList<Factura> generarFacturas() {
    ArrayList<Factura> facturas = new ArrayList<>();
    int facturaId = 1; // Start with ID 1
    for (int j = 0; j < 12; j++) {
        Factura alimentacion = new Alimentacion(GeneradorDatos.generarGasto());
        alimentacion.setId(facturaId++);
        facturas.add(alimentacion);

        Factura vivienda = new Vivienda(GeneradorDatos.generarGasto());
        vivienda.setId(facturaId++);
        facturas.add(vivienda);

        Factura educacion = new Educacion(GeneradorDatos.generarGasto());
        educacion.setId(facturaId++);
        facturas.add(educacion);

        Factura turismo = new Turismo(GeneradorDatos.generarGasto());
        turismo.setId(facturaId++);
        facturas.add(turismo);

        Factura salud = new Salud(GeneradorDatos.generarGasto());
        salud.setId(facturaId++);
        facturas.add(salud);
    }
    return facturas;
}

}