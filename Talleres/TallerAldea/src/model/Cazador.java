package model;

/**
 * Interfaz que define el comportamiento de un aldeano cazador.
 * Un aldeano que implementa esta interfaz puede cazar y acumular presas.
 */
public interface Cazador {

    /**
     * Realiza una jornada de caza y acumula la cantidad de animales cazados.
     *
     * @param cantidad Cantidad de animales cazados en esta jornada.
     */
    void cazar(int cantidad);

    /**
     * @return Total de animales cazados acumulados.
     */
    int getCapturaAnimales();

    /**
     * @return Descripcion de la actividad de caza del aldeano.
     */
    default String describirCaza() {
        return "Ha cazado " + getCapturaAnimales() + " animales en total.";
    }
}

