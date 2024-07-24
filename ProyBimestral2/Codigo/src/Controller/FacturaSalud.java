package Controller;

public class FacturaSalud extends Factura{
    public FacturaSalud(double monto) {
        super(monto);
    }
    public FacturaSalud(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }

    public double calcularGasto() {
        return getMonto() * 0.1;
    }
}