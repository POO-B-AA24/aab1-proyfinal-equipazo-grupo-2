package Controller;

import java.util.ArrayList;

/**
 *
 * @author MRodzDirect 😉 <Organico>
 */
public class OperacionesFactura {

    public static Factura crearFactura(String tipoFactura, double monto) {
        switch (tipoFactura) {
            case "Alimentacion":
                return new Alimentacion(monto);
            case "Vivienda":
                return new Vivienda(monto);
            case "Turismo":
                return new Turismo(monto);
            case "Vestimenta":
                return new Vestimenta(monto);
            case "Educacion":
                return new Educacion(monto);
            case "Salud":
                return new Salud(monto);
            default:
                throw new IllegalArgumentException("Tipo de factura inválido: " + tipoFactura);
        }
    }

    public static void saveFacturas(ArrayList<Factura> facturas, int contribuyenteId) {
        String sql = "INSERT INTO Facturas (tipo, monto, contribuyente_id) VALUES (?, ?, ?)";
        for (Factura factura : facturas) {
            ConexionADataBase.executeUpdate(sql, factura.getClass().getSimpleName(), factura.getMonto(), contribuyenteId);
        }
    }

    public static void updateFactura(Factura factura, int contribuyenteId) {
        String sql = "UPDATE Facturas SET tipo = ?, monto = ? WHERE contribuyente_id = ? AND tipo = ?";
        ConexionADataBase.executeUpdate(sql, factura.getClass().getSimpleName(), factura.getMonto(), contribuyenteId, factura.getClass().getSimpleName());
    }

    public static void deleteFactura(Factura factura, int contribuyenteId) {
        String sql = "DELETE FROM Facturas WHERE contribuyente_id = ? AND tipo = ?";
        ConexionADataBase.executeUpdate(sql, contribuyenteId, factura.getClass().getSimpleName());
    }

}
