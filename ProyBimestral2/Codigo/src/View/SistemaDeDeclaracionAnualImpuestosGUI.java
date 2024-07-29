package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import Controller.Contribuyente;
import Controller.Factura;
import Controller.OperacionesFactura;
import Controller.OperacionesContribuyente;

public class SistemaDeDeclaracionAnualImpuestosGUI extends JFrame {

    private JButton generateContribuyentesButton, showContribuyentesButton, searchContribuyenteButton, deleteContribuyenteButton, showReportButton, showMessageButton, showInvoicesButton, showSalariesButton, goBackButton;
    private JTextField searchContribuyenteField;
    private JTable contribuyenteTable, facturaTable;
    private DefaultTableModel contribuyenteTableModel, facturaTableModel;
    private String accountantUsername = "accountant";
    private String accountantPassword = "password123";
    private boolean accountantAuthenticated = false;
    private Contribuyente currentContribuyente;
    
    private JPanel additionalButtonPanel;
    private JPanel contribuyentePanel;
    


    public SistemaDeDeclaracionAnualImpuestosGUI() {
        setTitle("Sistema de Declaración Anual de Impuestos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Create the GUI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());

        generateContribuyentesButton = new JButton("Generar Contribuyentes");
        generateContribuyentesButton.setBackground(new Color(0, 128, 0)); // Green
        generateContribuyentesButton.setForeground(Color.WHITE);
        showContribuyentesButton = new JButton("Mostrar Contribuyentes");
        showContribuyentesButton.setBackground(new Color(0, 128, 0)); // Green
        showContribuyentesButton.setForeground(Color.WHITE);
        searchContribuyenteButton = new JButton("Buscar Contribuyente");
        searchContribuyenteButton.setBackground(new Color(0, 128, 0)); // Green
        searchContribuyenteButton.setForeground(Color.WHITE);
        deleteContribuyenteButton = new JButton("Eliminar Contribuyente");
        deleteContribuyenteButton.setBackground(new Color(255, 0, 0)); // Red
        deleteContribuyenteButton.setForeground(Color.WHITE);
        showReportButton = new JButton("Mostrar Reporte");
        showReportButton.setBackground(new Color(0, 128, 0)); // Green
        showReportButton.setForeground(Color.WHITE);
        showMessageButton = new JButton("¿Por qué pagaré esa cantidad?");
        showMessageButton.setBackground(new Color(0, 128, 0)); // Green
        showMessageButton.setForeground(Color.WHITE);
        showInvoicesButton = new JButton("Mostrar Facturas");
        showInvoicesButton.setBackground(new Color(0, 128, 0)); // Green
        showInvoicesButton.setForeground(Color.WHITE);
        showSalariesButton = new JButton("Mostrar Sueldos");
        showSalariesButton.setBackground(new Color(0, 128, 0)); // Green
        showSalariesButton.setForeground(Color.WHITE);
        searchContribuyenteField = new JTextField(20);
        searchContribuyenteField.setBackground(new Color(255, 255, 255)); // White
        searchContribuyenteField.setForeground(new Color(0, 0, 0)); // Black
        contribuyenteTableModel = new DefaultTableModel(new Object[]{"id", "nombre", "sueldosMensuales", "direccion", "cedula", "reporte", "mensaje"}, 0);
        contribuyenteTable = new JTable(contribuyenteTableModel);
        contribuyenteTable.setBackground(new Color(255, 255, 255)); // White
        contribuyenteTable.setForeground(new Color(0, 0, 0)); // Black

        goBackButton = new JButton("Ir al Inicio");
        goBackButton.setBackground(new Color(0, 128, 0)); // Green
        goBackButton.setForeground(Color.WHITE);

        // Add the components to the panels
        buttonPanel.add(generateContribuyentesButton);
        buttonPanel.add(showContribuyentesButton);
        buttonPanel.add(deleteContribuyenteButton);
        searchPanel.add(new JLabel("Buscar por Cedula:"));
        searchPanel.add(searchContribuyenteField);
        searchPanel.add(searchContribuyenteButton);
        buttonPanel.add(goBackButton);

        tablePanel.add(new JScrollPane(contribuyenteTable), BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Add event listeners
        generateContribuyentesButton.addActionListener(e -> generateContribuyentes());
        showContribuyentesButton.addActionListener(e -> showContribuyentes());
        searchContribuyenteButton.addActionListener(e -> searchContribuyente());
        deleteContribuyenteButton.addActionListener(e -> deleteContribuyente());
        searchContribuyenteField.addActionListener(e -> searchContribuyente());
        showReportButton.addActionListener(e -> showReport());
        showMessageButton.addActionListener(e -> showMessage());
        showInvoicesButton.addActionListener(e -> showInvoices());
        showSalariesButton.addActionListener(e -> showSalaries());
        goBackButton.addActionListener(e -> restoreButtons());

        // Hide the additional buttons initially
        showReportButton.setVisible(false);
        showMessageButton.setVisible(false);
        showInvoicesButton.setVisible(false);
        showSalariesButton.setVisible(false);

        // test
        buttonPanel = new JPanel(new FlowLayout());
        // Add the buttons to the buttonPanel
        buttonPanel.add(generateContribuyentesButton);
        buttonPanel.add(showContribuyentesButton);
        buttonPanel.add(deleteContribuyenteButton);
        buttonPanel.add(goBackButton);

        contribuyentePanel = new JPanel(new BorderLayout());
        contribuyentePanel.add(new JScrollPane(contribuyenteTable), BorderLayout.CENTER);

        // Create the additional button panel
        additionalButtonPanel = new JPanel(new FlowLayout());
        additionalButtonPanel.add(showReportButton);
        additionalButtonPanel.add(showMessageButton);
        additionalButtonPanel.add(showInvoicesButton);
        additionalButtonPanel.add(showSalariesButton);
        additionalButtonPanel.setVisible(false);

        // Add the panels to the main panel
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(contribuyentePanel, BorderLayout.CENTER);
        mainPanel.add(additionalButtonPanel, BorderLayout.EAST);

    }

  

    private void generateContribuyentes() {
        if (validateAccountant()) {
            contribuyenteTableModel.setRowCount(0);
            boolean continuar = true;
            int contadorUsuarios = OperacionesContribuyente.getLastContribuyenteId() + 1;

            while (continuar) {
                Contribuyente contribuyente = OperacionesContribuyente.crearYProcesarContribuyente(contadorUsuarios);
                contribuyenteTableModel.addRow(new Object[]{
                    contribuyente.getId(),
                    contribuyente.getName(),
                    OperacionesContribuyente.convertArrayToString(contribuyente.getSueldosMensuales()),
                    contribuyente.getDireccion(),
                    contribuyente.getCedula(),
                    contribuyente.getReporte(),
                    contribuyente.getMensaje()
                });
                contadorUsuarios++;

                int result = JOptionPane.showConfirmDialog(this, "¿Desea ingresar otro Contribuyente?", "Generar Contribuyente", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    continuar = false;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. Solo el usuario 'accountant' con contraseña 'password123' puede generar y mostrar contribuyentes.");
        }
    }

    private void showContribuyentes() {
        if (validateAccountant()) {
            contribuyenteTableModel.setRowCount(0);
            ArrayList<Contribuyente> contribuyentes = OperacionesContribuyente.leerTodosLosContribuyentesDesdeDB();
            for (Contribuyente contribuyente : contribuyentes) {
                contribuyenteTableModel.addRow(new Object[]{
                    contribuyente.getId(),
                    contribuyente.getName(),
                    OperacionesContribuyente.convertArrayToString(contribuyente.getSueldosMensuales()),
                    contribuyente.getDireccion(),
                    contribuyente.getCedula(),
                    contribuyente.getReporte(),
                    contribuyente.getMensaje()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. Solo el usuario 'accountant' con contraseña 'password123' puede generar y mostrar contribuyentes.");
        }
    }

    private void searchContribuyente() {
        String searchCedula = searchContribuyenteField.getText();
        currentContribuyente = OperacionesContribuyente.buscarContribuyentePorCedula(searchCedula);
        if (currentContribuyente != null) {
            contribuyenteTableModel.setRowCount(0);
            contribuyenteTableModel.addRow(new Object[]{
                currentContribuyente.getId(),
                currentContribuyente.getName(),
                OperacionesContribuyente.convertArrayToString(currentContribuyente.getSueldosMensuales()),
                currentContribuyente.getDireccion(),
                currentContribuyente.getCedula(),
                currentContribuyente.getReporte(),
                currentContribuyente.getMensaje()
            });

            additionalButtonPanel.setVisible(true); // controlaso


            additionalButtonPanel.add(showReportButton);
            additionalButtonPanel.add(showMessageButton);
            additionalButtonPanel.add(showInvoicesButton);
            additionalButtonPanel.add(showSalariesButton);
            additionalButtonPanel.add(goBackButton);

//             Add the additional buttons to the panel
            showReportButton.setVisible(true);
            showMessageButton.setVisible(true);
            showInvoicesButton.setVisible(true);
            showSalariesButton.setVisible(true);

            generateContribuyentesButton.setVisible(false);
            showContribuyentesButton.setVisible(false);
            deleteContribuyenteButton.setVisible(false);
            goBackButton.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún contribuyente con la cedula " + searchCedula);
        }
    }

    private void deleteContribuyente() {
        if (validateAccountant()) {
            String searchCedula = JOptionPane.showInputDialog(this, "Ingrese la cedula del contribuyente a eliminar:");
            Contribuyente contribuyente = OperacionesContribuyente.buscarContribuyentePorCedula(searchCedula);
            if (contribuyente != null) {
                OperacionesContribuyente.deleteContribuyentePorCedula(searchCedula);
                contribuyenteTableModel.setRowCount(0);
                showContribuyentes();
                JOptionPane.showMessageDialog(this, "El contribuyente con cedula " + searchCedula + " ha sido eliminado.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún contribuyente con la cedula " + searchCedula);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos. Solo el usuario 'accountant' con contraseña 'password123' puede eliminar contribuyentes.");
        }
    }

    private void showReport() {
        String reporte = currentContribuyente.getReporte();
        JOptionPane.showMessageDialog(this, reporte);

        // Write the report to a PDF file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_" + currentContribuyente.getCedula() + ".pdf"));
            writer.write(reporte);
            writer.close();
            JOptionPane.showMessageDialog(this, "El reporte se ha guardado en el archivo 'reporte_" + currentContribuyente.getCedula() + ".pdf'.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el reporte: " + e.getMessage());
        }
    }

    private void showMessage() {
        String mensaje = currentContribuyente.getMensaje();
        int result = JOptionPane.showConfirmDialog(this, "¿Desea saber por qué tiene que pagar " + mensaje + "?", "Mensaje del Contribuyente", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, mensaje);
        }
    }

    private void showInvoices() {
        // Create a new panel for the invoices table
        JPanel invoicesPanel = new JPanel(new BorderLayout());

        // Create the invoices table
        facturaTableModel = new DefaultTableModel(new Object[]{"id", "tipo", "monto"}, 0);
        facturaTable = new JTable(facturaTableModel);
        facturaTable.setBackground(new Color(255, 255, 255)); // White
        facturaTable.setForeground(new Color(0, 0, 0)); // Black

        // Populate the invoices table
        OperacionesFactura.leerFacturasDesdeDB(currentContribuyente);
        for (Factura factura : currentContribuyente.getFacturas()) {
            facturaTableModel.addRow(new Object[]{
                factura.getId(),
                factura.getClass().getSimpleName(),
                factura.getMonto()
            });
        }

        // Add the invoices table to the panel
        invoicesPanel.add(new JScrollPane(facturaTable), BorderLayout.CENTER);

        // Show the invoices panel
        JOptionPane.showMessageDialog(this, invoicesPanel, "Facturas del Contribuyente", JOptionPane.PLAIN_MESSAGE);
    }

    private void showSalaries() {
        String sueldosMensualesString = OperacionesContribuyente.convertArrayToString(currentContribuyente.getSueldosMensuales());
        JOptionPane.showMessageDialog(this, "Sueldos mensuales del contribuyente:\n" + sueldosMensualesString, "Sueldos del Contribuyente", JOptionPane.PLAIN_MESSAGE);
    }

    private boolean validateAccountant() {
        if (!accountantAuthenticated) {
            String username = JOptionPane.showInputDialog(this, "Ingrese el nombre de usuario:");
            String password = JOptionPane.showInputDialog(this, "Ingrese la contraseña:");
            if (username.equals(accountantUsername) && password.equals(accountantPassword)) {
                accountantAuthenticated = true;
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                return false;
            }
        } else {
            return true;
        }
    }

    private void restoreButtons() {
        generateContribuyentesButton.setVisible(true);
        showContribuyentesButton.setVisible(true);
        deleteContribuyenteButton.setVisible(true);
        showReportButton.setVisible(false);
        showMessageButton.setVisible(false);
        showInvoicesButton.setVisible(false);
        showSalariesButton.setVisible(false);
        goBackButton.setVisible(false);
        searchContribuyenteField.setText("");
        contribuyenteTableModel.setRowCount(0);
        currentContribuyente = null;

        additionalButtonPanel.setVisible(false);

    }
}
