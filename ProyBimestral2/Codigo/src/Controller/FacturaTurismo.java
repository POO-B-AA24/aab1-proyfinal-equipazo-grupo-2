package Controller;
public class FacturaTurismo extends Factura {

    public FacturaTurismo(double monto) {
        super(monto);
    }
    public FacturaTurismo(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    @Override
    public double calcularGasto() {
        return getMonto() * 0.05;
    }
}
