package view;

import model.*;

/**
 * Punto de entrada y demostracion del sistema de gestion de la aldea.
 * Registra aldeanos con distintas profesiones (simples y dobles),
 * simula jornadas de trabajo y muestra filtros y estadisticas.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println("   SISTEMA DE GESTION DE ALDEA");
        System.out.println("==========================================================\n");

        // ─── 1. Crear la Aldea ──────────────────────────────────────────
        System.out.println("--- 1. CREACION DE LA ALDEA ---\n");

        Aldea aldea = new Aldea("Villa Esperanza");
        System.out.println("Aldea creada: " + aldea.getNombre());

        // ─── 2. Registrar Aldeanos ──────────────────────────────────────
        System.out.println("\n--- 2. REGISTRO DE ALDEANOS ---\n");

        // Profesion simple
        AldeanoPescador a1 = aldea.registrarPescador("A001", "Pedro Rios", 30);
        AldeanoCazador a2 = aldea.registrarCazador("A002", "Diana Montoya", 25);
        AldeanoGranjero a3 = aldea.registrarGranjero("A003", "Carlos Tierra", 40);

        // Doble profesion
        AldeanoPescadorCazador a4 = aldea.registrarPescadorCazador("A004", "Laura Selva", 28);
        AldeanoPescadorGranjero a5 = aldea.registrarPescadorGranjero("A005", "Andres Costa", 35);
        AldeanoCazadorGranjero a6 = aldea.registrarCazadorGranjero("A006", "Maria Bosque", 32);

        System.out.println("Aldeanos registrados: " + aldea.getCantidadAldeanos());
        System.out.println();

        System.out.println(String.format("%-6s %-20s %-5s %-30s %s",
                "ID", "Nombre", "Edad", "Profesiones", "Doble?"));
        System.out.println("-".repeat(75));

        for (Aldeano a : aldea.getAldeanos()) {
            System.out.println(String.format("%-6s %-20s %-5d %-30s %s",
                    a.getId(), a.getNombre(), a.getEdad(),
                    String.join(", ", a.getProfesiones()),
                    a.tieneDobleProfesion() ? "Si" : "No"));
        }

        // ─── 3. Simular Jornadas de Trabajo ─────────────────────────────
        System.out.println("\n--- 3. JORNADAS DE TRABAJO ---\n");

        // Pedro pesca
        a1.pescar(12);
        a1.pescar(8);
        System.out.println(a1.getNombre() + " pesco en 2 jornadas: " + a1.getCapturaPeces() + " peces.");

        // Diana caza
        a2.cazar(5);
        a2.cazar(3);
        System.out.println(a2.getNombre() + " cazo en 2 jornadas: " + a2.getCapturaAnimales() + " animales.");

        // Carlos cultiva
        a3.cultivar(50);
        a3.cultivar(30);
        System.out.println(a3.getNombre() + " cosecho en 2 jornadas: " + a3.getCosecha() + " kg.");

        // Laura (Pescadora-Cazadora)
        a4.pescar(6);
        a4.cazar(4);
        System.out.println(a4.getNombre() + " pesco " + a4.getCapturaPeces()
                + " peces y cazo " + a4.getCapturaAnimales() + " animales.");

        // Andres (Pescador-Granjero)
        a5.pescar(10);
        a5.cultivar(25);
        System.out.println(a5.getNombre() + " pesco " + a5.getCapturaPeces()
                + " peces y cosecho " + a5.getCosecha() + " kg.");

        // Maria (Cazadora-Granjera)
        a6.cazar(7);
        a6.cultivar(40);
        System.out.println(a6.getNombre() + " cazo " + a6.getCapturaAnimales()
                + " animales y cosecho " + a6.getCosecha() + " kg.");

        // ─── 4. Filtrar por Profesion ───────────────────────────────────
        System.out.println("\n--- 4. FILTROS POR PROFESION ---\n");

        System.out.println("Pescadores:");
        for (Aldeano a : aldea.filtrarPescadores()) {
            System.out.println("  " + a.getId() + " - " + a.getNombre()
                    + " (" + ((Pescador) a).getCapturaPeces() + " peces)");
        }

        System.out.println("\nCazadores:");
        for (Aldeano a : aldea.filtrarCazadores()) {
            System.out.println("  " + a.getId() + " - " + a.getNombre()
                    + " (" + ((Cazador) a).getCapturaAnimales() + " animales)");
        }

        System.out.println("\nGranjeros:");
        for (Aldeano a : aldea.filtrarGranjeros()) {
            System.out.println("  " + a.getId() + " - " + a.getNombre()
                    + " (" + ((Granjero) a).getCosecha() + " kg)");
        }

        System.out.println("\nAldeanos con doble profesion:");
        for (Aldeano a : aldea.filtrarDobleProfesion()) {
            System.out.println("  " + a.getId() + " - " + a.getNombre()
                    + " [" + String.join(", ", a.getProfesiones()) + "]");
        }

        // ─── 5. Estadisticas Generales ──────────────────────────────────
        System.out.println("\n--- 5. ESTADISTICAS DE LA ALDEA ---\n");

        System.out.println("Aldea: " + aldea.getNombre());
        System.out.println("  Total aldeanos:             " + aldea.getCantidadAldeanos());
        System.out.println("  Total peces capturados:     " + aldea.getTotalPeces());
        System.out.println("  Total animales cazados:     " + aldea.getTotalAnimales());
        System.out.println("  Total kg cosechados:        " + aldea.getTotalCosecha());

        // ─── 6. Resumen Individual ──────────────────────────────────────
        System.out.println("\n--- 6. RESUMEN INDIVIDUAL ---\n");

        for (Aldeano a : aldea.getAldeanos()) {
            System.out.println(a.getResumen());
            System.out.println();
        }

        // ─── 7. Polimorfismo via Interfaces ─────────────────────────────
        System.out.println("--- 7. POLIMORFISMO VIA INTERFACES ---\n");

        System.out.println("Tratando a todos los pescadores uniformemente:");
        for (Aldeano a : aldea.filtrarPescadores()) {
            Pescador p = (Pescador) a;
            p.pescar(5); // todos pescan 5 mas
            System.out.println("  " + a.getNombre() + " ahora tiene " + p.getCapturaPeces() + " peces.");
        }

        // ─── 8. Pruebas de Casos Invalidos ──────────────────────────────
        System.out.println("\n--- 8. PRUEBAS DE CASOS INVALIDOS ---\n");

        tryInvalid("8.1 Id duplicado",
                () -> aldea.registrarPescador("A001", "Otro Nombre", 20));

        tryInvalid("8.2 Nombre vacio",
                () -> aldea.registrarCazador("A099", "", 20));

        tryInvalid("8.3 Edad invalida (0)",
                () -> aldea.registrarGranjero("A099", "Test", 0));

        tryInvalid("8.4 Pescar cantidad negativa",
                () -> a1.pescar(-3));

        tryInvalid("8.5 Cazar cantidad 0",
                () -> a2.cazar(0));

        tryInvalid("8.6 Cultivar cantidad negativa",
                () -> a3.cultivar(-10));

        tryInvalid("8.7 Buscar aldeano inexistente",
                () -> aldea.buscarPorId("A999"));

        System.out.println("\n==========================================================");
        System.out.println("   FIN DE PRUEBAS");
        System.out.println("==========================================================");
    }

    /**
     * Ejecuta un bloque que se espera lance excepcion e imprime el resultado.
     */
    private static void tryInvalid(String label, Runnable action) {
        try {
            action.run();
            System.out.println("  [FALLO] " + label + " — NO lanzo excepcion.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  [OK]    " + label + " — " + e.getMessage());
        }
    }
}

