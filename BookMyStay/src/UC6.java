import java.util.*;

/**
 * UC6 - Reservation Confirmation & Room Allocation
 *
 * This class demonstrates how booking requests are processed,
 * rooms are allocated uniquely, and inventory is updated safely.
 *
 * Ensures:
 * - FIFO processing
 * - No double booking
 * - Immediate inventory synchronization
 *
 * @author
 * @version 6.0
 */

// Reservation class
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
}

// Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Updated Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Booking Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Core Logic)
class BookingService {

    private RoomInventory inventory;

    // Track all allocated room IDs globally
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room type → allocated room IDs
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingRequestQueue queue) {

        System.out.println("\n--- Processing Booking Requests ---");

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("\nProcessing request for: " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = roomType.substring(0, 2).toUpperCase() + roomCounter++;

                // Ensure uniqueness using Set
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Map room type → allocated IDs
                    allocationMap
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                    // Decrement inventory (synchronized update)
                    inventory.decrement(roomType);

                    // Confirm booking
                    System.out.println("Booking CONFIRMED for " + request.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);

                } else {
                    System.out.println("Error: Duplicate Room ID detected!");
                }

            } else {
                System.out.println("Booking FAILED for " + request.getGuestName() +
                        " (No availability)");
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\n--- Room Allocations ---");

        for (Map.Entry<String, Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Application Class
public class UC6 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v6.0");
        System.out.println("=====================================");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));
        queue.addRequest(new Reservation("Eve", "Double Room")); // may fail

        // Process bookings
        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(queue);

        // Display results
        bookingService.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed successfully.");
    }
}