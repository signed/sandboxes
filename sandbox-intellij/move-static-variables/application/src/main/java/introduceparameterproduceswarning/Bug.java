package introduceparameterproduceswarning;

import com.google.common.collect.Lists;

import java.util.List;

public class Bug {


    private final List<Generic<?>> elements;

    public Bug() {
        elements = Lists.newArrayList(new StringGeneric(), new IntegerGeneric());
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
