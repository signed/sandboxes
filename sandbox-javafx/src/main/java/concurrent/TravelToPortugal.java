package concurrent;

import javafx.concurrent.Task;

import java.util.List;

public class TravelToPortugal extends Task<StringWrapper> {
    private List<Leg> journey;

    public TravelToPortugal(List<Leg> journey) {
        this.journey = journey;
    }

    @Override
    protected StringWrapper call() throws Exception {
        int legsCompleted = 0;
        int totalNumberOfLegs = journey.size();
        updateProgress(0, totalNumberOfLegs);
        Leg lastLeg = null;

        for (Leg leg : journey) {
            if(isCancelled()){
                break;
            }
            lastLeg = leg;

            updateMessage("travel to "+ leg.destination);
            leg.travel();
            updateMessage("arrived in "+ leg.destination);
            updateProgress(++legsCompleted, totalNumberOfLegs);
        }
        String destination = "we did not leave";
        if( null !=lastLeg){
            destination = lastLeg.destination;
        }
        return new StringWrapper(destination);
    }
}
