package model;

/**
 * Aldeano con doble profesion: Pescador y Granjero.
 */
public class AldeanoPescadorGranjero extends Aldeano implements Pescador, Granjero {

    private int capturaPeces;
    private int cosecha;

    public AldeanoPescadorGranjero(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.capturaPeces = 0;
        this.cosecha = 0;
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
                + "\n  Pesca:   " + describirPesca()
                + "\n  Cultivo: " + describirCultivo();
    }
}

