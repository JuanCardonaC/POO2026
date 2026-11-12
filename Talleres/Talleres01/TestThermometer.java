import java.util.Scanner; 

public class TestThermometer {
    public static void main(String[] args) {

        //se crea el objeto Scanner para leer desde el teclado
        Scanner scan = new Scanner(System.in);

        System.out.println("El rango válido es de -50.0 a 100.0 grados.");

        //se ingresa la temp inicial
        System.out.println("Por favor, ingresa la temperatura inicial: ");
        double startTemp = scan.nextDouble(); // Leemos el número escrito por el usuario

        //se crea el termometro con el valor que ingreso el usuario
        Thermometer myThermometer = new Thermometer(startTemp);
        
        System.out.println("Termómetro creado.");
        System.out.println("Temperatura actual registrada: " + myThermometer.getTemperature());

        //se pide una nueva temperatura para actualizar
        System.out.print("Ingresa una nueva temperatura para registrar: ");
        double newTemp = scan.nextDouble();//se lee el numero otra vez

        //se intenta registrar la nueva temperatura
        boolean successfullyRegistered = myThermometer.recordTemperature(newTemp);

        //se verifica si se pudo cambiar o no
        if (successfullyRegistered) {
            System.out.println("¡Éxito! La temperatura estaba en el rango permitido.");
        } else {
            System.out.println("¡Error! La temperatura está fuera del rango (-50 a 100). No se guardó.");
        }

        //se muestra la temperatura final
        System.out.println("Temperatura final del termómetro: " + myThermometer.getTemperature());
        scan.close();
    }
}