package Controller;
public class Alimentacion extends Factura {

    public Alimentacion(double monto) {
        super(monto);
    }
    public Alimentacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    @Override
    public double calcularGasto() {
        return getMonto() * 0.005;
    }
}
