package ticket.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private String ticketId;
    private LocalDateTime issueDate;
    private LocalDateTime travelDate;
    private String seatNumber;
    private Double price;
    private Map<Passenger, Seat> passengersSeats;
    private String source;
    private String destination;
    private Train train;

}
