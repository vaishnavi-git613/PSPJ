import java.util.Scanner;

class Passenger {
    private String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class BusReservation {
    private Passenger passenger;
    private String source;
    private String destination;
    private int seats;
    private int fare;

    public BusReservation(Passenger passenger,
                          String source,
                          String destination,
                          int seats,
                          int fare) {
        this.passenger = passenger;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.fare = fare;
    }

    public int calculateTotal() {
        return seats * fare;
    }

    public void displayReservation() {
        System.out.println("\n--- RESERVATION SUMMARY ---");
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("From: " + source);
        System.out.println("To: " + destination);
        System.out.println("Seats: " + seats);
        System.out.println("Fare per seat: Rs. " + fare);
        System.out.println("Total fare: Rs. " + calculateTotal());
        System.out.println("Reservation successful!");
    }
}

public class IntercityBusReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] cities = {
            "Hyderabad",
            "Vijayawada",
            "Bengaluru",
            "Chennai"
        };

        int[] fares = {500, 350, 800, 700};

        try {
            System.out.println("==============================");
            System.out.println(" INTERCITY BUS RESERVATION");
            System.out.println("==============================");

            System.out.print("Enter passenger name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                throw new IllegalArgumentException(
                    "Passenger name cannot be empty."
                );
            }

            Passenger passenger = new Passenger(name);

            System.out.println("\nAvailable Cities:");
            for (int i = 0; i < cities.length; i++) {
                System.out.println((i + 1) + ". " + cities[i]);
            }

            System.out.print("Select source city (1-4): ");
            int source = sc.nextInt();

            System.out.print("Select destination city (1-4): ");
            int destination = sc.nextInt();

            // Fixed: Added missing '||' operator after source < 1
            if (source < 1 || source > cities.length
                    || destination < 1
                    || destination > cities.length) {
                throw new IllegalArgumentException(
                    "Invalid city selection."
                );
            }

            if (source == destination) {
                throw new IllegalArgumentException(
                    "Source and destination cannot be the same."
                );
            }

            System.out.print("Enter number of seats: ");
            int seats = sc.nextInt();

            if (seats <= 0) {
                throw new IllegalArgumentException(
                    "Number of seats must be positive."
                );
            }

            int fare = fares[destination - 1];

            BusReservation reservation =
                new BusReservation(
                    passenger,
                    cities[source - 1],
                    cities[destination - 1],
                    seats,
                    fare
                );

            reservation.displayReservation();

        } catch (IllegalArgumentException e) {
            System.out.println("Booking error: " + e.getMessage());
        } catch (java.util.InputMismatchException e) {
            System.out.println(
                "Please enter valid numbers for city and seats."
            );
        } finally {
            sc.close();
        }
    }
}