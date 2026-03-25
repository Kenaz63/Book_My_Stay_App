import java.util.*;

class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 1);
        rooms.put("Double", 1);
        rooms.put("Suite", 1);
    }

    public synchronized boolean bookRoom(String roomType, String guestName) {
        if (!rooms.containsKey(roomType)) {
            System.out.println("Invalid room type for " + guestName);
            return false;
        }

        int available = rooms.get(roomType);

        if (available > 0) {
            System.out.println(guestName + " is booking " + roomType);
            rooms.put(roomType, available - 1);
            System.out.println("Booking confirmed for " + guestName);
            return true;
        } else {
            System.out.println("No rooms available for " + guestName + " (" + roomType + ")");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

class BookingProcessor extends Thread {
    private Queue<BookingRequest> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            if (request != null) {
                inventory.bookRoom(request.getRoomType(), request.getGuestName());
            }
        }
    }
}

public class UC11 {
    public static void main(String[] args) {
        Queue<BookingRequest> queue = new LinkedList<>();
        RoomInventory inventory = new RoomInventory();

        queue.add(new BookingRequest("Guest1", "Single"));
        queue.add(new BookingRequest("Guest2", "Single"));
        queue.add(new BookingRequest("Guest3", "Double"));
        queue.add(new BookingRequest("Guest4", "Suite"));
        queue.add(new BookingRequest("Guest5", "Suite"));

        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);
        Thread t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        inventory.displayInventory();
    }
}
