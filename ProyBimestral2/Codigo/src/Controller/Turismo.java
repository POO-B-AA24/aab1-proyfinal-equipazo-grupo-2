package Controller;
public class Turismo extends Factura {

    public Turismo(double monto) {
        super(monto);
    }
    public double calcularGasto() {
        return getMonto() * 0.05;
    }
}
