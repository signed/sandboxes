package tryanderror.xstream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasXPath;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.github.signed.xml.xstream.converter.RoleConverter;
import com.github.signed.xml.xstream.converter.XStreamExtension;
import com.github.signed.xml.xstream.domain.AcknowledgementDocument;
import com.github.signed.xml.xstream.domain.DateTimeConverter;
import com.github.signed.xml.xstream.domain.EicCodeConverter;
import com.github.signed.xml.xstream.domain.Identification;
import com.thoughtworks.xstream.XStream;

public class Xstream_Test {
    private DateTime documentDateTime = new DateTime(2005, 10, 3, 11, 14, 43, 47, DateTimeZone.UTC);
    private AcknowledgementDocument ack = new AcknowledgementDocument(documentDateTime);

    @Test
    public void serializesDocumentRoot() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument"));
    }

    @Test
    public void serializesDocumentDateTime() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/DocumentDateTime"));
    }

    @Test
    public void serializesDocumentDateTimeAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/DocumentDateTime/@v", is("2005-10-03T11:14:43Z")));
    }

    @Test
    public void serializesSenderIdentification() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/SenderIdentification"));
    }

    @Test
    public void serializesSendersEiCodeAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/SenderIdentification/@v", is("10XRECEIVER-OFD8")));
    }

    @Test
    public void serializesSendersCodeSchemeAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/SenderIdentification/@codeScheme", is("A01")));
    }

    @Test
    public void serializesSendersRole() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/SenderRole"));
    }

    @Test
    public void serializesSendersRoleAsAttribte() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/SenderRole/@v"));
    }

    @Test
    public void serializesReceiverRole() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/ReceiverRole"));
    }

    @Test
    public void serializesReceiverRoleAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/ReceiverRole/@v", is("A27")));
    }

    @Test
    public void serializesReciverIdentification() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/ReceiverIdentification"));
    }

    @Test
    public void serializesReceiverEiCodeAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/ReceiverIdentification/@v", is("10XSENDER-OF-DO5")));
    }

    @Test
    public void serializesReceiverCodeSchemeAsAttribute() throws Exception {
        assertThat(theSerialized(ack), hasXPath("/AcknowledgementDocument/ReceiverIdentification/@codeScheme", is("A01")));
    }

    @Test
    public void testName() throws Exception {
        String xml = xstream().toXML(new ToSerialize(new AClass()));
        System.out.println(xml);
    }

    public static interface AInterface {

    }

    public static class AClass implements AInterface {
        public String fixValue = "bekommt er das";
    }

    public static class ToSerialize {
        public AInterface hmm;

        public ToSerialize(AInterface jump) {
            this.hmm = jump;
        }
    }

    public Node theSerialized(Object ack) throws Exception {
        String xml = xstream().toXML(ack);
        return the(xml);
    }

    private XStream xstream() {
        XStreamExtension xStream = new XStreamExtension();
        xStream.alias("AcknowledgementDocument", AcknowledgementDocument.class);
        xStream.registerConverter(new EicCodeConverter());

        xStream.registerLocalConverter(AcknowledgementDocument.class, "DocumentDateTime", new DateTimeConverter("v"));
        xStream.useAttributeFor(Identification.class, "code");
        xStream.aliasAttribute("v", "code");

        xStream.useAttributeFor(Identification.class, "codeScheme");

        xStream.registerConverter(new RoleConverter());

        return xStream;
    }

    public static Node the(String xml) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(xml));
        Document document = db.parse(inputSource);
        return document.getDocumentElement();
    }
}