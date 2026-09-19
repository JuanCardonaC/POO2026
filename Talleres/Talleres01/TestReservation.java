import java.util.Scanner;
    
public class TestReservation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Creación de la reserva ===");
        System.out.print("Ingrese el nombre del huésped: ");
        String guestName = scanner.nextLine();

        System.out.print("Ingrese el número de habitación: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la cantidad de noches: ");
        int numberOfNights = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el valor por noche: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());

        Reservation reservation = new Reservation(guestName, roomNumber, numberOfNights, pricePerNight);

        boolean keepRunning = true;

        while (keepRunning) {
            printMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    showReservationInfo(reservation);
                    break;
                case "2":
                    System.out.print("Ingrese el nuevo nombre del huésped: ");
                    String newGuestName = scanner.nextLine();
                    reportResult("nombre del huésped", reservation.updateGuestName(newGuestName));
                    showTotalCost(reservation);
                    break;
                case "3":
                    System.out.print("Ingrese la nueva cantidad de noches: ");
                    int newNumberOfNights = Integer.parseInt(scanner.nextLine());
                    reportResult("cantidad de noches", reservation.updateNumberOfNights(newNumberOfNights));
                    showTotalCost(reservation);
                    break;
                case "4":
                    System.out.print("Ingrese el nuevo valor por noche: ");
                    double newPricePerNight = Double.parseDouble(scanner.nextLine());
                    reportResult("valor por noche", reservation.updatePricePerNight(newPricePerNight));
                    showTotalCost(reservation);
                    break;
                case "5":
                    keepRunning = false;
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Menú ===");
        System.out.println("1. Consultar información de la reserva");
        System.out.println("2. Modificar nombre del huésped");
        System.out.println("3. Modificar cantidad de noches");
        System.out.println("4. Modificar valor por noche");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void showReservationInfo(Reservation reservation) {
        System.out.println("Huésped: " + reservation.getGuestName());
        System.out.println("Habitación: " + reservation.getRoomNumber());
        System.out.println("Noches: " + reservation.getNumberOfNights());
        System.out.println("Valor por noche: " + reservation.getPricePerNight());
        System.out.println("Costo total: " + reservation.getTotalCost());
    }

    private static void showTotalCost(Reservation reservation) {
        System.out.println("Costo total actual: " + reservation.getTotalCost());
    }

    private static void reportResult(String fieldName, boolean wasAccepted) {
        if (wasAccepted) {
            System.out.println("Modificación de " + fieldName + " -> ACEPTADA");
        } else {
            System.out.println("Modificación de " + fieldName + " -> RECHAZADA (valor inválido)");
        }
    }
}