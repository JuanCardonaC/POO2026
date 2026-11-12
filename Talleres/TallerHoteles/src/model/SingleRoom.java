package model;

/**
 * Single room: one single bed, capacity for one guest.
 * Bed rate = single bed rate (TCS).
 */
public class SingleRoom extends Room {

    public static final int SINGLE_BEDS = 1;
    public static final int DOUBLE_BEDS = 0;

    SingleRoom(Hotel hotel, int number, int floor, boolean oceanFrontView, boolean premiumTv) {
        super(hotel, number, floor, SINGLE_BEDS, DOUBLE_BEDS, oceanFrontView, premiumTv);
    }

    @Override
    public double calculateBedRate(int guestCount) {
        validateGuestCount(guestCount);
        return getHotel().getSingleBedRate();
    }
}

