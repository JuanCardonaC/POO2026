package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta base para todos los aldeanos.
 * Encapsula los datos personales comunes y obliga a las subclases
 * a declarar sus profesiones mediante la implementacion de interfaces.
 */
public abstract class Aldeano {

    private final String id;
    private final String nombre;
    private final int edad;

    protected Aldeano(String id, String nombre, int edad) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id del aldeano no puede ser nulo o vacio.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del aldeano no puede ser nulo o vacio.");
        }
        if (edad < 1) {
            throw new IllegalArgumentException("La edad debe ser al menos 1.");
        }

        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    // ── Profesiones ──────────────────────────────────────────────────────

    /**
     * @return Lista con los nombres de las profesiones que tiene este aldeano.
     */
    public List<String> getProfesiones() {
        List<String> profesiones = new ArrayList<>();
        if (this instanceof Pescador) {
            profesiones.add("Pescador");
        }
        if (this instanceof Cazador) {
            profesiones.add("Cazador");
        }
        if (this instanceof Granjero) {
            profesiones.add("Granjero");
        }
        return Collections.unmodifiableList(profesiones);
    }

    /**
     * @return true si el aldeano tiene exactamente dos profesiones.
     */
    public boolean tieneDobleProfesion() {
        return getProfesiones().size() == 2;
    }

    // ── Representacion textual ───────────────────────────────────────────

    /**
     * @return Resumen completo del aldeano incluyendo profesiones y estadisticas.
     */
    public abstract String getResumen();

    @Override
    public String toString() {
        return id + " - " + nombre + " (edad: " + edad + ") [" +
                String.join(", ", getProfesiones()) + "]";
    }
}

