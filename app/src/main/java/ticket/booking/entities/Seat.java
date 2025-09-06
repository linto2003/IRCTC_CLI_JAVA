package ticket.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    public enum SeatType {
        UPPER,
        LOWER,
        MIDDLE,
        SLOWER,
        SUPPER
    }
    private String seatNumber;
    private SeatType seatType;
    private boolean isAvailable;
    private String coachNumber;
}
