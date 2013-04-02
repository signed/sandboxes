package saucer;

import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class FirstDoc {

    public static void main(String[] args) throws IOException, DocumentException {
        URL baseLocation = FirstDoc.class.getProtectionDomain().getCodeSource().getLocation();
        URL location = new URL(baseLocation, "firstdoc.xhtml");

        String outputFile = "firstdoc.pdf";
        OutputStream os = new FileOutputStream(outputFile);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(location.toExternalForm());
        renderer.layout();
        renderer.createPDF(os);

        os.close();
    }
}