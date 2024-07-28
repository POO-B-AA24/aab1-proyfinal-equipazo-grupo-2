package Controller;

import java.io.Serializable;

public abstract class Factura implements GastoCalculable, Serializable {

    double monto;
    int id;
    int contribuyenteId;
    double discount;

    public Factura(double monto) {
        this.monto = monto;
    }

    public Factura() {
    }

    public Factura(double monto, int contribuyenteId) {
        this.monto = monto;
        this.contribuyenteId = contribuyenteId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContribuyenteId() {
        return contribuyenteId;
    }

    public void setContribuyenteId(int contribuyenteId) {
        this.contribuyenteId = contribuyenteId;
    }

    @Override
    public abstract double calcularGasto();

    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura #" + i);
        sb.append("\n monto=").append(monto);
        sb.append("Descuento= ").append(discount);
        sb.append("\n contribuyenteId=").append(contribuyenteId); //once tests have finished. Exclude this from toString()
        sb.append('}');
        return sb.toString();
    }
}
