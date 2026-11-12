package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona la coleccion de aldeanos de la aldea.
 * Actua como fabrica para registrar aldeanos y como repositorio
 * para buscarlos y filtrarlos por profesion.
 */
public class Aldea {

    private final String nombre;
    private final List<Aldeano> aldeanos;

    public Aldea(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la aldea no puede ser nulo o vacio.");
        }
        this.nombre = nombre;
        this.aldeanos = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public String getNombre() {
        return nombre;
    }

    public List<Aldeano> getAldeanos() {
        return Collections.unmodifiableList(aldeanos);
    }

    public int getCantidadAldeanos() {
        return aldeanos.size();
    }

    // ── Registro de aldeanos (fabrica) ───────────────────────────────────

    /**
     * Registra un aldeano que solo es Pescador.
     */
    public AldeanoPescador registrarPescador(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoPescador aldeano = new AldeanoPescador(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    /**
     * Registra un aldeano que solo es Cazador.
     */
    public AldeanoCazador registrarCazador(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoCazador aldeano = new AldeanoCazador(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    /**
     * Registra un aldeano que solo es Granjero.
     */
    public AldeanoGranjero registrarGranjero(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoGranjero aldeano = new AldeanoGranjero(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    /**
     * Registra un aldeano que es Pescador y Cazador.
     */
    public AldeanoPescadorCazador registrarPescadorCazador(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoPescadorCazador aldeano = new AldeanoPescadorCazador(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    /**
     * Registra un aldeano que es Pescador y Granjero.
     */
    public AldeanoPescadorGranjero registrarPescadorGranjero(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoPescadorGranjero aldeano = new AldeanoPescadorGranjero(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    /**
     * Registra un aldeano que es Cazador y Granjero.
     */
    public AldeanoCazadorGranjero registrarCazadorGranjero(String id, String nombre, int edad) {
        validarIdUnico(id);
        AldeanoCazadorGranjero aldeano = new AldeanoCazadorGranjero(id, nombre, edad);
        aldeanos.add(aldeano);
        return aldeano;
    }

    // ── Busqueda ─────────────────────────────────────────────────────────

    /**
     * Busca un aldeano por su id.
     *
     * @throws IllegalArgumentException si no se encuentra.
     */
    public Aldeano buscarPorId(String id) {
        for (Aldeano aldeano : aldeanos) {
            if (aldeano.getId().equals(id)) {
                return aldeano;
            }
        }
        throw new IllegalArgumentException("No se encontro aldeano con id '" + id + "'.");
    }

    /**
     * @return Lista de todos los aldeanos que son Pescador.
     */
    public List<Aldeano> filtrarPescadores() {
        List<Aldeano> resultado = new ArrayList<>();
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Pescador) {
                resultado.add(aldeano);
            }
        }
        return resultado;
    }

    /**
     * @return Lista de todos los aldeanos que son Cazador.
     */
    public List<Aldeano> filtrarCazadores() {
        List<Aldeano> resultado = new ArrayList<>();
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Cazador) {
                resultado.add(aldeano);
            }
        }
        return resultado;
    }

    /**
     * @return Lista de todos los aldeanos que son Granjero.
     */
    public List<Aldeano> filtrarGranjeros() {
        List<Aldeano> resultado = new ArrayList<>();
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Granjero) {
                resultado.add(aldeano);
            }
        }
        return resultado;
    }

    /**
     * @return Lista de todos los aldeanos que tienen exactamente dos profesiones.
     */
    public List<Aldeano> filtrarDobleProfesion() {
        List<Aldeano> resultado = new ArrayList<>();
        for (Aldeano aldeano : aldeanos) {
            if (aldeano.tieneDobleProfesion()) {
                resultado.add(aldeano);
            }
        }
        return resultado;
    }

    // ── Estadisticas ─────────────────────────────────────────────────────

    /**
     * @return Total de peces capturados por todos los pescadores de la aldea.
     */
    public int getTotalPeces() {
        int total = 0;
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Pescador) {
                total += ((Pescador) aldeano).getCapturaPeces();
            }
        }
        return total;
    }

    /**
     * @return Total de animales cazados por todos los cazadores de la aldea.
     */
    public int getTotalAnimales() {
        int total = 0;
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Cazador) {
                total += ((Cazador) aldeano).getCapturaAnimales();
            }
        }
        return total;
    }

    /**
     * @return Total de kilogramos cosechados por todos los granjeros de la aldea.
     */
    public int getTotalCosecha() {
        int total = 0;
        for (Aldeano aldeano : aldeanos) {
            if (aldeano instanceof Granjero) {
                total += ((Granjero) aldeano).getCosecha();
            }
        }
        return total;
    }

    // ── Validacion privada ───────────────────────────────────────────────

    private void validarIdUnico(String id) {
        for (Aldeano existente : aldeanos) {
            if (existente.getId().equals(id)) {
                throw new IllegalArgumentException(
                        "Ya existe un aldeano con id '" + id + "'.");
            }
        }
    }
}

