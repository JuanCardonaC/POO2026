package model;

/**
 * Aldeano con doble profesion: Pescador y Cazador.
 */
public class AldeanoPescadorCazador extends Aldeano implements Pescador, Cazador {

    private int capturaPeces;
    private int capturaAnimales;

    public AldeanoPescadorCazador(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.capturaPeces = 0;
        this.capturaAnimales = 0;
    }

    // ── Pescador ─────────────────────────────────────────────────────────

    @Override
    public void pescar(int cantidad) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad de peces debe ser al menos 1.");
        }
        this.capturaPeces += cantidad;
    }

    @Override
    public int getCapturaPeces() {
        return capturaPeces;
    }

    // ── Cazador ──────────────────────────────────────────────────────────

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

    // ── Resumen ──────────────────────────────────────────────────────────

    @Override
    public String getResumen() {
        return toString()
                + "\n  Pesca: " + describirPesca()
                + "\n  Caza:  " + describirCaza();
    }
}

