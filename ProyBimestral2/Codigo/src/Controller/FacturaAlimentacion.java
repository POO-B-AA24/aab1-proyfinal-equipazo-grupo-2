package Controller;

public class FacturaAlimentacion extends Factura {

    public FacturaAlimentacion(double monto) {
        super(monto);
        this.discount = 0.005;
    }

    public FacturaAlimentacion() {
        this.discount = 0.005;
    }

    public FacturaAlimentacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
        this.discount = 0.005;

    }

    @Override
    public double calcularGasto() {
        return getMonto() * this.discount;
    }
}
