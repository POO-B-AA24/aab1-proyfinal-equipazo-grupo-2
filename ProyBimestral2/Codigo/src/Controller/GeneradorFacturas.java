package Controller;
import java.util.ArrayList;

public class GeneradorFacturas {
public static ArrayList<Factura> generarFacturas() {
    ArrayList<Factura> facturas = new ArrayList<>();
    int facturaId = 1; // Inicia desde el ID 1
    
    // Todas son las facturas de 1 solo mes.
    
    for (int j = 0; j < GeneradorDatos.generarCantidadDeFacturas(); j++) {
        
        Factura fac1 = GeneradorDatos.generarCategoriasObj();
        fac1.setMonto(GeneradorDatos.generarGasto());
        fac1.setId(facturaId++);
        facturas.add(fac1);
        
    }
    return facturas;
}
// may perform an enhancement
// Pruebas:
//        Factura alimentacion = new FacturaAlimentacion(GeneradorDatos.generarGasto());
//        alimentacion.setId(facturaId++);
//        facturas.add(alimentacion);
}