package ticket.booking;

import ticket.booking.entities.Booking;
import ticket.booking.entities.Train;
import ticket.booking.services.TrainServices;
import ticket.booking.services.UserBookingService;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TrainServices trainServices = new TrainServices();
        UserBookingService bookingService = new UserBookingService();

        System.out.println("=== Train Ticket Booking System ===");
        String userId = "user123"; // For now assume 1 static user, can extend later with login.

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Search Trains");
            System.out.println("2. Book a Seat");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View My Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    searchTrains(trainServices);
                    break;
                case 2:
                    bookSeat(trainServices, bookingService, userId);
                    break;
                case 3:
                    cancelBooking(bookingService, userId);
                    break;
                case 4:
                    viewBookings(bookingService, userId);
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        }
    }

    private static void searchTrains(TrainServices trainServices) {
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();

        List<Train> trains = trainServices.searchTrains(source, destination);
        if (trains.isEmpty()) {
            System.out.println("No trains found!");
        } else {
            System.out.println("Available Trains:");
            for (Train t : trains) {
                System.out.printf("TrainId: %s | Name: %s | %s -> %s | Departure: %s%n",
                        t.getTrainId(), t.getTrainName(), t.getSource(),
                        t.getDestination(), t.getDepartureTime());
            }
        }
    }

    private static void bookSeat(TrainServices trainServices, UserBookingService bookingService, String userId) {
        System.out.print("Enter TrainId: ");
        String trainId = scanner.nextLine();
        System.out.print("Enter Seat Number: ");
        String seatNumber = scanner.nextLine();

        Booking booking = bookingService.bookSeat(trainId, seatNumber, userId);
        if (booking != null) {
            System.out.println("Booking successful! BookingId: " + booking.getBookingId());
        } else {
            System.out.println("Booking failed!");
        }
    }

    private static void cancelBooking(UserBookingService bookingService, String userId) {
        System.out.print("Enter BookingId to cancel: ");
        String bookingId = scanner.nextLine();

        boolean success = bookingService.cancelBooking(bookingId, userId);
        if (success) {
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }

    private static void viewBookings(UserBookingService bookingService, String userId) {
        List<Booking> userBookings = bookingService.getUserBookings(userId);
        if (userBookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("Your Bookings:");
            for (Booking b : userBookings) {
                System.out.printf("BookingId: %s | TrainId: %s | Seat: %s | Status: %s%n",
                        b.getBookingId(), b.getTrainId(), b.getSeatNumber(), b.getStatus());
            }
        }
    }
}
