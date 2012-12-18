package guice;

public class Mail implements Deliverable {

    @Override
    public String name() {
        return "Mail";
    }
}
