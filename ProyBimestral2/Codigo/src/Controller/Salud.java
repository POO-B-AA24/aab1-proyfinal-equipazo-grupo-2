package Controller;

public class Salud extends Factura{
    public Salud(double monto) {
        super(monto);
    }

    public double calcularGasto() {
        return getMonto() * 0.1;
    }
}