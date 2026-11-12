package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for all room types. Encapsulates common attributes (number, floor,
 * bed configuration, amenities) and surcharge calculations. Each concrete subclass
 * implements {@link #calculateBedRate(int)} to apply its own pricing policy.
 */
public abstract class Room {

    private final int number;
    private final int floor;
    private final int singleBedCount;
    private final int doubleBedCount;
    private final boolean oceanFrontView;
    private final boolean premiumTv;
    private boolean enabled;
    private final Hotel hotel;
    private final List<Reservation> reservations;

    protected Room(Hotel hotel, int number, int floor, int singleBedCount,
                   int doubleBedCount, boolean oceanFrontView, boolean premiumTv) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel must not be null.");
        }
        if (floor < 1 || floor > hotel.getTotalFloors()) {
            throw new IllegalArgumentException(
                    "Floor must be between 1 and " + hotel.getTotalFloors() + ".");
        }

        this.hotel = hotel;
        this.number = number;
        this.floor = floor;
        this.singleBedCount = singleBedCount;
        this.doubleBedCount = doubleBedCount;
        this.oceanFrontView = oceanFrontView;
        this.premiumTv = premiumTv;
        this.enabled = true;
        this.reservations = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public boolean hasOceanFrontView() {
        return oceanFrontView;
    }

    public boolean hasPremiumTv() {
        return premiumTv;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Maximum capacity is derived from the bed configuration:
     * each single bed holds 1 person, each double bed holds 2.
     */
    public int getMaxCapacity() {
        return singleBedCount + (doubleBedCount * 2);
    }

    /**
     * A room is available when it is enabled and has no active
     * (PENDING or CONFIRMED) reservation.
     */
    public boolean isAvailable() {
        if (!enabled) {
            return false;
        }
        for (Reservation reservation : reservations) {
            if (reservation.isActive()) {
                return false;
            }
        }
        return true;
    }

    // ── Enable / Disable ─────────────────────────────────────────────────

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        for (Reservation reservation : reservations) {
            if (reservation.isActive()) {
                throw new IllegalStateException(
                        "Cannot disable room " + number + ": it has an active reservation.");
            }
        }
        this.enabled = false;
    }

    // ── Surcharge calculations ───────────────────────────────────────────

    /**
     * RVM = baseRoomRate × (oceanFrontSurchargePercent / 100), or 0 if no ocean view.
     */
    public double calculateOceanFrontSurcharge() {
        if (!oceanFrontView) {
            return 0.0;
        }
        return hotel.getBaseRoomRate() * (hotel.getOceanFrontSurchargePercent() / 100.0);
    }

    /**
     * RPI = RVM × floor-surcharge-rate.
     * Floors 1–3: 0 %, 4–6: 10 %, 7+: 20 %.
     */
    public double calculateFloorSurcharge() {
        return calculateOceanFrontSurcharge() * hotel.getFloorSurchargeRate(floor);
    }

    /**
     * RCP: fixed premium-TV charge per room per night, or 0 if the room has no premium TV.
     */
    public double calculatePremiumTvSurcharge() {
        if (!premiumTv) {
            return 0.0;
        }
        return hotel.getPremiumTvSurcharge();
    }

    /**
     * TBA = TBH + RVM + RPI + RCP.
     */
    public double calculateAdjustedBaseRate() {
        return hotel.getBaseRoomRate()
                + calculateOceanFrontSurcharge()
                + calculateFloorSurcharge()
                + calculatePremiumTvSurcharge();
    }

    // ── Bed-rate (polymorphic) ───────────────────────────────────────────

    /**
     * TCA – bed rate that depends on the room type and guest count.
     * Each subclass implements its own pricing policy.
     */
    public abstract double calculateBedRate(int guestCount);

    /**
     * Minibar rate: 0 for all rooms except Suite.
     */
    public double calculateMinibarRate() {
        return 0.0;
    }

    // ── Cost calculations ────────────────────────────────────────────────

    /**
     * CPN = TBA + TCA + TMI_suite.
     */
    public double calculateNightlyCost(int guestCount) {
        validateGuestCount(guestCount);
        return calculateAdjustedBaseRate()
                + calculateBedRate(guestCount)
                + calculateMinibarRate();
    }

    /**
     * CTR = CPN × nights.
     */
    public double calculateTotalCost(int guestCount, int nights) {
        if (nights < 1) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }
        return calculateNightlyCost(guestCount) * nights;
    }

    // ── Validation helper ────────────────────────────────────────────────

    protected void validateGuestCount(int guestCount) {
        if (guestCount < 1 || guestCount > getMaxCapacity()) {
            throw new IllegalArgumentException(
                    "Guest count must be between 1 and " + getMaxCapacity()
                            + " for room " + number + ".");
        }
    }

    // ── Package-private: reservation management ──────────────────────────

    void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

