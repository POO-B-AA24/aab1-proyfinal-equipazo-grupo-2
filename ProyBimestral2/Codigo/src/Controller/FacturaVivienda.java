package Controller;
public class FacturaVivienda extends Factura {

    public FacturaVivienda(double monto) {
        super(monto);
    }
    public FacturaVivienda(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    public double calcularGasto() {
        return getMonto() * 0.04;
    }
}
