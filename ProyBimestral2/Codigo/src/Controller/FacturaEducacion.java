package Controller;
public class FacturaEducacion extends Factura {

    public FacturaEducacion(double monto) {
        super(monto);
    }

    public FacturaEducacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    
    public double calcularGasto() {
        return getMonto() * 0.03;
    }
}
