public class Thermometer {

    //Crea los limites de temperatura
    private static final double minimumTemperature = -50.0;
    private static final double maximumTemperature = 100.0;

    private double temperature;

    //Se crea un termometro con temperatura inicial
    //En caso de registrar temperatura invalida la deja en 0.0
    public Thermometer(double initialTemperature){
        if (isWhitinValidRange(initialTemperature)) {
            this.temperature = initialTemperature;
        } else  {
            this.temperature = 0.0;
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean recordTemperature(double newTemperature){
        if (isWhitinValidRange(newTemperature)) {
            this.temperature = newTemperature;
            return true;
        }
        return false;
    }

    private boolean isWhitinValidRange(double value) {
        return value >= minimumTemperature && value <= maximumTemperature;
    }
}