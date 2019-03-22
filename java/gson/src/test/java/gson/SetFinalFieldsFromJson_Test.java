package gson;

import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SetFinalFieldsFromJson_Test {

    private final Gson gson = new Gson();

    private static class InitializeOnFieldDeclaration {
        final String field = "initial";
    }

    @Test
    public void doNotSetFieldValueOnDeSerializationIfValueToFinalFieldIsAssignedOnFieldDeclaration() throws Exception {
        assertThat(gson.fromJson("{\"field\":\"another\"}", InitializeOnFieldDeclaration.class).field, is("initial"));
    }

    private static class InitializeWithNullOnFieldDeclaration {
        final String field = null;
    }

    @Test
    public void setFieldValueOnDeSerializationIfNullIsAssignedToFinalFieldOnFieldDeclaration() throws Exception {
        assertThat(gson.fromJson("{\"field\":\"another\"}", InitializeWithNullOnFieldDeclaration.class).field, is("another"));
    }

    private static class InitializeOnFieldInConstructor{
        final String field;

        private InitializeOnFieldInConstructor() {
            field = "initial";
        }
    }

    @Test
    public void setFieldValueOnDeSerializationIfValueToFinalFieldIsAssignedInConstructor() throws Exception {
        assertThat(gson.fromJson("{\"field\":\"another\"}", InitializeOnFieldInConstructor.class).field, is("another"));
    }


}
