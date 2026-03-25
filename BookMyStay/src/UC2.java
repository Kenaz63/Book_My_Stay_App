/**
 * UC2 - Room Initialization and Static Availability
 *
 * This class demonstrates basic object-oriented modeling using
 * abstraction, inheritance, and polymorphism for a hotel booking system.
 *
 * @author
 * @version 2.1
 */

// Abstract Room class
abstract class Room {
    private String roomType;
    private int beds;
    private double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}

// Single Room class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Main Application Class
public class UC2 {

    /**
     * Main method - entry point of the application
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v2.1");
        System.out.println("=====================================");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("\n--- Room Details & Availability ---\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("----------------------------------");

        doub.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("----------------------------------");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("----------------------------------");

        System.out.println("\nApplication terminated successfully.");
    }
}