package View;

import Controller.ArrancarSistema;
import javax.swing.SwingUtilities;
import View.SistemaDeDeclaracionAnualImpuestosGUI;

public class SistemaDeDeclaracionAnualImpuestosCLI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            SistemaDeDeclaracionAnualImpuestosGUI gui = new SistemaDeDeclaracionAnualImpuestosGUI();
            gui.setVisible(true);
        });

        ArrancarSistema.comienza();
    }
}
