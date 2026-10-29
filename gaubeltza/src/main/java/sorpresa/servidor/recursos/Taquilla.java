package sorpresa.servidor.recursos;

/** Recurso compartido por todos los hilos del servidor. */
public class Taquilla {
    private int entradasDisponibles;
    private int ventasTotales = 0;

    public Taquilla(int entradasIniciales) {
        this.entradasDisponibles = entradasIniciales;
    }

    public synchronized int getEntradasDisponibles() {
        return entradasDisponibles;
    }

    public synchronized int getVentasTotales() {
        return ventasTotales;
    }

    
    public synchronized boolean comprar(int cantidad) {
        if (cantidad <= 0 || cantidad > entradasDisponibles) {
            return false;
        }
        entradasDisponibles -= cantidad;
        ventasTotales++;
        return true;
    }
}