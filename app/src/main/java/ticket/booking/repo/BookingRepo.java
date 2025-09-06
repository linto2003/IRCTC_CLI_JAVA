package ticket.booking.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ticket.booking.entities.Booking;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookingRepo {
    private static final String FILE_PATH = "src/main/resources/booking.json";
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public List<Booking> loadBookings() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Booking>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveBookings(List<Booking> bookings) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
