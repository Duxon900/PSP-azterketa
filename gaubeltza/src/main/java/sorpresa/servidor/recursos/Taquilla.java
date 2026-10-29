package sorpresa.servidor.recursos;

/** Recurso compartido por todos los hilos del servidor. */
public class Taquilla {
    private int entradasDisponibles;
    private int ventasTotales = 0;

    public Taquilla(int entradasIniciales) {
        this.entradasDisponibles = entradasIniciales;
    }
    //TODO: getEntradasDisponibles() y getVentasTotales()
    //TODO: boolean comprar(int cantidad)
    
}