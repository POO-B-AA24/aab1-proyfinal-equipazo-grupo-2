package Controller;
public class Vestimenta extends Factura {

    public Vestimenta(double monto) {
        super(monto);
    }
    public double calcularGasto() {
        return getMonto();
    }
}
