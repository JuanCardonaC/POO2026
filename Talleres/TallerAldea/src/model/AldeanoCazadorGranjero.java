package model;

/**
 * Aldeano con doble profesion: Cazador y Granjero.
 */
public class AldeanoCazadorGranjero extends Aldeano implements Cazador, Granjero {

    private int capturaAnimales;
    private int cosecha;

    public AldeanoCazadorGranjero(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.capturaAnimales = 0;
        this.cosecha = 0;
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

    // ── Granjero ─────────────────────────────────────────────────────────

    @Override
    public void cultivar(int kilos) {
        if (kilos < 1) {
            throw new IllegalArgumentException("Los kilogramos cosechados deben ser al menos 1.");
        }
        this.cosecha += kilos;
    }

    @Override
    public int getCosecha() {
        return cosecha;
    }

    // ── Resumen ──────────────────────────────────────────────────────────

    @Override
    public String getResumen() {
        return toString()
                + "\n  Caza:    " + describirCaza()
                + "\n  Cultivo: " + describirCultivo();
    }
}

