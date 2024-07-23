package Controller;
public class Educacion extends Factura {

    public Educacion(double monto) {
        super(monto);
    }
    public double calcularGasto() {
        return getMonto() * 0.03;
    }
}
