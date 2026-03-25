import java.util.*;

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> services = reservationServicesMap.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }
        return total;
    }
}

public class UC7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("Enter Reservation ID:");
        String reservationId = sc.nextLine();

        System.out.println("Enter number of add-on services:");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter service name:");
            String name = sc.nextLine();

            System.out.println("Enter service cost:");
            double cost = sc.nextDouble();
            sc.nextLine();

            AddOnService service = new AddOnService(name, cost);
            manager.addService(reservationId, service);
        }

        List<AddOnService> services = manager.getServices(reservationId);

        System.out.println("Services for Reservation ID " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - " + s.getCost());
        }

        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);

        sc.close();
    }
}