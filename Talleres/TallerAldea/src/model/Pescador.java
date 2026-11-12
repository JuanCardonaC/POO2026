package model;

/**
 * Interfaz que define el comportamiento de un aldeano pescador.
 * Un aldeano que implementa esta interfaz puede pescar y acumular capturas de peces.
 */
public interface Pescador {

    /**
     * Realiza una jornada de pesca y acumula la cantidad de peces capturados.
     *
     * @param cantidad Cantidad de peces capturados en esta jornada.
     */
    void pescar(int cantidad);

    /**
     * @return Total de peces capturados acumulados.
     */
    int getCapturaPeces();

    /**
     * @return Descripcion de la actividad de pesca del aldeano.
     */
    default String describirPesca() {
        return "Ha capturado " + getCapturaPeces() + " peces en total.";
    }
}

