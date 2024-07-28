package Controller;

public class FacturaSalud extends Factura {

    public FacturaSalud(double monto) {
        super(monto);
        this.discount = 0.1;

    }

    public FacturaSalud() {
        this.discount = 0.1;

    }

    public FacturaSalud(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
        this.discount = 0.1;

    }

    @Override
    public double calcularGasto() {
        return getMonto() * this.discount;
    }
}
