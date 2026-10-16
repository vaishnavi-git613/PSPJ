import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.stream.Collectors;

// CO4: Class, object, constructor and encapsulation
class BusReservation {
    private int bookingId;
    private String passengerName;
    private String source;
    private String destination;
    private int seats;
    private int fare;

    public BusReservation(int id, String name, String source,
                           String destination, int seats, int fare) {
        this.bookingId = id;
        this.passengerName = name;
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

    public String getSource() {
        return source;
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

    public String toFileLine() {
        return bookingId + "," + passengerName + "," + source + ","
                + destination + "," + seats + "," + fare;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId
                + " | Passenger: " + passengerName
                + " | From: " + source
                + " | To: " + destination
                + " | Seats: " + seats
                + " | Total Fare: Rs. " + getTotalFare();
    }
}

public class IntercityBusReservationSystem {

    // CO1: Arrays and variables
    static String[] cities = {
        "Hyderabad", "Vijayawada", "Bengaluru", "Chennai"
    };

    static int[] fares = {500, 350, 800, 700};

    // CO6: Collections and generics
    static List<BusReservation> reservations = new ArrayList<>();
    static Set<Integer> bookingIds = new HashSet<>();

    static Path file = Paths.get("reservations.txt");
    static Scanner sc = new Scanner(System.in);

    // CO1: Method and input validation
    static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // CO5: File reading and exception handling
    static void loadReservations() {
        if (!Files.exists(file)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

            for (String line : lines) {
                try {
                    String[] data = line.split(",", -1);

                    if (data.length != 6) {
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String source = data[2];
                    String destination = data[3];
                    int seats = Integer.parseInt(data[4]);
                    int fare = Integer.parseInt(data[5]);

                    if (name.isEmpty() || seats <= 0 || fare <= 0 || id <= 0 ||
                        bookingIds.contains(id) ||
                        !Arrays.asList(cities).contains(source) ||
                        !Arrays.asList(cities).contains(destination) ||
                        source.equals(destination) ||
                        data[1].contains(",")) {
                        continue;
                    }

                    reservations.add(new BusReservation(
                        id, name, source, destination, seats, fare
                    ));
                    bookingIds.add(id);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping a malformed record.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read reservation file.");
        }
    }

    // CO5: File writing and persistence
    static void saveReservations() {
        List<String> lines = reservations.stream()
                .map(BusReservation::toFileLine)
                .collect(Collectors.toList());

        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
            System.out.println("Reservations saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save reservations.");
        }
    }

    // CO1 and CO4: Conditions, loops, objects and methods
    static void bookTicket() {
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Enter a valid name without commas.");
            return;
        }

        System.out.println("\nAvailable Cities:");
        for (int i = 0; i < cities.length; i++) {
            System.out.println((i + 1) + ". " + cities[i]);
        }

        int sourceChoice = readInt("Select source city (1-4): ");
        int destinationChoice = readInt("Select destination city (1-4): ");

        if (sourceChoice < 1 || sourceChoice > cities.length ||
            destinationChoice < 1 || destinationChoice > cities.length) {
            System.out.println("Invalid city selection.");
            return;
        }

        if (sourceChoice == destinationChoice) {
            System.out.println("Cities must be different.");
            return;
        }

        int seats = readInt("Enter number of seats: ");

        if (seats <= 0) {
            System.out.println("Seats must be greater than zero.");
            return;
        }

        String source = cities[sourceChoice - 1];
        String destination = cities[destinationChoice - 1];
        int fare = fares[destinationChoice - 1];

        int id = 101;
        while (bookingIds.contains(id)) {
            id++;
        }

        BusReservation reservation = new BusReservation(
            id, name, source, destination, seats, fare
        );

        reservations.add(reservation);
        bookingIds.add(id);

        System.out.println("\n--- BOOKING CONFIRMED ---");
        System.out.println(reservation);
        saveReservations();
    }

    // CO6: forEach and collection processing
    static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        reservations.forEach(System.out::println);
    }

    // CO5 and CO6: String comparison and Optional
    static void searchReservation() {
        System.out.print("Enter passenger name to search: ");
        String searchName = sc.nextLine().trim();

        Optional<BusReservation> result = reservations.stream()
                .filter(r -> r.getPassengerName().equalsIgnoreCase(searchName))
                .findFirst();

        result.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Reservation not found.")
        );
    }

    // CO6: Lambda expressions and sorting
    static void sortReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations to display.");
            return;
        }

        reservations.stream()
                .sorted(Comparator.comparing(
                    BusReservation::getPassengerName,
                    String.CASE_INSENSITIVE_ORDER))
                .forEach(System.out::println);
    }

    // CO6: Streams, groupingBy and counting
    static void showReport() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations for the report.");
            return;
        }

        Map<String, Long> report = reservations.stream()
                .collect(Collectors.groupingBy(
                    BusReservation::getDestination,
                    TreeMap::new,
                    Collectors.counting()
                ));

        System.out.println("\n--- DESTINATION-WISE REPORT ---");
        report.forEach((city, count) ->
            System.out.println(city + ": " + count + " booking(s)")
        );

        int totalSeats = reservations.stream()
                .mapToInt(BusReservation::getSeats)
                .sum();

        int totalRevenue = reservations.stream()
                .mapToInt(BusReservation::getTotalFare)
                .sum();

        System.out.println("Total bookings: " + reservations.size());
        System.out.println("Total seats booked: " + totalSeats);
        System.out.println("Total revenue: Rs. " + totalRevenue);
    }

    public static void main(String[] args) {

        // CO5: Load previously saved data
        loadReservations();

        // CO1: Loop and switch menu
        while (true) {
            System.out.println("\n================================");
            System.out.println(" INTERCITY BUS RESERVATION SYSTEM");
            System.out.println("================================");
            System.out.println("1. Book a ticket");
            System.out.println("2. View all reservations");
            System.out.println("3. Search by passenger name");
            System.out.println("4. Sort reservations by name");
            System.out.println("5. Booking report");
            System.out.println("6. Save reservations");
            System.out.println("0. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    searchReservation();
                    break;
                case 4:
                    sortReservations();
                    break;
                case 5:
                    showReport();
                    break;
                case 6:
                    saveReservations();
                    break;
                case 0:
                    saveReservations();
                    System.out.println("Thank you for using our system!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}