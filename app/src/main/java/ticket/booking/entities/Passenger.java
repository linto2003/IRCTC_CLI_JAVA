package ticket.booking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    public enum Privilege {
        NORMAL,VIP,SENIOR_CITIZEN,STUDENT
    }

    private String passengerId;
    private String name;
    private int age;
    private Privilege privilege;
}
