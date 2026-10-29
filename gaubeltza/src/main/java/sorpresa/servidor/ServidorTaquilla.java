package sorpresa.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sorpresa.servidor.hilos.GestorCliente;
import sorpresa.servidor.recursos.Taquilla;

public class ServidorTaquilla {
    private static final int PUERTO = 5050;
    //TODO: declarar ENTRADAS_INICIALES

    public static void main(String[] args) {
        //TODO: crear objeto taquilla
        //TODO: abrir hilo  de GestorCliente con el cliente conectado y la taquilla. Darle de nombre "Cliente-"+contador
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Taquilla abierta con " + ENTRADAS_INICIALES + " entradas en el puerto " + PUERTO + ".");
            int contador = 0;
            while (true) {
                Socket cliente = servidor.accept();
                contador++;
                System.out.println("Cliente " + contador + " conectado.");
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
