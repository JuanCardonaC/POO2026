import java.util.Scanner;

public class TestProduct {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----Creacion del producto----");
        System.out.print("Ingrese el nombre del producto: ");
        String initialName = scanner.nextLine();

        System.out.print("Ingrese el precio del producto: ");
        double initialPrice = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese el stock disponible: ");
        int initialStock = Integer.parseInt(scanner.nextLine());

        Product product = new Product(initialName, initialPrice, initialStock);

        boolean keepRunning = true;

        while (keepRunning) {
            printMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    showProductInfo(product);
                    break;
                case "2":
                    System.out.print("\nIngrese el nuevo nombre: ");
                    String newName = scanner.nextLine();
                    reportResult("nombre", product.updateName(newName));
                    break;
                case "3":
                    System.out.print("\nIngrese el nuevo precio: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    reportResult("precio", product.updatePrice(newPrice));
                    break;
                case "4":
                    System.out.print("\nIngrese el nuevo stock: ");
                    int newStock = Integer.parseInt(scanner.nextLine());
                    reportResult("stock", product.updateStock(newStock));
                    break;
                case "5":
                    keepRunning = false;
                    System.out.print("Programa finalizado.");
                    break;
                default:
                    System.out.print("Opción no válida.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("----Menu----");
        System.out.println("1. Consultar informacion del producto");
        System.out.println("2. Modificar nombre");
        System.out.println("3. Modificar precio");
        System.out.println("4. Modificar stock");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void showProductInfo(Product product) {
        System.out.println("\nNombre: " + product.getName());
        System.out.println("Precio: " + product.getPrice());
        System.out.println("Stock: " + product.getStock());
    }

    private static void reportResult(String fieldName, boolean wasAccepted) {
        if (wasAccepted) {
            System.out.println("Modificacion de " + fieldName + " -> ACEPTADA");
        } else {
            System.out.println("Modificacion de " + fieldName + " -> RECHAZADA (valor no valido)");
        }
    }
}