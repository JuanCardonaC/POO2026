package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Guest {

    private final String id;
    private final String fullName;
    private final String documentNumber;
    private final String phone;
    private final String email;
    private final List<Reservation> reservations;

    Guest(String id, String fullName, String documentNumber, String phone, String email) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Guest id must not be null or blank.");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Guest full name must not be null or blank.");
        }
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new IllegalArgumentException("Guest document number must not be null or blank.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Guest phone must not be null or blank.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Guest email must not be null or blank.");
        }

        this.id = id;
        this.fullName = fullName;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

