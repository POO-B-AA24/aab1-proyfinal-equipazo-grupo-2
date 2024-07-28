package Controller;

public class FacturaVivienda extends Factura {

    public FacturaVivienda(double monto) {
        super(monto);
        this.discount = 0.04;
    }

    public FacturaVivienda() {
        this.discount = 0.04;
    }

    public FacturaVivienda(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
        this.discount = 0.04;
    }

    @Override
    public double calcularGasto() {
        return getMonto() * this.discount;
    }
}
