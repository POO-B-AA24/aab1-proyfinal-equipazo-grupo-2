package Controller;
public class FacturaAlimentacion extends Factura {

    public FacturaAlimentacion(double monto) {
        super(monto);
    }
    public FacturaAlimentacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    @Override
    public double calcularGasto() {
        return getMonto() * 0.005;
    }
}
