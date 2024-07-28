package Model;

/**
 *
 * @author MRodzDirect 😉 <Organico>
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.File;

public class DataBaseManager {

    public static void createTables() {
        String createContribuyentesTable = "CREATE TABLE IF NOT EXISTS Contribuyentes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT, "
                + "sueldosMensuales TEXT, "
                + "direccion TEXT, "
                + "cedula TEXT, "
                + "reporte TEXT, "
                + "mensaje TEXT"
                + ")";

        createFacturasTable();

        try (Connection connection = ConexionADataBase.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(createContribuyentesTable);
            //statement.executeUpdate(createFacturasTable);
        } catch (SQLException e) {
            System.err.println("Error creating database tables: " + e.getMessage());
        }
    }

    public static void createFacturasTable() {
        String createFacturasTable = "CREATE TABLE IF NOT EXISTS Facturas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo TEXT, "
                + "monto REAL, "
                + "contribuyente_id INTEGER, "
                + "FOREIGN KEY (contribuyente_id) REFERENCES Contribuyentes(id)"
                + ")";

        try (Connection connection = ConexionADataBase.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(createFacturasTable);
        } catch (SQLException e) {
            System.err.println("Error creating Facturas table: " + e.getMessage());
        }
    }

    public static boolean databaseExists() {
        String databaseFilePath = "DB/Contribuyentes.db"; // Ruta de la base de datos
        File databaseFile = new File(databaseFilePath);
        return databaseFile.exists();
    }

    public static boolean tableExists(String tableName) {
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name=?";
        try (Connection connection = ConexionADataBase.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if table exists: " + e.getMessage());
        }
        return false;
    }

}
