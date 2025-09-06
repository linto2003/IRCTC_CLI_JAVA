package ticket.booking.repo;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainRepo {
    private final String filePath = "D:\\JAVA\\TicketBooking\\app\\src\\main\\java\\ticket\\localDb\\trains.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Train> loadTrains() {

        try{
            File file = new File(filePath);
            if(!file.exists()) {
                System.out.println("Train file does not exist");
                return new ArrayList<>();
            }
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(file, new TypeReference<List<Train>>(){});

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public void saveTrains(List<Train> trains) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), trains);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


