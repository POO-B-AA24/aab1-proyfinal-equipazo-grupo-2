package Controller;
import java.util.ArrayList;

public class GeneradorFacturas {
public static ArrayList<Factura> generarFacturas() {
    ArrayList<Factura> facturas = new ArrayList<>();
    int facturaId = 1; // Start with ID 1
    for (int j = 0; j < 12; j++) {
        Factura alimentacion = new FacturaAlimentacion(GeneradorDatos.generarGasto());
        alimentacion.setId(facturaId++);
        facturas.add(alimentacion);

        Factura vivienda = new FacturaVivienda(GeneradorDatos.generarGasto());
        vivienda.setId(facturaId++);
        facturas.add(vivienda);

        Factura educacion = new FacturaEducacion(GeneradorDatos.generarGasto());
        educacion.setId(facturaId++);
        facturas.add(educacion);

        Factura turismo = new FacturaTurismo(GeneradorDatos.generarGasto());
        turismo.setId(facturaId++);
        facturas.add(turismo);

        Factura salud = new FacturaSalud(GeneradorDatos.generarGasto());
        salud.setId(facturaId++);
        facturas.add(salud);
    }
    return facturas;
}

}