package Controller;

public class FacturaEducacion extends Factura {

    public FacturaEducacion(double monto) {
        super(monto);
        this.discount = 0.03;

    }

    public FacturaEducacion() {
        this.discount = 0.03;

    }

    public FacturaEducacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
        this.discount = 0.03;
    }

    @Override
    public double calcularGasto() {
        return getMonto() * this.discount ;
    }
}
