import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (rooms.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        checkAvailability(roomType);

        int count = rooms.get(roomType);
        if (count - 1 < 0) {
            throw new InvalidBookingException("Inventory cannot be negative");
        }

        rooms.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class UC9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        try {
            System.out.println("Enter room type to book (Single/Double/Suite):");
            String roomType = sc.nextLine();

            inventory.bookRoom(roomType);
            System.out.println("Booking successful for " + roomType);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }

        System.out.println();
        inventory.displayInventory();

        sc.close();
    }
}