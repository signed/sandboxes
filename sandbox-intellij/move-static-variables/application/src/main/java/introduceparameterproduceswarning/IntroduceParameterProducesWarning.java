package introduceparameterproduceswarning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntroduceParameterProducesWarning {
    private final List<Generic<?>> elements;

    public IntroduceParameterProducesWarning() {
        elements = newArrayList(new SomeGeneric(), new SomeOtherGeneric());
    }

    public void doStuff() {
        elements.size();
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<E>();
        Collections.addAll(list, elements);
        return list;
    }

    private static class SomeClass {

    }

    private static class SomeOtherClass {

    }

    private static interface Generic<T>{

    }

    private static class SomeGeneric implements Generic<SomeClass> {
    }

    private static class SomeOtherGeneric implements Generic<SomeOtherClass> {
    }
}
