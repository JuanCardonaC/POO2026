package model;

/**
 * Double room: one double bed, capacity for two guests.
 * Bed rate depends on occupancy: TCD1 for one guest, TCD2 for two.
 */
public class DoubleRoom extends Room {

    public static final int SINGLE_BEDS = 0;
    public static final int DOUBLE_BEDS = 1;

    DoubleRoom(Hotel hotel, int number, int floor, boolean oceanFrontView, boolean premiumTv) {
        super(hotel, number, floor, SINGLE_BEDS, DOUBLE_BEDS, oceanFrontView, premiumTv);
    }

    @Override
    public double calculateBedRate(int guestCount) {
        validateGuestCount(guestCount);
        if (guestCount == 1) {
            return getHotel().getDoubleBedOneGuestRate();
        }
        return getHotel().getDoubleBedTwoGuestsRate();
    }
}

