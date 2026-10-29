package sorpresa.cliente.modelo;

/** Datos de un comprador: su nombre y las entradas que lleva compradas. */
public class Comprador {
    private final String nombre;
    private int entradasCompradas = 0;

    public Comprador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEntradasCompradas() {
        return entradasCompradas;
    }

    public void anadirEntradas(int cantidad) {
        entradasCompradas += cantidad;
    }
}
