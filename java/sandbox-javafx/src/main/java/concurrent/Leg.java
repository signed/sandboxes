package concurrent;

public class Leg {
    public final String destination;

    Leg(String destination) {
        this.destination = destination;
    }


    public void travel(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            //nothing to do ...
        }
    }
}
