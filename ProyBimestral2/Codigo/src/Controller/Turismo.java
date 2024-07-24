package Controller;
public class Turismo extends Factura {

    public Turismo(double monto) {
        super(monto);
    }
    public Turismo(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    @Override
    public double calcularGasto() {
        return getMonto() * 0.05;
    }
}
