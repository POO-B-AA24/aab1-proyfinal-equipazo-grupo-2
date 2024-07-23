package Controller;
public class Alimentacion extends Factura {

    public Alimentacion(double monto) {
        super(monto);
    }
    public double calcularGasto() {
        return getMonto() * 0.005;
    }
}
