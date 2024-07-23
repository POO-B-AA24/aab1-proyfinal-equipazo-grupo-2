package Controller;
public class Vivienda extends Factura {

    public Vivienda(double monto) {
        super(monto);
    }
    public double calcularGasto() {
        return getMonto() * 0.04;
    }
}
