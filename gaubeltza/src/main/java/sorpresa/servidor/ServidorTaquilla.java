package sorpresa.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sorpresa.servidor.hilos.GestorCliente;
import sorpresa.servidor.recursos.Taquilla;

public class ServidorTaquilla {
    private static final int PUERTO = 5050;
    private static final int ENTRADAS_INICIALES = 20;

    public static void main(String[] args) {
        Taquilla taquilla = new Taquilla(ENTRADAS_INICIALES);
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Taquilla abierta con " + ENTRADAS_INICIALES + " entradas en el puerto " + PUERTO + ".");
            int contador = 0;
            while (true) {
                Socket cliente = servidor.accept();
                contador++;
                System.out.println("Cliente " + contador + " conectado.");
                new Thread(new GestorCliente(cliente, taquilla), "Cliente-" + contador).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
