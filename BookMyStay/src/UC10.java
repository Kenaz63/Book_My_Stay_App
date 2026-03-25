import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class Inventory {
    private Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 1);
        rooms.put("Double", 1);
        rooms.put("Suite", 1);
    }

    public boolean allocateRoom(String roomType) {
        if (rooms.containsKey(roomType) && rooms.get(roomType) > 0) {
            rooms.put(roomType, rooms.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Inventory Status:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

class CancellationService {
    private Map<String, Reservation> bookingMap;
    private Stack<String> rollbackStack;

    public CancellationService() {
        bookingMap = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public void addReservation(Reservation r) {
        bookingMap.put(r.getReservationId(), r);
    }

    public void cancelReservation(String reservationId, Inventory inventory) {
        if (!bookingMap.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        Reservation r = bookingMap.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.releaseRoom(r.getRoomType());

        bookingMap.remove(reservationId);

        System.out.println("Cancellation successful for Reservation ID: " + reservationId);
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack (Released Room IDs): " + rollbackStack);
    }
}

public class UC10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Inventory inventory = new Inventory();
        CancellationService service = new CancellationService();

        System.out.println("Enter Reservation ID:");
        String id = sc.nextLine();

        System.out.println("Enter Room Type (Single/Double/Suite):");
        String type = sc.nextLine();

        System.out.println("Enter Room ID:");
        String roomId = sc.nextLine();

        if (inventory.allocateRoom(type)) {
            Reservation r = new Reservation(id, type, roomId);
            service.addReservation(r);
            System.out.println("Booking Confirmed");
        } else {
            System.out.println("Booking Failed: No availability");
        }

        System.out.println("\nEnter Reservation ID to cancel:");
        String cancelId = sc.nextLine();

        service.cancelReservation(cancelId, inventory);

        System.out.println();
        inventory.displayInventory();
        service.displayRollbackStack();

        sc.close();
    }
}