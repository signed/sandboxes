package com.github.signed.sandbox.gpx;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import com.topografix.gpx._1._1.GpxType;

public class GpxPersister {

    public GpxType load(Path path) {
        try {
            Unmarshaller unmarshaller = context().createUnmarshaller();
            JAXBElement<GpxType> unmarshal = (JAXBElement<GpxType>) unmarshaller.unmarshal(path.toFile());
            return unmarshal.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toXml(GpxType hutsGpx) throws JAXBException {
        JAXBElement<GpxType> element = new JAXBElement<>(new QName("http://www.topografix.com/GPX/1/1", "gpx", ""), GpxType.class, hutsGpx);
        Marshaller marshaller = context().createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(element, out);
        return new String(out.toByteArray(), Charset.forName("UTF-8"));
    }

    private JAXBContext context() throws JAXBException {
        return JAXBContext.newInstance(GpxType.class);
    }
}
