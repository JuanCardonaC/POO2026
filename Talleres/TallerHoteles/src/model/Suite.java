package model;

/**
 * Suite: one double bed, capacity 2, private lounge, premium TV (always),
 * and minibar. May only occupy one of the two topmost floors of the hotel.
 * Bed rate is always TCD2 regardless of occupancy (1 or 2 guests).
 */
public class Suite extends Room {

    public static final int SINGLE_BEDS = 0;
    public static final int DOUBLE_BEDS = 1;
    public static final int TOP_FLOORS_ALLOWED = 2;

    Suite(Hotel hotel, int number, int floor, boolean oceanFrontView) {
        // Suites always have premium TV
        super(hotel, number, floor, SINGLE_BEDS, DOUBLE_BEDS, oceanFrontView, true);

        int minAllowedFloor = hotel.getTotalFloors() - TOP_FLOORS_ALLOWED + 1;
        if (floor < minAllowedFloor) {
            throw new IllegalArgumentException(
                    "Suite must be on one of the top " + TOP_FLOORS_ALLOWED
                            + " floors (floor " + minAllowedFloor + " or higher).");
        }
    }

    /**
     * Suite always charges TCD2, regardless of whether there is 1 or 2 guests.
     */
    @Override
    public double calculateBedRate(int guestCount) {
        validateGuestCount(guestCount);
        return getHotel().getDoubleBedTwoGuestsRate();
    }

    /**
     * Suite is the only room type that includes minibar.
     */
    @Override
    public double calculateMinibarRate() {
        return getHotel().getMinibarRate();
    }
}

