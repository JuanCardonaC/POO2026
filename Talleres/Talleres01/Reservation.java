public class Reservation {

    private String guestName;
    private int roomNumber;
    private int numberOfNights;
    private double pricePerNight;

    public Reservation(String guestName, int roomNumber, int numberOfNights, double pricePerNight) {
        this.guestName = isValidGuestName(guestName) ? guestName : "Unnamed";
        this.roomNumber = roomNumber;
        this.numberOfNights = isValidNumberOfNights(numberOfNights) ? numberOfNights : 1;
        this.pricePerNight = isValidPricePerNight(pricePerNight) ? pricePerNight : 1.0;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public double getTotalCost() {
        return numberOfNights * pricePerNight;
    }

    public boolean updateGuestName(String newGuestName) {
        if (isValidGuestName(newGuestName)) {
            this.guestName = newGuestName;
            return true;
        }
        return false;
    }

    public boolean updateNumberOfNights(int newNumberOfNights) {
        if (isValidNumberOfNights(newNumberOfNights)) {
            this.numberOfNights = newNumberOfNights;
            return true;
        }
        return false;
    }

    public boolean updatePricePerNight(double newPricePerNight) {
        if (isValidPricePerNight(newPricePerNight)) {
            this.pricePerNight = newPricePerNight;
            return true;
        }
        return false;
    }

    private boolean isValidGuestName(String candidateName) {
        return candidateName != null && !candidateName.trim().isEmpty();
    }

    private boolean isValidNumberOfNights(int candidateNights) {
        return candidateNights > 0;
    }

    private boolean isValidPricePerNight(double candidatePrice) {
        return candidatePrice > 0;
    }
}