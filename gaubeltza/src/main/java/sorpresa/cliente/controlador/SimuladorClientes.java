package sorpresa.cliente.controlador;

import sorpresa.cliente.modelo.Comprador;
import sorpresa.cliente.vista.VistaCliente;

/** Lanza varios clientes a la vez, cada uno en su propio hilo. */
public class SimuladorClientes {
    private static final int NUM_CLIENTES = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 5050;

    public static void main(String[] args) throws InterruptedException {
        VistaCliente vista = new VistaCliente();
        Thread[] hilos = new Thread[NUM_CLIENTES];
        for (int i = 0; i < NUM_CLIENTES; i++) {
            Comprador comprador = new Comprador("Comprador-" + (i + 1));
            hilos[i] = new Thread(new ClienteTaquilla(comprador, vista, HOST, PUERTO));
            hilos[i].start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }
        vista.mostrarFin();
    }
}
