package Controller;

public class FacturaTurismo extends Factura {

    public FacturaTurismo(double monto) {
        super(monto);
        this.discount = 0.05;

    }

    public FacturaTurismo() {
        this.discount = 0.05;

    }

    public FacturaTurismo(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
        this.discount = 0.05;
    }

    @Override
    public double calcularGasto() {
        return getMonto() * this.discount;
    }
}
