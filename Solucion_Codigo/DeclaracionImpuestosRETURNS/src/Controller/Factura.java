package Controller;


import java.io.Serializable;


public class Factura implements Serializable {

    private String categoria;
    private double monto;

    public Factura(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Factura #"+i);
        sb.append("\ncategoria=").append(categoria);
        sb.append("\n monto=").append(monto);
        sb.append('}');
        return sb.toString();
    }
    
    

   

}
