package ticket.booking.services;

import lombok.Getter;
import ticket.booking.entities.Booking;
import ticket.booking.entities.Seat;
import ticket.booking.entities.Train;
import ticket.booking.repo.BookingRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserBookingService {

    private final BookingRepo bookingRepo = new BookingRepo();
    private final TrainServices trainServices = new TrainServices();

    @Getter
    private List<Booking> bookings;

    public UserBookingService() {
        bookings = bookingRepo.loadBookings();
    }

    // BOOK A SEAT
    public Booking bookSeat(String trainId, String userId) {
        Train train = trainServices.getTrain(trainId);
        if (train == null) {
            System.out.println("Train not found!");
            return null;
        }


        Seat seat = train.getSeats().stream().filter(s->s.isAvailable()).findFirst().orElse(null);
           if (seat == null) {
               System.out.println("Seat not found!");
               return null;
           }


        // Mark seat as booked
        seat.setAvailable(false);
        trainServices.updateSeat(trainId, seat, true);
        System.out.println("Seat booked!"+seat.getSeatNumber());
        // Create booking
        Booking booking = new Booking(
                UUID.randomUUID().toString(),
                trainId,
                userId,
                seat.getSeatNumber(),
                LocalDateTime.now(),
                Booking.Status.BOOKED
        );

        bookings.add(booking);
        bookingRepo.saveBookings(bookings);
        return booking;
    }

    // CANCEL BOOKING
    public boolean cancelBooking(String bookingId, String userId) {
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId) && b.getUserId().equals(userId))
                .findFirst().orElse(null);

        if (booking == null || booking.getStatus() == Booking.Status.CANCELLED) {
            System.out.println("Booking not found or already cancelled!");
            return false;
        }

        // Free the seat
        Train train = trainServices.getTrain(booking.getTrainId());
        if (train != null) {
            Seat seat = train.getSeats().stream()
                    .filter(s -> s.getSeatNumber().equals(booking.getSeatNumber()))
                    .findFirst().orElse(null);

            if (seat != null) {
                seat.setAvailable(true);
                trainServices.updateSeat(train.getTrainId(), seat, false);
            }
        }

        booking.setStatus(Booking.Status.CANCELLED);
        bookingRepo.saveBookings(bookings);
        return true;
    }

    // GET BOOKINGS OF A USER
    public List<Booking> getUserBookings(String userId) {
        return bookings.stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // GET BOOKING DETAILS
    public Booking getBookingDetails(String bookingId) {
        return bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst().orElse(null);
    }
}
