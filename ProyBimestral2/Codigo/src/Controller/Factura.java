package Controller;

import java.io.Serializable;

public abstract class Factura implements GastoCalculable , Serializable{
    double monto;

    public Factura(double monto) {
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public abstract double calcularGasto();

    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura #" + i);
        sb.append("\n monto=").append(monto);
        sb.append('}');
        return sb.toString();
    }
}