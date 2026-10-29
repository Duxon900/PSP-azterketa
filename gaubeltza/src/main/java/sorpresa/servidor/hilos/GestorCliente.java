package sorpresa.servidor.hilos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import sorpresa.servidor.recursos.Taquilla;

/** Atiende a un cliente en su propio hilo. */
public class GestorCliente implements Runnable {
    private final Socket socket;
    private final Taquilla taquilla;

    public GestorCliente(Socket socket, Taquilla taquilla) {
        this.socket = socket;
        this.taquilla = taquilla;
    }

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        try (
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            gestionarPeticion(entrada, salida, nombreHilo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[" + nombreHilo + "] Conexión terminada: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("No se pudo cerrar el socket: " + e.getMessage());
            }
        }
    }

    private void gestionarPeticion(ObjectInputStream entrada, ObjectOutputStream salida, String nombreHilo) throws ClassNotFoundException, IOException {
        String peticion;
        while ((peticion = (String) entrada.readObject()) != null) {
            System.out.println("[" + nombreHilo + "] Petición: " + peticion);
            String respuesta = procesar(peticion);
            salida.writeObject(respuesta);
            salida.flush();
            if (peticion.equals("SALIR")) {
                break;
            }
        }
    }

    private String procesar(String peticion) {
        switch (peticion) {
            case "STOCK":
                return "STOCK:" + taquilla.getEntradasDisponibles();
            case "SALIR":
                return "AGUR";
            default:
                return (peticion.startsWith("COMPRAR:")) ? comprobarCompra(peticion) : "ERROR:Comando desconocido";
        }
    }

    private String comprobarCompra(String peticion) {
        try {
            int cantidad = Integer.parseInt(peticion.substring("COMPRAR:".length()));
            if (cantidad <= 0) {
                return "ERROR:Cantidad no válida";
            }
            if (taquilla.comprar(cantidad)) {
                return "OK:" + cantidad;
            }
            return "ERROR:No hay suficientes entradas";
        } catch (NumberFormatException e) {
            return "ERROR:Cantidad no válida";
        }
    }
}
