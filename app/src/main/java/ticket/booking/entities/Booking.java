package ticket.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    public enum Status {
        BOOKED, CANCELLED
    }

    private String bookingId;
    private String trainId;
    private String userId;
    private String seatNumber;
    private LocalDateTime bookingDate;
    private Status status;
}
