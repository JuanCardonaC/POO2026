package model;

/**
 * Represents the lifecycle states of a reservation.
 * PENDING → CONFIRMED or CANCELLED; CONFIRMED → CANCELLED; CANCELLED is final.
 */
public enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

