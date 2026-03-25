import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double cost;

    public Reservation(String reservationId, String guestName, String roomType, double cost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.cost = cost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getCost() {
        return cost;
    }
}

class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType() + " | " +
                            r.getCost()
            );
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0.0;

        for (Reservation r : reservations) {
            totalRevenue += r.getCost();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: " + totalRevenue);
    }
}

public class UC8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("Enter number of confirmed bookings:");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter Reservation ID:");
            String id = sc.nextLine();

            System.out.println("Enter Guest Name:");
            String name = sc.nextLine();

            System.out.println("Enter Room Type:");
            String room = sc.nextLine();

            System.out.println("Enter Cost:");
            double cost = sc.nextDouble();
            sc.nextLine();

            Reservation reservation = new Reservation(id, name, room, cost);
            history.addReservation(reservation);
        }

        System.out.println("\n--- Booking History ---");
        reportService.displayAllBookings(history.getAllReservations());

        System.out.println("\n--- Booking Summary ---");
        reportService.generateSummary(history.getAllReservations());

        sc.close();
    }
}