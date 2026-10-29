package sorpresa.cliente.vista;

/** Toda la salida por consola del cliente. */
public class VistaCliente {

    public void mostrarRespuesta(String nombre, String respuesta) {
        System.out.println(nombre + " -> " + respuesta);
    }

    public void mostrarTotal(String nombre, int entradas) {
        System.out.println(nombre + " ha comprado " + entradas + " entradas en total.");
    }

    public void mostrarError(String nombre, String mensaje) {
        System.err.println(nombre + " error: " + mensaje);
    }

    public void mostrarFin() {
        System.out.println("Todos los clientes han terminado.");
    }
}
