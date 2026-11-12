package model;

/**
 * Aldeano con una sola profesion: Cazador.
 */
public class AldeanoCazador extends Aldeano implements Cazador {

    private int capturaAnimales;

    public AldeanoCazador(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.capturaAnimales = 0;
    }

    @Override
    public void cazar(int cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad de animales debe ser al menos 1.");
        }
        this.capturaAnimales += cantidad;
    }

    @Override
    public int getCapturaAnimales() {
        return capturaAnimales;
    }

    @Override
    public String getResumen() {
        return toString() + "\n  Caza: " + describirCaza();
    }
}

