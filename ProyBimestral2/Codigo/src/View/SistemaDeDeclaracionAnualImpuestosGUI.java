package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import Controller.Contribuyente;
import Controller.OperacionesContribuyente;

public class SistemaDeDeclaracionAnualImpuestosGUI extends JFrame {

    private JButton generateContribuyentesButton, showContribuyentesButton, searchContribuyenteButton;
    private JTextField searchContribuyenteField;
    private JTable contribuyenteTable;
    private DefaultTableModel contribuyenteTableModel;

    public SistemaDeDeclaracionAnualImpuestosGUI() {
        setTitle("Sistema de Declaración Anual de Impuestos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the GUI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());

        generateContribuyentesButton = new JButton("Generar Contribuyentes");
        showContribuyentesButton = new JButton("Mostrar Contribuyentes");
        searchContribuyenteButton = new JButton("Buscar Contribuyente");
        searchContribuyenteField = new JTextField(20);
        contribuyenteTableModel = new DefaultTableModel(new Object[]{"id", "nombre", "sueldosMensuales", "direccion", "cedula", "reporte", "mensaje"}, 0);
        contribuyenteTable = new JTable(contribuyenteTableModel);

        // Add the components to the panels
        buttonPanel.add(generateContribuyentesButton);
        buttonPanel.add(showContribuyentesButton);
        searchPanel.add(new JLabel("Buscar por Cedula:"));
        searchPanel.add(searchContribuyenteField);
        searchPanel.add(searchContribuyenteButton);

        tablePanel.add(new JScrollPane(contribuyenteTable), BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Add event listeners
        generateContribuyentesButton.addActionListener(e -> generateContribuyentes());
        showContribuyentesButton.addActionListener(e -> showContribuyentes());
        searchContribuyenteButton.addActionListener(e -> searchContribuyente());
    }

    private void generateContribuyentes() {
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
    }

    private void showContribuyentes() {
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
    }

    private void searchContribuyente() {
        String searchCedula = JOptionPane.showInputDialog(this, "Ingrese la cedula del contribuyente:");
        Contribuyente contribuyente = OperacionesContribuyente.buscarContribuyentePorCedula(searchCedula);
        if (contribuyente != null) {
            JOptionPane.showMessageDialog(this, "Reporte de " + contribuyente.getName() + " (Cedula: " + contribuyente.getCedula() + "):\n" + contribuyente.getReporte());
            try {
                String fileName = contribuyente.getName() + "_" + contribuyente.getId() + "_reporte.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(contribuyente.getReporte());
                writer.close();
                JOptionPane.showMessageDialog(this, "El reporte se ha guardado en el archivo " + fileName);
                JOptionPane.showMessageDialog(this, "Mensaje de " + contribuyente.getName() + " (Cedula: " + contribuyente.getCedula() + "):\n" + contribuyente.getMensaje());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el reporte: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún contribuyente con la cedula " + searchCedula);
        }
    }

}
