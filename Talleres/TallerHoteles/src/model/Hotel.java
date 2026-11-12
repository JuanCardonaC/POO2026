package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central orchestrator of the hotel management system.
 * Owns rooms (composition), registers guests, manages reservations and vouchers.
 * Acts as a factory for rooms, guests and reservations to enforce all business rules.
 */
public class Hotel {

    // ── Class constants ──────────────────────────────────────────────────

    public static final int MIN_FLOORS = 8;
    public static final double DOUBLE_BED_ONE_GUEST_FACTOR = 1.45;
    public static final double DOUBLE_BED_TWO_GUESTS_FACTOR = 1.90;
    public static final double ADDITIONAL_GUEST_FACTOR = 0.70;
    public static final int LOW_FLOOR_LIMIT = 3;
    public static final int MID_FLOOR_LIMIT = 6;
    public static final double MID_FLOOR_SURCHARGE_RATE = 0.10;
    public static final double HIGH_FLOOR_SURCHARGE_RATE = 0.20;

    // ── Instance fields ──────────────────────────────────────────────────

    private final String id;
    private final String name;
    private final String address;
    private final int totalFloors;
    private final double baseRoomRate;
    private final double singleBedRate;
    private final double minibarRate;
    private final double oceanFrontSurchargePercent;
    private final double premiumTvSurcharge;

    private final List<Room> rooms;
    private final List<Guest> guests;
    private final List<Reservation> reservations;
    private final Map<String, Voucher> vouchersByNumber;

    // ── Constructor ──────────────────────────────────────────────────────

    public Hotel(String id, String name, String address, int totalFloors,
                 double baseRoomRate, double singleBedRate, double minibarRate,
                 double oceanFrontSurchargePercent, double premiumTvSurcharge) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Hotel id must not be null or blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Hotel name must not be null or blank.");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Hotel address must not be null or blank.");
        }
        if (totalFloors < MIN_FLOORS) {
            throw new IllegalArgumentException(
                    "Hotel must have at least " + MIN_FLOORS + " floors.");
        }
        if (baseRoomRate <= 0) {
            throw new IllegalArgumentException("Base room rate must be greater than zero.");
        }
        if (singleBedRate <= 0) {
            throw new IllegalArgumentException("Single bed rate must be greater than zero.");
        }
        if (minibarRate <= 0) {
            throw new IllegalArgumentException("Minibar rate must be greater than zero.");
        }

        this.id = id;
        this.name = name;
        this.address = address;
        this.totalFloors = totalFloors;
        this.baseRoomRate = baseRoomRate;
        this.singleBedRate = singleBedRate;
        this.minibarRate = minibarRate;
        this.oceanFrontSurchargePercent = oceanFrontSurchargePercent;
        this.premiumTvSurcharge = premiumTvSurcharge;

        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.vouchersByNumber = new HashMap<>();
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public double getBaseRoomRate() {
        return baseRoomRate;
    }

    public double getSingleBedRate() {
        return singleBedRate;
    }

    public double getMinibarRate() {
        return minibarRate;
    }

    public double getOceanFrontSurchargePercent() {
        return oceanFrontSurchargePercent;
    }

    public double getPremiumTvSurcharge() {
        return premiumTvSurcharge;
    }

    // ── Derived rates ────────────────────────────────────────────────────

    public double getDoubleBedOneGuestRate() {
        return singleBedRate * DOUBLE_BED_ONE_GUEST_FACTOR;
    }

    public double getDoubleBedTwoGuestsRate() {
        return singleBedRate * DOUBLE_BED_TWO_GUESTS_FACTOR;
    }

    public double getAdditionalGuestRate() {
        return singleBedRate * ADDITIONAL_GUEST_FACTOR;
    }

    /**
     * Returns the floor surcharge rate as a decimal:
     * floors 1–3 → 0.0, floors 4–6 → 0.10, floors 7+ → 0.20.
     */
    public double getFloorSurchargeRate(int floor) {
        if (floor <= LOW_FLOOR_LIMIT) {
            return 0.0;
        }
        if (floor <= MID_FLOOR_LIMIT) {
            return MID_FLOOR_SURCHARGE_RATE;
        }
        return HIGH_FLOOR_SURCHARGE_RATE;
    }

    // ── Room factory methods ─────────────────────────────────────────────

    public SingleRoom addSingleRoom(int number, int floor,
                                    boolean oceanFrontView, boolean premiumTv) {
        SingleRoom room = new SingleRoom(this, number, floor, oceanFrontView, premiumTv);
        registerRoom(room);
        return room;
    }

    public DoubleRoom addDoubleRoom(int number, int floor,
                                    boolean oceanFrontView, boolean premiumTv) {
        DoubleRoom room = new DoubleRoom(this, number, floor, oceanFrontView, premiumTv);
        registerRoom(room);
        return room;
    }

    public FamilyRoom addFamilyRoom(int number, int floor, int singleBedCount,
                                    boolean oceanFrontView, boolean premiumTv) {
        FamilyRoom room = new FamilyRoom(this, number, floor, singleBedCount,
                oceanFrontView, premiumTv);
        registerRoom(room);
        return room;
    }

    public Suite addSuite(int number, int floor, boolean oceanFrontView) {
        Suite suite = new Suite(this, number, floor, oceanFrontView);
        registerRoom(suite);
        return suite;
    }

    private void registerRoom(Room room) {
        for (Room existing : rooms) {
            if (existing.getNumber() == room.getNumber()) {
                throw new IllegalArgumentException(
                        "Room number " + room.getNumber() + " already exists.");
            }
        }
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    // ── Guest registration ───────────────────────────────────────────────

    public Guest registerGuest(String id, String fullName, String documentNumber,
                               String phone, String email) {
        for (Guest existing : guests) {
            if (existing.getId().equals(id)) {
                throw new IllegalArgumentException(
                        "Guest with id '" + id + "' already exists.");
            }
            if (existing.getDocumentNumber().equals(documentNumber)) {
                throw new IllegalArgumentException(
                        "Guest with document number '" + documentNumber + "' already exists.");
            }
        }

        Guest guest = new Guest(id, fullName, documentNumber, phone, email);
        guests.add(guest);
        return guest;
    }

    // ── Reservation management ───────────────────────────────────────────

    public Reservation createReservation(String id, String guestId, int roomNumber,
                                         int guestCount, int nights) {
        validateReservationId(id);
        Guest guest = findGuestById(guestId);
        Room room = findRoomByNumber(roomNumber);

        Reservation reservation = new Reservation(id, guest, room, guestCount, nights);
        reservations.add(reservation);
        return reservation;
    }

    /**
     * Creates a reservation specifically requiring premium TV.
     * Automatically finds an available room with premium TV that fits the guest count.
     */
    public Reservation createReservationWithPremiumTv(String id, String guestId,
                                                      int guestCount, int nights) {
        validateReservationId(id);
        Guest guest = findGuestById(guestId);

        List<Room> available = findAvailableRooms(guestCount, true);
        if (available.isEmpty()) {
            throw new IllegalStateException(
                    "No available room with premium TV for " + guestCount + " guest(s).");
        }

        Room room = available.get(0);
        Reservation reservation = new Reservation(id, guest, room, guestCount, nights);
        reservations.add(reservation);
        return reservation;
    }

    public Voucher confirmReservation(String reservationId, String voucherNumber) {
        if (voucherNumber == null || voucherNumber.isBlank()) {
            throw new IllegalArgumentException("Voucher number must not be null or blank.");
        }
        if (vouchersByNumber.containsKey(voucherNumber)) {
            throw new IllegalArgumentException(
                    "Voucher number '" + voucherNumber + "' already exists.");
        }

        Reservation reservation = findReservationById(reservationId);
        Voucher voucher = reservation.confirm(voucherNumber);
        vouchersByNumber.put(voucherNumber, voucher);
        return voucher;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        reservation.cancel();
    }

    // ── Lookup methods ───────────────────────────────────────────────────

    public Room findRoomByNumber(int number) {
        for (Room room : rooms) {
            if (room.getNumber() == number) {
                return room;
            }
        }
        throw new IllegalArgumentException("Room number " + number + " not found.");
    }

    public Guest findGuestById(String id) {
        for (Guest guest : guests) {
            if (guest.getId().equals(id)) {
                return guest;
            }
        }
        throw new IllegalArgumentException("Guest with id '" + id + "' not found.");
    }

    public Reservation findReservationById(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        throw new IllegalArgumentException("Reservation with id '" + id + "' not found.");
    }

    public Voucher findVoucherByNumber(String voucherNumber) {
        Voucher voucher = vouchersByNumber.get(voucherNumber);
        if (voucher == null) {
            throw new IllegalArgumentException(
                    "Voucher with number '" + voucherNumber + "' not found.");
        }
        return voucher;
    }

    /**
     * Returns available rooms that can accommodate the given guest count.
     * If requiresPremiumTv is true, only rooms with premium TV are returned.
     */
    public List<Room> findAvailableRooms(int guestCount, boolean requiresPremiumTv) {
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()
                    && room.getMaxCapacity() >= guestCount
                    && (!requiresPremiumTv || room.hasPremiumTv())) {
                result.add(room);
            }
        }
        return result;
    }

    // ── Private helpers ──────────────────────────────────────────────────

    private void validateReservationId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Reservation id must not be null or blank.");
        }
        for (Reservation existing : reservations) {
            if (existing.getId().equals(id)) {
                throw new IllegalArgumentException(
                        "Reservation with id '" + id + "' already exists.");
            }
        }
    }
}

