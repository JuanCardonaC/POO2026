public class TestStudent {

    public static void main(String[] args) {
        Student student = new Student("2026-001", "Maria Gomez", 4.0, 3.5, 4.5);

        System.out.println("Información inicial");
        showStudentInfo(student);
        System.out.println();

        attemptUpdateName(student, "Maria Fernanda Gomez");
        attemptUpdateName(student, "");

        attemptUpdateFirstGrade(student, 5.0);
        attemptUpdateSecondGrade(student, 2.8);

        attemptUpdateThirdGrade(student, 5.5);
        attemptUpdateFirstGrade(student, -1.0);

        System.out.println("=== Información final ===");
        showStudentInfo(student);
    }

    private static void showStudentInfo(Student student) {
        System.out.println("Código: " + student.getStudentId());
        System.out.println("Nombre: " + student.getName());
        System.out.println("Primera nota: " + student.getFirstGrade());
        System.out.println("Segunda nota: " + student.getSecondGrade());
        System.out.println("Tercera nota: " + student.getThirdGrade());
        System.out.println("Promedio: " + student.getAverage());
    }

    private static void attemptUpdateName(Student student, String newName) {
        boolean wasAccepted = student.updateName(newName);
        reportResult("nombre a \"" + newName + "\"", wasAccepted, student);
    }

    private static void attemptUpdateFirstGrade(Student student, double newGrade) {
        boolean wasAccepted = student.updateFirstGrade(newGrade);
        reportResult("primera nota a " + newGrade, wasAccepted, student);
    }

    private static void attemptUpdateSecondGrade(Student student, double newGrade) {
        boolean wasAccepted = student.updateSecondGrade(newGrade);
        reportResult("segunda nota a " + newGrade, wasAccepted, student);
    }

    private static void attemptUpdateThirdGrade(Student student, double newGrade) {
        boolean wasAccepted = student.updateThirdGrade(newGrade);
        reportResult("tercera nota a " + newGrade, wasAccepted, student);
    }

    private static void reportResult(String description, boolean wasAccepted, Student student) {
        if (wasAccepted) {
            System.out.println("Modificación de " + description + " -> ACEPTADA");
        } else {
            System.out.println("Modificación de " + description + " -> RECHAZADA");
        }
        System.out.println("Promedio actual: " + student.getAverage());
        System.out.println();
    }
}