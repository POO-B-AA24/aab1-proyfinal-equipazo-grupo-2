package Controller;
public class Educacion extends Factura {

    public Educacion(double monto) {
        super(monto);
    }

    public Educacion(double monto, int contribuyenteId) {
        super(monto, contribuyenteId);
    }
    
    public double calcularGasto() {
        return getMonto() * 0.03;
    }
}
