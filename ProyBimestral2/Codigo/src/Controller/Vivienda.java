package Controller;
public class Vivienda extends Factura {

    public Vivienda(double monto) {
        super(monto);
    }
    public Vivienda(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    public double calcularGasto() {
        return getMonto() * 0.04;
    }
}
