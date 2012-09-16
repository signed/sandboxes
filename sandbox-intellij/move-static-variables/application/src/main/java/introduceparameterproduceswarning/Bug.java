package introduceparameterproduceswarning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bug {


    private final List<Generic<?>> elements;

    public Bug() {
        elements = newArrayList(new StringGeneric(), new IntegerGeneric());
    }

    public void doStuff() {
        elements.size();
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<E>();
        Collections.addAll(list, elements);
        return list;
    }

    private static class Doom{

    }

    private static class JudgmentDay{

    }

    private static interface Generic<T>{

    }

    private static class StringGeneric implements Generic<Doom> {
    }

    private static class IntegerGeneric implements Generic<JudgmentDay> {
    }
}
