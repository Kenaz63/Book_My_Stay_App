import java.util.HashMap;
import java.util.Map;

/**
 * UC4 - Room Search & Availability Check
 *
 * This class demonstrates how guests can search for available rooms
 * without modifying system state. It reinforces read-only access,
 * separation of concerns, and defensive programming.
 *
 * @author
 * @version 4.0
 */

// Abstract Room class (Domain Model)
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Inventory Class (Read-only access in this use case)
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // Example: unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service (Read-only logic)
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("\n--- Available Rooms ---\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("-----------------------------");
            }
        }
    }
}

// Main Application Class
public class UC4 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v4.0");
        System.out.println("=====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room objects (domain model)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Perform search (read-only)
        searchService.searchAvailableRooms(rooms);

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}