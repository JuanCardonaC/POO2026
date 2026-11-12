package model;

/**
 * Immutable snapshot created when a reservation is confirmed.
 * Preserves its data even if the reservation is later cancelled.
 */
public class Voucher {

    private final String voucherNumber;
    private final String reservationId;
    private final String guestFullName;
    private final int roomNumber;
    private final int nights;
    private final double totalCost;

    Voucher(String voucherNumber, Reservation reservation) {
        if (voucherNumber == null || voucherNumber.isBlank()) {
            throw new IllegalArgumentException("Voucher number must not be null or blank.");
        }
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation must not be null.");
        }

        this.voucherNumber = voucherNumber;
        this.reservationId = reservation.getId();
        this.guestFullName = reservation.getGuest().getFullName();
        this.roomNumber = reservation.getRoom().getNumber();
        this.nights = reservation.getNights();
        this.totalCost = reservation.calculateTotalCost();
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

