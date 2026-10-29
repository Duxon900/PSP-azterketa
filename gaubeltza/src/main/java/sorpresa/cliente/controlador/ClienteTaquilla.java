package sorpresa.cliente.controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import sorpresa.cliente.modelo.Comprador;
import sorpresa.cliente.vista.VistaCliente;

/** Cliente que consulta el stock y compra entradas hasta que se agotan. */
//TODO: runnable
public class ClienteTaquilla {
    private final Comprador comprador;
    private final VistaCliente vista;
    private final String host;
    private final int puerto;

    public ClienteTaquilla(Comprador comprador, VistaCliente vista, String host, int puerto) {
        this.comprador = comprador;
        this.vista = vista;
        this.host = host;
        this.puerto = puerto;
    }

    @Override
    public void run() {
        Random random = new Random();
        try (
            Socket socket = new Socket(host, puerto);
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
        ) {
            while (true) {
                String stock = enviar(salida, entrada, "STOCK");
                int disponibles = Integer.parseInt(stock.split(":")[1]);
                if (disponibles == 0) {
                    break;
                }
                int cantidad = random.nextInt(3) + 1;
                //TODO: respuesta enviar
                
                vista.mostrarRespuesta(comprador.getNombre(), respuesta);
                if (respuesta.startsWith("OK")) {
                    comprador.anadirEntradas(cantidad);
                }
                Thread.sleep(random.nextInt(100));
            }
            enviar(salida, entrada, "SALIR");
            vista.mostrarTotal(comprador.getNombre(), comprador.getEntradasCompradas());
        } catch (IOException | ClassNotFoundException e) {
            vista.mostrarError(comprador.getNombre(), e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String enviar(ObjectOutputStream salida, ObjectInputStream entrada, String mensaje)
            throws IOException, ClassNotFoundException {
        salida.writeObject(mensaje);
        salida.flush();
        return (String) entrada.readObject();
    }
}
