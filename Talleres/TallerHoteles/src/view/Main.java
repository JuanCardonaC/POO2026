package view;

import model.*;

/**
 * Entry point and test driver for the Hotel Management System.
 * Creates hotel "Mar Azul" (8 floors), registers rooms from the physical
 * layout,
 * registers guests, creates/confirms/cancels reservations, and verifies costs
 * against the tariff model. Also tests invalid scenarios to ensure model
 * integrity.
 *
 * Rates used: TBH=$50,000, TCS=$40,000, TMI=$30,000, PVM=30%, RCP=$3,500.
 */
public class Main {

        public static void main(String[] args) {

                System.out.println("==========================================================");
                System.out.println("   SISTEMA DE GESTION HOTELERA — Hotel Mar Azul");
                System.out.println("==========================================================\n");

                // ─── 1. Create Hotel ─────────────────────────────────────────────
                System.out.println("--- 1. CREACION DEL HOTEL ---\n");

                Hotel hotel = new Hotel(
                                "H001", "Mar Azul", "Calle del Mar 123, Santa Marta",
                                8, // totalFloors
                                50000.0, // baseRoomRate (TBH)
                                40000.0, // singleBedRate (TCS)
                                30000.0, // minibarRate (TMI)
                                30.0, // oceanFrontSurchargePercent (PVM = 30%)
                                3500.0 // premiumTvSurcharge (RCP)
                );

                System.out.println("Hotel creado: " + hotel.getName());
                System.out.println("  Direccion: " + hotel.getAddress());
                System.out.println("  Pisos: " + hotel.getTotalFloors());
                System.out.println("  TBH: $" + format(hotel.getBaseRoomRate()));
                System.out.println("  TCS: $" + format(hotel.getSingleBedRate()));
                System.out.println("  TMI: $" + format(hotel.getMinibarRate()));
                System.out.println("  PVM: " + hotel.getOceanFrontSurchargePercent() + "%");
                System.out.println("  RCP: $" + format(hotel.getPremiumTvSurcharge()));
                System.out.println("  TCD1 (derivada): $" + format(hotel.getDoubleBedOneGuestRate()));
                System.out.println("  TCD2 (derivada): $" + format(hotel.getDoubleBedTwoGuestsRate()));
                System.out.println("  TPA  (derivada): $" + format(hotel.getAdditionalGuestRate()));

                // ─── 2. Register Rooms ───────────────────────────────────────────
                System.out.println("\n--- 2. REGISTRO DE HABITACIONES ---\n");

                // Piso 1: pisos 1-3 → recargo 0%
                hotel.addSingleRoom(101, 1, false, false);
                hotel.addDoubleRoom(102, 1, true, false);

                // Piso 2
                hotel.addDoubleRoom(201, 2, true, true);
                hotel.addSingleRoom(202, 2, false, false);

                // Piso 3
                hotel.addDoubleRoom(301, 3, true, false);
                hotel.addSingleRoom(302, 3, false, false);

                // Piso 4: pisos 4-6 → recargo 10%
                hotel.addFamilyRoom(401, 4, 2, true, false); // 2 dobles + 2 sencillas = cap 6
                hotel.addSingleRoom(402, 4, false, true);

                // Piso 5
                hotel.addDoubleRoom(501, 5, true, true);
                hotel.addDoubleRoom(502, 5, false, false);

                // Piso 6
                hotel.addFamilyRoom(601, 6, 3, true, true); // 2 dobles + 3 sencillas = cap 7
                hotel.addFamilyRoom(602, 6, 4, false, false); // 2 dobles + 4 sencillas = cap 8

                // Piso 7: pisos 7+ → recargo 20%
                hotel.addSuite(701, 7, true);
                hotel.addDoubleRoom(702, 7, true, true);

                // Piso 8
                hotel.addSuite(801, 8, true);
                hotel.addSuite(802, 8, true);

                System.out.println("Habitaciones registradas: " + hotel.getRooms().size());
                System.out.println();
                System.out.println(String.format("%-6s %-10s %-5s %-10s %-10s %-4s",
                                "Num", "Tipo", "Piso", "Vista Mar", "TV Premium", "Cap"));
                System.out.println("-".repeat(52));

                for (Room room : hotel.getRooms()) {
                        String type;
                        if (room instanceof Suite)
                                type = "Suite";
                        else if (room instanceof FamilyRoom)
                                type = "Family";
                        else if (room instanceof DoubleRoom)
                                type = "Double";
                        else
                                type = "Single";

                        System.out.println(String.format("%-6d %-10s %-5d %-10s %-10s %-4d",
                                        room.getNumber(), type, room.getFloor(),
                                        room.hasOceanFrontView() ? "Si" : "No",
                                        room.hasPremiumTv() ? "Si" : "No",
                                        room.getMaxCapacity()));
                }

                // ─── 3. Register Guests ──────────────────────────────────────────
                System.out.println("\n--- 3. REGISTRO DE HUESPEDES ---\n");

                Guest g1 = hotel.registerGuest("G001", "Carlos Perez", "CC-1001", "300-111-2222", "carlos@mail.com");
                Guest g2 = hotel.registerGuest("G002", "Ana Gomez", "CC-2002", "310-333-4444", "ana@mail.com");
                Guest g3 = hotel.registerGuest("G003", "Luis Torres", "CC-3003", "320-555-6666", "luis@mail.com");
                Guest g4 = hotel.registerGuest("G004", "Maria Lopez", "CC-4004", "315-777-8888", "maria@mail.com");

                System.out.println("Huespedes registrados:");
                System.out.println("  " + g1.getId() + " - " + g1.getFullName());
                System.out.println("  " + g2.getId() + " - " + g2.getFullName());
                System.out.println("  " + g3.getId() + " - " + g3.getFullName());
                System.out.println("  " + g4.getId() + " - " + g4.getFullName());

                // ─── 4. Cost Verification (Piso 8, vista al mar, TV premium, 3 noches) ──
                System.out.println("\n--- 4. VERIFICACION DE COSTOS ---");
                System.out.println("    Tabla de referencia: Piso 8, vista al mar, TV premium, 3 noches\n");

                // Suite 801 (piso 8, vista mar, TV premium siempre)
                Room suite801 = hotel.findRoomByNumber(801);
                System.out.println("Suite 801 (1 o 2 huespedes):");
                System.out.println("  TBA = $" + format(suite801.calculateAdjustedBaseRate()));
                System.out.println("  Costo/noche (2 huespedes) = $" + format(suite801.calculateNightlyCost(2)));
                System.out.println("  Total 3 noches = $" + format(suite801.calculateTotalCost(2, 3)));
                System.out.println("  Esperado: $177,500/noche = $532,500 total");

                System.out.println();

                // Verify TBA components for room 801
                System.out.println("  Desglose TBA hab 801:");
                System.out.println("    TBH = $" + format(hotel.getBaseRoomRate()));
                System.out.println("    RVM = $" + format(suite801.calculateOceanFrontSurcharge()));
                System.out.println("    RPI = $" + format(suite801.calculateFloorSurcharge()));
                System.out.println("    RCP = $" + format(suite801.calculatePremiumTvSurcharge()));
                System.out.println("    TBA = $" + format(suite801.calculateAdjustedBaseRate()));

                // ─── 5. Reservations — Valid Scenarios ───────────────────────────
                System.out.println("\n--- 5. RESERVAS VALIDAS ---\n");

                // Reservation 1: Suite 801, 2 guests, 3 nights
                Reservation r1 = hotel.createReservation("R001", "G001", 801, 2, 3);
                System.out.println("Reserva R001 creada:");
                System.out.println("  Huesped: " + r1.getGuest().getFullName());
                System.out.println("  Habitacion: " + r1.getRoom().getNumber() + " (Suite)");
                System.out.println("  Huespedes: " + r1.getGuestCount() + " | Noches: " + r1.getNights());
                System.out.println("  Estado: " + r1.getStatus());
                System.out.println("  Costo/noche: $" + format(r1.calculateNightlyCost()));
                System.out.println("  Costo total: $" + format(r1.calculateTotalCost()));

                System.out.println();

                // Reservation 2: Double 702, 1 guest, 2 nights (piso 7, vista mar, TV premium)
                Reservation r2 = hotel.createReservation("R002", "G002", 702, 1, 2);
                System.out.println("Reserva R002 creada:");
                System.out.println("  Huesped: " + r2.getGuest().getFullName());
                System.out.println("  Habitacion: " + r2.getRoom().getNumber() + " (Doble)");
                System.out.println("  Huespedes: " + r2.getGuestCount() + " | Noches: " + r2.getNights());
                System.out.println("  Costo total: $" + format(r2.calculateTotalCost()));

                System.out.println();

                // Reservation 3: Family 601, 7 guests, 2 nights (piso 6, vista mar, TV premium)
                Reservation r3 = hotel.createReservation("R003", "G003", 601, 7, 2);
                System.out.println("Reserva R003 creada:");
                System.out.println("  Huesped: " + r3.getGuest().getFullName());
                System.out.println("  Habitacion: " + r3.getRoom().getNumber() + " (Familiar, cap 7)");
                System.out.println("  Huespedes: " + r3.getGuestCount() + " | Noches: " + r3.getNights());
                System.out.println("  Costo total: $" + format(r3.calculateTotalCost()));

                System.out.println();

                // Reservation 4: Single 101, 1 guest, 5 nights (piso 1, sin vista, sin TV
                // premium)
                Reservation r4 = hotel.createReservation("R004", "G004", 101, 1, 5);
                System.out.println("Reserva R004 creada:");
                System.out.println("  Huesped: " + r4.getGuest().getFullName());
                System.out.println("  Habitacion: " + r4.getRoom().getNumber() + " (Individual, sin recargos)");
                System.out.println("  Costo/noche: $" + format(r4.calculateNightlyCost()));
                System.out.println("  Costo total: $" + format(r4.calculateTotalCost()));
                System.out.println("  Esperado: TBA=$50,000 + TCS=$40,000 = $90,000/noche x 5 = $450,000");

                // ─── 6. Confirm & Voucher ────────────────────────────────────────
                System.out.println("\n--- 6. CONFIRMACION Y COMPROBANTES ---\n");

                Voucher v1 = hotel.confirmReservation("R001", "VCH-0001");
                System.out.println("Reserva R001 confirmada. Comprobante generado:");
                System.out.println("  Numero: " + v1.getVoucherNumber());
                System.out.println("  Reserva: " + v1.getReservationId());
                System.out.println("  Huesped: " + v1.getGuestFullName());
                System.out.println("  Habitacion: " + v1.getRoomNumber());
                System.out.println("  Noches: " + v1.getNights());
                System.out.println("  Costo total: $" + format(v1.getTotalCost()));
                System.out.println("  Estado reserva: " + r1.getStatus());

                System.out.println();

                // Look up voucher by number
                Voucher found = hotel.findVoucherByNumber("VCH-0001");
                System.out.println("Consulta comprobante VCH-0001:");
                System.out.println("  Encontrado: " + found.getVoucherNumber()
                                + " — Huesped: " + found.getGuestFullName()
                                + " — Total: $" + format(found.getTotalCost()));

                // ─── 7. Cancel Reservations ──────────────────────────────────────
                System.out.println("\n--- 7. CANCELACION DE RESERVAS ---\n");

                // Cancel confirmed reservation R001
                hotel.cancelReservation("R001");
                System.out.println("Reserva R001 cancelada. Estado: " + r1.getStatus());
                System.out.println("  Habitacion 801 disponible: " + hotel.findRoomByNumber(801).isAvailable());

                // Voucher persists after cancellation
                Voucher vAfterCancel = hotel.findVoucherByNumber("VCH-0001");
                System.out.println("  Comprobante VCH-0001 aun existe: " + (vAfterCancel != null));
                System.out.println("  Datos: Huesped=" + vAfterCancel.getGuestFullName()
                                + ", Total=$" + format(vAfterCancel.getTotalCost()));

                System.out.println();

                // Cancel pending reservation R002
                hotel.cancelReservation("R002");
                System.out.println("Reserva R002 (pendiente) cancelada. Estado: " + r2.getStatus());

                // ─── 8. Available Rooms ──────────────────────────────────────────
                System.out.println("\n--- 8. BUSQUEDA DE HABITACIONES DISPONIBLES ---\n");

                System.out.println("Disponibles para 2 huespedes (cualquier TV):");
                for (Room r : hotel.findAvailableRooms(2, false)) {
                        System.out.println("  Hab " + r.getNumber() + " (piso " + r.getFloor() + ")");
                }

                System.out.println();
                System.out.println("Disponibles para 1 huesped con TV premium:");
                for (Room r : hotel.findAvailableRooms(1, true)) {
                        System.out.println("  Hab " + r.getNumber() + " (piso " + r.getFloor() + ")");
                }

                // ─── 9. Reservation with Premium TV ─────────────────────────────
                System.out.println("\n--- 9. RESERVA CON TV PREMIUM ---\n");

                Reservation r5 = hotel.createReservationWithPremiumTv("R005", "G001", 2, 3);
                System.out.println("Reserva R005 (con TV premium) creada:");
                System.out.println("  Habitacion asignada: " + r5.getRoom().getNumber()
                                + " (TV premium: " + r5.getRoom().hasPremiumTv() + ")");
                System.out.println("  Costo total: $" + format(r5.calculateTotalCost()));

                // ─── 10. Disable / Enable Room ───────────────────────────────────
                System.out.println("\n--- 10. HABILITAR / DESHABILITAR HABITACIONES ---\n");

                Room room302 = hotel.findRoomByNumber(302);
                room302.disable();
                System.out.println("Habitacion 302 deshabilitada. Disponible: " + room302.isAvailable());

                room302.enable();
                System.out.println("Habitacion 302 habilitada.    Disponible: " + room302.isAvailable());

                // ─── 11. Invalid Scenarios ───────────────────────────────────────
                System.out.println("\n--- 11. PRUEBAS DE CASOS INVALIDOS ---\n");

                // 11.1 Hotel with less than 8 floors
                tryInvalid("11.1 Hotel con menos de 8 pisos",
                                () -> new Hotel("X", "X", "X", 5, 50000, 40000, 30000, 30, 3500));

                // 11.2 Room on invalid floor (0)
                tryInvalid("11.2 Habitacion en piso 0", () -> hotel.addSingleRoom(999, 0, false, false));

                // 11.3 Room on floor > totalFloors
                tryInvalid("11.3 Habitacion en piso 10 (hotel de 8)", () -> hotel.addSingleRoom(999, 10, false, false));

                // 11.4 Suite not on top floors
                tryInvalid("11.4 Suite en piso 5 (debe ser 7 u 8)", () -> hotel.addSuite(999, 5, true));

                // 11.5 FamilyRoom with invalid single bed count
                tryInvalid("11.5 Familiar con 1 cama sencilla", () -> hotel.addFamilyRoom(999, 3, 1, false, false));

                tryInvalid("11.5b Familiar con 5 camas sencillas", () -> hotel.addFamilyRoom(998, 3, 5, false, false));

                // 11.6 Duplicate room number
                tryInvalid("11.6 Numero de habitacion duplicado (101)",
                                () -> hotel.addSingleRoom(101, 1, false, false));

                // 11.7 Reserve disabled room
                tryInvalid("11.7 Reservar habitacion deshabilitada", () -> {
                        Room rm = hotel.findRoomByNumber(302);
                        rm.disable();
                        try {
                                hotel.createReservation("RX1", "G002", 302, 1, 1);
                        } finally {
                                rm.enable();
                        }
                });

                // 11.8 Reserve room with active reservation
                tryInvalid("11.8 Reservar habitacion con reserva activa (601)",
                                () -> hotel.createReservation("RX2", "G002", 601, 3, 1));

                // 11.9 Guest count = 0
                tryInvalid("11.9 Reservar con 0 huespedes", () -> hotel.createReservation("RX3", "G002", 202, 0, 1));

                // 11.10 Guest count > capacity
                tryInvalid("11.10 Reservar con 3 huespedes en individual (cap 1)",
                                () -> hotel.createReservation("RX4", "G002", 202, 3, 1));

                // 11.11 Confirm already cancelled reservation
                tryInvalid("11.11 Confirmar reserva cancelada (R001)",
                                () -> hotel.confirmReservation("R001", "VCH-9999"));

                // 11.12 Cancel already cancelled reservation
                tryInvalid("11.12 Cancelar reserva ya cancelada (R001)", () -> hotel.cancelReservation("R001"));

                // 11.13 Duplicate guest id
                tryInvalid("11.13 Huesped con id duplicado",
                                () -> hotel.registerGuest("G001", "Otro", "CC-9999", "300-000", "otro@mail.com"));

                // 11.14 Duplicate document number
                tryInvalid("11.14 Huesped con documento duplicado",
                                () -> hotel.registerGuest("G099", "Otro", "CC-1001", "300-000", "otro@mail.com"));

                // 11.15 Reservation with unregistered guest
                tryInvalid("11.15 Reserva con huesped no registrado",
                                () -> hotel.createReservation("RX5", "G999", 202, 1, 1));

                // 11.16 Duplicate voucher number
                System.out.println("  (Preparacion: R003 confirmada con VCH-0003)");
                tryInvalid("11.16 Numero de comprobante duplicado", () -> hotel.confirmReservation("R004", "VCH-0003"));

                // 11.17 Disable room with active reservation
                tryInvalid("11.17 Deshabilitar habitacion con reserva activa (601)",
                                () -> hotel.findRoomByNumber(601).disable());

                // 11.18 Voucher not found
                tryInvalid("11.18 Buscar comprobante inexistente", () -> hotel.findVoucherByNumber("VCH-FAKE"));

                // ─── 12. Cost Comparison Table ───────────────────────────────────
                System.out.println("\n--- 12. TABLA COMPARATIVA DE COSTOS ---");
                System.out.println("    Habitaciones en piso 8, con vista al mar y TV premium, 3 noches\n");

                // Re-create rooms temporarily for cost verification (suites 801, 802 already
                // exist on floor 8)
                System.out.println(String.format("%-15s %-10s %15s %15s",
                                "Tipo", "Huespedes", "Costo/Noche", "Total 3 Noches"));
                System.out.println("-".repeat(58));

                // Suite 801 — already on floor 8
                System.out.println(String.format("%-15s %-10d %15s %15s",
                                "Suite", 1,
                                "$" + format(suite801.calculateNightlyCost(1)),
                                "$" + format(suite801.calculateTotalCost(1, 3))));
                System.out.println(String.format("%-15s %-10d %15s %15s",
                                "Suite", 2,
                                "$" + format(suite801.calculateNightlyCost(2)),
                                "$" + format(suite801.calculateTotalCost(2, 3))));

                System.out.println("\n==========================================================");
                System.out.println("   FIN DE PRUEBAS");
                System.out.println("==========================================================");
        }

        // ── Utility ──────────────────────────────────────────────────────────

        /**
         * Formats a number with dot separators (Colombian style) and no decimals.
         */
        private static String format(double value) {
                return String.format("%,.0f", value).replace(',', '.');
        }

        /**
         * Runs a block that is expected to throw an exception, prints the result.
         */
        private static void tryInvalid(String label, Runnable action) {
                try {
                        action.run();
                        System.out.println("  [FALLO] " + label + " — NO lanzo excepcion.");
                } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("  [OK]    " + label + " — " + e.getMessage());
                }
        }
}
