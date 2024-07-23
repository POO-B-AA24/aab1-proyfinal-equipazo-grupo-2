/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author MRodzDirect 😉 <Organico>
 */

import Controller.ConexionADataBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    public static void createTables() {
        String createContribuyentesTable = "CREATE TABLE IF NOT EXISTS Contribuyentes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "sueldosMensuales TEXT," +
                "direccion TEXT," +
                "cedula TEXT," +
                "reporte TEXT" +
                ")";

        String createFacturasTable = "CREATE TABLE IF NOT EXISTS Facturas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "monto REAL NOT NULL," +
                "contribuyente_id INTEGER NOT NULL," +
                "FOREIGN KEY (contribuyente_id) REFERENCES Contribuyentes(id)" +
                ")";

        try (Connection connection = ConexionADataBase.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createContribuyentesTable);
            statement.executeUpdate(createFacturasTable);
        } catch (SQLException e) {
            System.err.println("Error creating database tables: " + e.getMessage());
        }
    }
}

