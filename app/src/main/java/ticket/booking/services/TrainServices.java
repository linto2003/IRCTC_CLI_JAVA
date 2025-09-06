package ticket.booking.services;

import lombok.Getter;
import ticket.booking.entities.Schedule;
import ticket.booking.entities.Seat;
import ticket.booking.entities.Train;
import ticket.booking.repo.TrainRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Train service must include the following :
// admin functions : Add a Train , Update train schedule or update a train , delete a train ,



public class TrainServices {
    private final TrainRepo trainRepo =  new TrainRepo();
    @Getter
    List<Train> trains;

    public TrainServices(){
        trains = trainRepo.loadTrains();
    }
    //USER FUNCTIONS
    public Train getTrain(String id){
        return trains.stream().filter(t ->
             t.getTrainId().equals(id)
        ).findFirst().orElse(null);
    }

    public List<Train> searchTrains(String sourceLocation, String destinationLocation){
        return trains.stream().filter(t->t.getSource().equals(sourceLocation) && t.getDestination().equals(destinationLocation)).collect(Collectors.toList());
    }

    public List<Train> searchTrains(String trainNumber){
        return trains.stream().filter(t->t.getTrainNumber().equals(trainNumber)).collect(Collectors.toList());
    }

    //ADMIN FUNCTIONS
    public boolean addTrain(Train train){
        trains.add(train);
        try{
        trainRepo.saveTrains(trains);
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }


    public boolean deleteTrain(String id){
        try{
            trains.removeIf(t -> t.getTrainId().equals(id));
            trainRepo.saveTrains(trains);
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateTrain(Train train){
        try{
            trains.removeIf(t -> t.getTrainId().equals(train.getTrainId()));
            trains.add(train);
            trainRepo.saveTrains(trains);
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }


    //USER BOOKING HELPER
    public boolean updateSeat(String TrainId , Seat BookedSeat, boolean action){
        try{
            Train train = trains.stream().filter(t->t.getTrainId()
                    .equals(TrainId))
                    .findFirst().orElse(null);
            if(train == null){
                return false;
            }
            List<Seat>seats = train.getSeats();
            Seat seat = seats.stream().filter(s->s.getSeatNumber()
                    .equals(BookedSeat.getSeatNumber()))
                    .findFirst().orElse(null);
            if(seat == null){
                return false;
            }
            if (action) {
                if(seat.isAvailable()){
                seat.setAvailable(false);
                }
            }
            else {
                seat.setAvailable(true);
            }

            trainRepo.saveTrains(trains);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    // GET TRAIN Available
    public boolean isTrainAvailable(Train train, String station, LocalDateTime date){

        List<Schedule> schedule = train.getSchedule();

        if(schedule == null){
            return false;
        }
        else {
            for (Schedule s: schedule){
                if(s.getStationName().equals(station) && s.getArrivalTime().isAfter(date)){
                    return false;
                } else return s.getStationName().equals(station) && s.getArrivalTime().isBefore(date);
            }
        }
        return false;
    }




}
