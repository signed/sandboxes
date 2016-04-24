package refactor;

import reference.Youtrack;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@Youtrack("https://youtrack.jetbrains.com/issue/IDEA-155147")
public class MoveMethdBreaksReference {

    private static class Destination{

    }

    private final Destination destination = new Destination();

    public void main(){
        new ArrayList<String>().stream().filter(this::notNull).collect(toList());
    }

    private boolean notNull(String it) {
        return it != null;
    }

}
