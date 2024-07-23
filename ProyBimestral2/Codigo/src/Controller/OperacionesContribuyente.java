package Controller;

import java.util.ArrayList;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperacionesContribuyente {

    public static Contribuyente crearYProcesarContribuyente(int contadorUsuarios) { // Método público
        ArrayList<Factura> facturas = GeneradorFacturas.generarFacturas();
        Contribuyente usuario = new Contribuyente(contadorUsuarios,
                GeneradorDatos.generarNombres(),
                GeneradorDatos.generarSueldosDeUnAnio(),
                GeneradorDatos.generarDireccion(),
                GeneradorDatos.generarCedulas()
        );
        // Guardar facturas del contribuyente
        OperacionesFactura.saveFacturas(facturas, usuario.getId());

        // archivos normales , no DB
//        String archivoFacturas = ManejoArchivos.guardarFacturasEnArchivo(facturas, contadorUsuarios, usuario);
//        usuario.setFacturas(ManejoArchivos.leerFacturasDeArchivo(archivoFacturas));

        // Leer facturas desde la db
        leerFacturasDesdeDB(usuario);
        usuario.setFacturas(usuario.getFacturas());
        
        // Proceso fundamentales
        procesarImpuestos(usuario);

        // archivos
//        ManejoArchivos.guardarContribuyente(usuario);
        // Guardar contribuyente a ala base de datos
        saveContribuyente(usuario);

        return usuario;
    }

    private static void procesarImpuestos(Contribuyente usuario) {
        Random r = new Random();
        if (r.nextBoolean()) {
            usuario.setDividend(GeneradorDatos.generarDividendos());
            usuario.setDividendTaxRate(GeneradorDatos.generarTasasImpositivasDividendos());
        }
        usuario.calcularImpuestos();
        usuario.generarReporteImpuestos();
    }

    public static void leerYMostrarContribuyentes(ArrayList<Contribuyente> usuarios) { // Método público
        for (Contribuyente usuario : usuarios) {
            Contribuyente cliente = ManejoArchivos.leerContribuyente(usuario.getName());
            System.out.println("Contribuyente con su reporte leído desde " + usuario.getName() + ".dat");
            System.out.println(cliente.toString());

        }
    }

    // Operaciones para consultar hacia la database:
    private static void saveContribuyente(Contribuyente contribuyente) {
        String sueldosMensualesString = convertArrayToString(contribuyente.getSueldosMensuales());
        String sql = "INSERT INTO Contribuyentes (nombre, sueldosMensuales, direccion, cedula, reporte) VALUES (?, ?, ?, ?, ?)";
        ConexionADataBase.executeUpdate(sql, contribuyente.getName(), sueldosMensualesString, contribuyente.getDireccion(), contribuyente.getCedula(), contribuyente.getReporte());
    }

    private static String convertArrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static void leerDesdeDBContribuyentes(ArrayList<Contribuyente> usuarios) {
        String sql = "SELECT c.id, c.nombre, c.sueldosMensuales, c.direccion, c.cedula, c.reporte, f.tipo, f.monto "
                + "FROM Contribuyentes c "
                + "LEFT JOIN Facturas f ON c.id = f.contribuyente_id";
        try (ResultSet resultSet = ConexionADataBase.executeQuery(sql)) {
            while (resultSet.next()) {
                int contribuyenteId = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String sueldosMensualesString = resultSet.getString("sueldosMensuales");
                double[] sueldosMensuales = convertStringToArray(sueldosMensualesString);
                String direccion = resultSet.getString("direccion");
                String cedula = resultSet.getString("cedula");
                String tipoFactura = resultSet.getString("tipo");
                double monto = resultSet.getDouble("monto");
                String reporte = resultSet.getString("reporte");

                Contribuyente contribuyente = verificarContribuyente(usuarios, contribuyenteId, nombre); // evitar que el mismo cliente no este duplicado. Esto es un double-check
                contribuyente.setSueldosMensuales(sueldosMensuales);
                contribuyente.setDireccion(direccion);
                contribuyente.setCedula(cedula);
                contribuyente.setReporte(reporte);

                leerFacturasDesdeDB(contribuyente);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data from the database: " + e.getMessage());
        }

        for (Contribuyente contribuyente : usuarios) {
            System.out.println(contribuyente);
        }
    }

    private static double[] convertStringToArray(String sueldosMensualesString) {
        String[] sueldosMensualesArray = sueldosMensualesString.split(",");
        double[] sueldosMensuales = new double[sueldosMensualesArray.length];
        for (int i = 0; i < sueldosMensualesArray.length; i++) {
            sueldosMensuales[i] = Double.parseDouble(sueldosMensualesArray[i]);
        }
        return sueldosMensuales;
    }

    private static void leerFacturasDesdeDB(Contribuyente contribuyente) {
        String sql = "SELECT f.tipo, f.monto FROM Facturas f WHERE f.contribuyente_id = ?";
        try (Connection connection = ConexionADataBase.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contribuyente.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tipoFactura = resultSet.getString("tipo");
                    double monto = resultSet.getDouble("monto");
                    Factura factura = OperacionesFactura.crearFactura(tipoFactura, monto);
                    contribuyente.addFactura(factura);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data from the database: " + e.getMessage());
        }
    }

    private static Contribuyente verificarContribuyente(ArrayList<Contribuyente> usuarios, int contribuyenteId, String nombre) {
        for (Contribuyente contribuyente : usuarios) {
            if (contribuyente.getId() == contribuyenteId) {
                return contribuyente;
            }
        }
        Contribuyente newContribuyente = usuarios.get(contribuyenteId);
        newContribuyente.setId(contribuyenteId);
        usuarios.add(newContribuyente);
        return newContribuyente;
    }

    public static void deleteContribuyente(Contribuyente contribuyente) {
        String sql = "DELETE FROM Facturas WHERE contribuyente_id = ?";
        ConexionADataBase.executeUpdate(sql, contribuyente.getId());

        sql = "DELETE FROM Contribuyentes WHERE id = ?";
        ConexionADataBase.executeUpdate(sql, contribuyente.getId());
    }

}
