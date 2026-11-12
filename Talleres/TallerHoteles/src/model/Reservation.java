package model;

/**
 * Represents a hotel reservation with a state machine:
 * PENDING → CONFIRMED (via confirm) or CANCELLED (via cancel);
 * CONFIRMED → CANCELLED; CANCELLED is final.
 *
 * Cost calculations delegate to the assigned Room.
 */
public class Reservation {

    private final String id;
    private final int guestCount;
    private final int nights;
    private ReservationStatus status;
    private final Guest guest;
    private final Room room;
    private Voucher voucher;

    Reservation(String id, Guest guest, Room room, int guestCount, int nights) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Reservation id must not be null or blank.");
        }
        if (guest == null) {
            throw new IllegalArgumentException("Guest must not be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room must not be null.");
        }
        if (guestCount < 1 || guestCount > room.getMaxCapacity()) {
            throw new IllegalArgumentException(
                    "Guest count must be between 1 and " + room.getMaxCapacity() + ".");
        }
        if (nights < 1) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }
        if (!room.isAvailable()) {
            throw new IllegalStateException(
                    "Room " + room.getNumber() + " is not available for reservation.");
        }

        this.id = id;
        this.guest = guest;
        this.room = room;
        this.guestCount = guestCount;
        this.nights = nights;
        this.status = ReservationStatus.PENDING;
        this.voucher = null;

        // Maintain bidirectional relationships
        room.addReservation(this);
        guest.addReservation(this);
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public int getNights() {
        return nights;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    /**
     * A reservation is active when its status is PENDING or CONFIRMED.
     */
    public boolean isActive() {
        return status == ReservationStatus.PENDING
                || status == ReservationStatus.CONFIRMED;
    }

    // ── Cost calculations (delegate to Room) ─────────────────────────────

    public double calculateNightlyCost() {
        return room.calculateNightlyCost(guestCount);
    }

    public double calculateTotalCost() {
        return room.calculateTotalCost(guestCount, nights);
    }

    // ── State transitions ────────────────────────────────────────────────

    /**
     * Package-private: only Hotel may confirm a reservation, after validating
     * the voucher number. Transitions PENDING → CONFIRMED and generates a Voucher.
     */
    Voucher confirm(String voucherNumber) {
        if (status != ReservationStatus.PENDING) {
            throw new IllegalStateException(
                    "Only a PENDING reservation can be confirmed. Current status: " + status + ".");
        }
        this.status = ReservationStatus.CONFIRMED;
        this.voucher = new Voucher(voucherNumber, this);
        return this.voucher;
    }

    /**
     * Cancels the reservation. PENDING or CONFIRMED → CANCELLED.
     * Frees the room for new reservations.
     */
    public void cancel() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        this.status = ReservationStatus.CANCELLED;
    }
}

