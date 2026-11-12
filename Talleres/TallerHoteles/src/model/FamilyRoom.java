package model;

/**
 * Family room: exactly 2 double beds + 2 to 4 single beds (capacity 6–8).
 * Bed rate for 1–6 guests: fixed minimum = 2×TCD2 + 2×TCS.
 * Each guest beyond 6 adds TPA (additional-guest rate).
 */
public class FamilyRoom extends Room {

    public static final int DOUBLE_BEDS = 2;
    public static final int MIN_SINGLE_BEDS = 2;
    public static final int MAX_SINGLE_BEDS = 4;
    public static final int BASE_OCCUPANCY_LIMIT = 6;

    FamilyRoom(Hotel hotel, int number, int floor, int singleBedCount,
               boolean oceanFrontView, boolean premiumTv) {
        super(hotel, number, floor, singleBedCount, DOUBLE_BEDS, oceanFrontView, premiumTv);

        if (singleBedCount < MIN_SINGLE_BEDS || singleBedCount > MAX_SINGLE_BEDS) {
            throw new IllegalArgumentException(
                    "Family room must have between " + MIN_SINGLE_BEDS
                            + " and " + MAX_SINGLE_BEDS + " single beds.");
        }
    }

    @Override
    public double calculateBedRate(int guestCount) {
        validateGuestCount(guestCount);
        Hotel hotel = getHotel();

        // Fixed minimum: 2 double beds occupied by 2 + 2 single beds
        double baseBedRate = 2 * hotel.getDoubleBedTwoGuestsRate()
                + 2 * hotel.getSingleBedRate();

        if (guestCount <= BASE_OCCUPANCY_LIMIT) {
            return baseBedRate;
        }

        // Each guest over 6 adds TPA
        int additionalGuests = guestCount - BASE_OCCUPANCY_LIMIT;
        return baseBedRate + additionalGuests * hotel.getAdditionalGuestRate();
    }
}

