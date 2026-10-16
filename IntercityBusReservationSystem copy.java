import java.util.*;
import java.util.stream.Collectors;

class BusReservation {
    private int bookingId;
    private String passengerName;
    private String source;
    private String destination;
    private int seats;
    private int fare;

    public BusReservation(int bookingId, String passengerName,
                          String source, String destination,
                          int seats, int fare) {
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.fare = fare;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getDestination() {
        return destination;
    }

    public int getSeats() {
        return seats;
    }

    public int getTotalFare() {
        return seats * fare;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + ", Passenger: " + passengerName
                + ", From: " + source
                + ", To: " + destination
                + ", Seats: " + seats
                + ", Total Fare: Rs. " + getTotalFare();
    }
}

public class IntercityBusReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Collections to store reservation details
        List<BusReservation> reservations = new ArrayList<>();
        Map<Integer, BusReservation> bookingMap = new HashMap<>();
        Set<Integer> bookingIds = new HashSet<>();

        // Sample reservations
        reservations.add(new BusReservation(
                101, "Ravi", "Hyderabad", "Vijayawada", 2, 350));

        reservations.add(new BusReservation(
                102, "Asha", "Hyderabad", "Bengaluru", 1, 800));

        reservations.add(new BusReservation(
                103, "Kiran", "Chennai", "Vijayawada", 3, 350));

        // Populate map and set
        for (BusReservation r : reservations) {
            bookingMap.put(r.getBookingId(), r);
            bookingIds.add(r.getBookingId());
        }

        System.out.println("=== INTERCITY BUS RESERVATION ===");
        System.out.println("1. View all reservations");
        System.out.println("2. Search booking by ID");
        System.out.println("3. Show bookings with fare above Rs. 500");
        System.out.println("4. Sort bookings by passenger name");
        System.out.println("5. Show total bookings");
        System.out.println("6. Generate destination-wise booking report");
        System.out.print("Enter your choice: ");

        try {
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    reservations.forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Enter booking ID: ");
                    int id = sc.nextInt();

                    Optional.ofNullable(bookingMap.get(id))
                            .ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Booking not found.")
                            );
                    break;

                case 3:
                    reservations.stream()
                            .filter(r -> r.getTotalFare() > 500)
                            .forEach(System.out::println);
                    break;

                case 4:
                    reservations.stream()
                            .sorted(Comparator.comparing(
                                    BusReservation::getPassengerName))
                            .forEach(System.out::println);
                    break;

                case 5:
                    long count = reservations.stream().count();
                    System.out.println("Total bookings: " + count);
                    break;

                case 6:
                    // Generate a destination-wise booking report
                    Map<String, Long> report = reservations.stream()
                            .collect(Collectors.groupingBy(
                                    BusReservation::getDestination,
                                    TreeMap::new,
                                    Collectors.counting()));

                    System.out.println("\nDestination-wise report:");
                    report.forEach((city, total) ->
                            System.out.println(city + ": " + total + " booking(s)"));

                    System.out.println("Unique booking IDs: " + bookingIds.size());
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
        } finally {
            sc.close();
        }
    }
}