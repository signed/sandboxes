package sample;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GenerateAsciiDoctorSourcesTest {

    @Test
    public void name() throws Exception {
        Path generatedAsciidocSource = Paths.get("build/generated/asciidoc");
        Files.createDirectories(generatedAsciidocSource);
        Path outputFile = generatedAsciidocSource.resolve("generated.adoc");

        LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("UTC"));
        String content =
                "== Include generated documentation\n" +
                "This is generated from a junit 4 test "+timestamp;
        Files.write(outputFile, content.getBytes(Charset.forName("UTF-8")));
    }
}
