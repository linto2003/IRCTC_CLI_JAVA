package ticket.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    public enum TrainType {
        EXPRESS,PASSENGER,SUPERFAST
    }

    private String trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private List<Schedule> schedule;
    private List<Seat> seats;
    private TrainType trainType;
    private String trainNumber;
}

