import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }
        return null;
    }
}

public class UC12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PersistenceService ps = new PersistenceService();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        SystemState loadedState = ps.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
        } else {
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 2);
            inventory.put("Suite", 1);

            bookings = new ArrayList<>();
        }

        System.out.println("Enter Reservation ID:");
        String id = sc.nextLine();

        System.out.println("Enter Room Type (Single/Double/Suite):");
        String type = sc.nextLine();

        if (inventory.containsKey(type) && inventory.get(type) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            bookings.add(new Reservation(id, type));
            System.out.println("Booking successful.");
        } else {
            System.out.println("Booking failed: No availability.");
        }

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r.getReservationId() + " | " + r.getRoomType());
        }

        SystemState currentState = new SystemState(inventory, bookings);
        ps.save(currentState);

        sc.close();
    }
}
