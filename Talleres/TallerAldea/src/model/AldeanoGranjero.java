package model;

/**
 * Aldeano con una sola profesion: Granjero.
 */
public class AldeanoGranjero extends Aldeano implements Granjero {

    private int cosecha;

    public AldeanoGranjero(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.cosecha = 0;
    }

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

    @Override
    public String getResumen() {
        return toString() + "\n  Cultivo: " + describirCultivo();
    }
}

