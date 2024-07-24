package Controller;

public class Salud extends Factura{
    public Salud(double monto) {
        super(monto);
    }
    public Salud(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }

    public double calcularGasto() {
        return getMonto() * 0.1;
    }
}