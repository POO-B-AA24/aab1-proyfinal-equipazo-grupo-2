package View;
import Controller.Factura;
import Controller.Persona;
import java.util.ArrayList;
import java.util.Random;
public class Ejecutor {

    public static void main(String[] args) {
        ArrayList<Persona> personas = new ArrayList<>();

        for (Persona persona : personas) {
            persona.setFactura(new Factura("Pablo", 1050.0, "", "Quito"));
        }
    }

    public static String generarDireccion() {
        Random random = new Random();
        String[] direcciones = {"Loja", "El oro", "Quito", "Cuenca", "Guayaquil", "Zapotillo", "Ambato", "Manabí", "Esmeraldas", "Zamora"};
        return direcciones[random.nextInt(10)];
    }

    public static String generarCedulas() {
        Random random = new Random();
        String[] ced = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
        String cedula = ced[random.nextInt(24)];
        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            cedula += digito;
        }
        return cedula;
    }
}
