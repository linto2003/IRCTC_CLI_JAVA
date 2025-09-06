package ticket.booking.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private String stationName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}
