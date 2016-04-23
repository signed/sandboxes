package refactor;

import java.util.ArrayList;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class MoveMethdBreaksReference {

    private static class Destination{

    }

    private final Destination destination = new Destination();

    public void main(){
        new ArrayList<String>().stream().filter(notNull()).collect(toList());
    }

    private Predicate<String> notNull() {
        return it -> null != it;
    }

}
