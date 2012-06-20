package apackage;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.ValueConverter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionParser_Test {


    private final OptionParser optionParser = new OptionParser();


    @Test
    public void extractSpecifiedOptions() throws Exception {
        optionParser.accepts("a");
        optionParser.accepts("b");


        assertThat(parsedOptions("-a").has("a"), is(true));
    }

    @Test
    public void requiresSpecifiedOptions() throws Exception {
        optionParser.accepts("a").withRequiredArg();
        assertThat((String) parsedOptions("-a", "doombuggy").valueOf("a"), is("doombuggy"));
    }

    @Test
    public void convertsToTyperequiresSpecifiedOptions() throws Exception {
        ValueConverter<File> converter  = new ValueConverter<File>() {
            @Override
            public File convert(String value) {
                return new File(value);
            }

            @Override
            public Class<File> valueType() {
                return File.class;
            }

            @Override
            public String valuePattern() {
                return "/some/file\\.txt";
            }
        };
        OptionSpec<File> pathToSecretFile = optionParser.accepts("a").withRequiredArg().withValuesConvertedBy(converter);
        assertThat(parsedOptions("-a", "doombuggy").valueOf(pathToSecretFile), is(new File("doombuggy")));
    }

    @Test
    public void defaultsEvenIfOptionIsNotPassed() throws Exception {
        optionParser.accepts("a").withOptionalArg().defaultsTo("banane").required();
        assertThat((String)parsedOptions("-a").valueOf("a"), is("banane"));
    }

    private OptionSet parsedOptions(String ... passedByTheUser) throws IOException {
        optionParser.printHelpOn(System.out);
        return optionParser.parse(passedByTheUser);
    }
}
