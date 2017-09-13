package sample;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class Junit5GeneratesAsciiDoctorSourceTest {

    @Test
    @Tag("documentation")
    void generateDocumentation() throws Exception {
        Path generatedAsciidocSource = Paths.get("build/generated/asciidoc");
        Files.createDirectories(generatedAsciidocSource);
        Path outputFile = generatedAsciidocSource.resolve("junit5.adoc");

        LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("UTC"));
        String content =
                "== Include generated documentation\n" +
                        "This is generated from a junit 5 test "+timestamp;
        Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")));
    }

    @Test
    void thisShouldNotBeExecutedDuringDocumentationPhase() throws Exception {
        assertThat(true, equalTo(true));
    }
}
