import java.util.Scanner;

public class IntercityBusReservation {

    // 1D arrays: storing bus information
    static int[] busNumbers = {101, 102, 103};
    static String[] busNames = {"Express", "Super Fast", "Deluxe"};
    static String[] sources = {"Hyderabad", "Hyderabad", "Hyderabad"};
    static String[] destinations = {"Vijayawada", "Warangal", "Bangalore"};
    static double[] fares = {750.0, 450.0, 1200.0};

    // 2D array: seat status for each bus
    // 0 = available, 1 = booked
    static int[][] seats = new int[3][10];

    // Method for displaying available buses
    static void displayBuses() {
        System.out.println("\n========== AVAILABLE BUSES ==========");

        for (int i = 0; i < busNumbers.length; i++) {
            System.out.println(
                "Bus No: " + busNumbers[i]
                + " | " + busNames[i]
                + " | " + sources[i] + " -> " + destinations[i]
                + " | Fare: Rs." + fares[i]
            );
        }
    }

    // Method for searching a bus
    static int searchBus(int busNumber) {
        for (int i = 0; i < busNumbers.length; i++) {
            if (busNumbers[i] == busNumber) {
                return i;
            }
        }
        return -1;
    }

    // Method for displaying seats
    static void displaySeats(int busIndex) {
        System.out.println("\n========== SEAT STATUS ==========");

        for (int i = 0; i < seats[busIndex].length; i++) {
            if (seats[busIndex][i] == 0) {
                System.out.print("Seat " + (i + 1) + " : Available\t");
            } else {
                System.out.print("Seat " + (i + 1) + " : Booked\t");
            }

            if ((i + 1) % 2 == 0) {
                System.out.println();
            }
        }
    }

    // Method for calculating discount
    static double calculateDiscount(int age, double fare) {

        if (age >= 60) {
            return fare * 0.10;
        } else if (age >= 18) {
            return 0;
        } else {
            return fare * 0.05;
        }
    }

    // Recursive method: sum of digits
    static int sumOfDigits(int number) {

        if (number == 0) {
            return 0;
        }

        return (number % 10) + sumOfDigits(number / 10);
    }

    // Method for calculating final fare
    static double calculateFinalFare(double fare, double discount) {
        return fare - discount;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==============================================");
        System.out.println("       INTERCITY BUS RESERVATION SYSTEM");
        System.out.println("==============================================");

        // Input
        System.out.print("Enter passenger name: ");
        String passengerName = sc.nextLine();

        System.out.print("Enter passenger age: ");
        int age = sc.nextInt();

        displayBuses();

        System.out.print("\nEnter bus number: ");
        int busNumber = sc.nextInt();

        // Search operation
        int busIndex = searchBus(busNumber);

        if (busIndex == -1) {
            System.out.println("Invalid bus number.");
            sc.close();
            return;
        }

        displaySeats(busIndex);

        System.out.print("\nEnter seat number (1-10): ");
        int seatNumber = sc.nextInt();

        // Validation using logical operators
        if (seatNumber < 1 || seatNumber > 10) {
            System.out.println("Invalid seat number.");
            sc.close();
            return;
        }

        // Check seat availability
        if (seats[busIndex][seatNumber - 1] == 1) {
            System.out.println("Sorry! The selected seat is already booked.");
            sc.close();
            return;
        }

        // Book the seat
        seats[busIndex][seatNumber - 1] = 1;

        // Fare calculation
        double originalFare = fares[busIndex];
        double discount = calculateDiscount(age, originalFare);
        double finalFare = calculateFinalFare(originalFare, discount);

        // Switch statement
        String passengerCategory;

        switch (age / 10) {
            case 0:
                passengerCategory = "Child";
                break;

            case 1:
                passengerCategory = "Teenager";
                break;

            case 2:
            case 3:
            case 4:
            case 5:
                passengerCategory = "Adult";
                break;

            default:
                passengerCategory = "Senior Citizen";
        }

        // Generate booking ID
        int bookingId = busNumber * 10 + seatNumber;

        // Recursive calculation
        int digitSum = sumOfDigits(bookingId);

        // Output
        System.out.println("\n==============================================");
        System.out.println("           BOOKING CONFIRMED");
        System.out.println("==============================================");

        System.out.println("Passenger Name   : " + passengerName);
        System.out.println("Passenger Age    : " + age);
        System.out.println("Category         : " + passengerCategory);
        System.out.println("Bus Number       : " + busNumbers[busIndex]);
        System.out.println("Bus Name         : " + busNames[busIndex]);
        System.out.println("Route            : "
                + sources[busIndex] + " -> " + destinations[busIndex]);
        System.out.println("Seat Number      : " + seatNumber);
        System.out.println("Original Fare    : Rs." + originalFare);
        System.out.println("Discount         : Rs." + discount);
        System.out.println("Final Fare       : Rs." + finalFare);
        System.out.println("Booking ID       : " + bookingId);
        System.out.println("Booking ID Sum   : " + digitSum);
        System.out.println("Status           : RESERVED");

        System.out.println("==============================================");

        sc.close();
    }
}
