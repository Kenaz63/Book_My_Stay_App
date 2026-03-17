import java.util.LinkedList;
import java.util.Queue;

/**
 * UC5 - Booking Request Queue (First-Come-First-Served)
 *
 * This class demonstrates how booking requests are collected
 * and stored in a queue to ensure fair processing order.
 *
 * No inventory updates or room allocation are performed here.
 *
 * @author
 * @version 5.0
 */

// Reservation class (represents booking intent)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue class
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO Order) ---");

        if (queue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        for (Reservation r : queue) {
            r.displayRequest();
        }
    }
}

// Main Application Class
public class UC5 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v5.0");
        System.out.println("=====================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");
        Reservation r4 = new Reservation("David", "Single Room");

        // Add requests to queue (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Display queued requests
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in arrival order.");
        System.out.println("No allocation performed at this stage.");
    }
}