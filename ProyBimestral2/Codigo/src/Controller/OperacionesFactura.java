package Controller;

import Model.ConexionADataBase;
import java.util.ArrayList;
import Model.DataBaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author MRodzDirect 😉 <Organico>
 */
public class OperacionesFactura {

    public static Factura crearFactura(String tipoFactura, double monto, int contribuyenteId) {
        switch (tipoFactura) {
            case "Alimentacion":
                return new FacturaAlimentacion(monto, contribuyenteId);
            case "Vivienda":
                return new FacturaVivienda(monto, contribuyenteId);
            case "Turismo":
                return new FacturaTurismo(monto, contribuyenteId);
            case "Educacion":
                return new FacturaEducacion(monto, contribuyenteId);
            case "Salud":
                return new FacturaSalud(monto, contribuyenteId);
            default:
                throw new IllegalArgumentException("Tipo de factura inválido: " + tipoFactura);
        }
    }

public static void saveFacturas(ArrayList<Factura> facturas, int contribuyenteId) {
    if (DataBaseManager.tableExists("Facturas")) {
        String sql = "INSERT INTO Facturas (tipo, monto, contribuyente_id) VALUES (?, ?, ?)";
        for (Factura factura : facturas) {
            ConexionADataBase.executeUpdate(sql, factura.getClass().getSimpleName(), factura.getMonto(), contribuyenteId);
            int facturaId = getLastFacturaId() + 1;
            factura.setId(facturaId);
        }
    } else {
        System.out.println("Facturas table does not exist.");
    }
}




private static int getLastFacturaId() {
    String sql = "SELECT MAX(id) AS last_id FROM Facturas";
    try (ResultSet resultSet = ConexionADataBase.executeQuery(sql)) {
        if (resultSet.next()) {
            return resultSet.getInt("last_id");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving data from the database: " + e.getMessage());
    }
    return 0; // Return 0 if the table is empty
}



public static void leerFacturasDesdeDB(Contribuyente contribuyente) {
    if (DataBaseManager.tableExists("Facturas")) {
        String sql = "SELECT f.id, f.tipo, f.monto, f.contribuyente_id FROM Facturas f WHERE f.contribuyente_id = ?";
        try (Connection connection = ConexionADataBase.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contribuyente.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int facturaId = resultSet.getInt("id");
                    String tipoFactura = resultSet.getString("tipo");
                    double monto = resultSet.getDouble("monto");
                    int contribuyenteId = resultSet.getInt("contribuyente_id");
                    Factura factura = OperacionesFactura.crearFactura(tipoFactura, monto,contribuyenteId);
                    factura.setId(facturaId);
                    contribuyente.addFactura(factura);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data from the database: " + e.getMessage());
        }
    } else {
        System.out.println("Facturas table does not exist.");
    }
}



    /*public static int getNextFacturaId() {
        String sql = "SELECT MAX(id) AS last_id FROM Facturas";
        try (ResultSet resultSet = ConexionADataBase.executeQuery(sql)) {
            if (resultSet.next()) {
                int lastId = resultSet.getInt("last_id");
                return lastId + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data from the database: " + e.getMessage());
        }
        return 1; // Return 1 if the table is empty
    }*/

    public static void updateFactura(Factura factura, int contribuyenteId) {
        String sql = "UPDATE Facturas SET tipo = ?, monto = ? WHERE contribuyente_id = ? AND tipo = ?";
        ConexionADataBase.executeUpdate(sql, factura.getClass().getSimpleName(), factura.getMonto(), contribuyenteId, factura.getClass().getSimpleName());
    }

    public static void deleteFactura(Factura factura, int contribuyenteId) {
        String sql = "DELETE FROM Facturas WHERE contribuyente_id = ? AND tipo = ?";
        ConexionADataBase.executeUpdate(sql, contribuyenteId, factura.getClass().getSimpleName());
    }

}
