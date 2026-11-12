package model;

/**
 * Interfaz que define el comportamiento de un aldeano granjero.
 * Un aldeano que implementa esta interfaz puede cultivar y acumular cosechas.
 */
public interface Granjero {

    /**
     * Realiza una jornada de cultivo y acumula la cantidad cosechada (en kg).
     *
     * @param kilos Kilogramos cosechados en esta jornada.
     */
    void cultivar(int kilos);

    /**
     * @return Total de kilogramos cosechados acumulados.
     */
    int getCosecha();

    /**
     * @return Descripcion de la actividad de cultivo del aldeano.
     */
    default String describirCultivo() {
        return "Ha cosechado " + getCosecha() + " kg en total.";
    }
}

