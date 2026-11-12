package model;

/**
 * Aldeano con una sola profesion: Pescador.
 */
public class AldeanoPescador extends Aldeano implements Pescador {

    private int capturaPeces;

    public AldeanoPescador(String id, String nombre, int edad) {
        super(id, nombre, edad);
        this.capturaPeces = 0;
    }

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

    @Override
    public String getResumen() {
        return toString() + "\n  Pesca: " + describirPesca();
    }
}

