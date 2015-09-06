package com.github.signed.sandbox.gpx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.junit.Test;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.WptType;

public class ProximityFilterTest {

    @Test
    public void testName() throws Exception {
        File hutsFile = new File("src/test/resources/huts/blackforsthuts.gpx");
        File trackFile = new File("src/test/resources/tracks/schluchtensteig.gpx");
        GpxType track = load(trackFile);
        GpxType hutsGpx = load(hutsFile);

        List<WptType> shelter = new ArrayList<>();
        for (WptType gpxWayPoint : hutsGpx.getWpt()) {
            if (isCloseTo(track, gpxWayPoint)) {
                shelter.add(gpxWayPoint);
            }
        }

        hutsGpx.getWpt().clear();
        hutsGpx.getWpt().addAll(shelter);

        String xml = toXml(hutsGpx);
        System.out.println(xml);
    }

    private boolean isCloseTo(GpxType track, WptType gpxWayPoint) {
        for (WptType waypointOnTrack : track.getTrk().get(0).getTrkseg().get(0).getTrkpt()) {
            double distance = distance(gpxWayPoint.getLat().doubleValue(), gpxWayPoint.getLon().doubleValue(), waypointOnTrack.getLat().doubleValue(), waypointOnTrack.getLon().doubleValue());
            if (distance <= 1) {
                System.out.println(distance);
                return true;
            }
        }
        return false;
    }

    private GpxType load(File file) throws Exception {

        Unmarshaller unmarshaller = context().createUnmarshaller();
        JAXBElement<GpxType> unmarshal = (JAXBElement<GpxType>) unmarshaller.unmarshal(file);
        com.topografix.gpx._1._1.GpxType fosterHome = unmarshal.getValue();

        return fosterHome;
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
        return JAXBContext.newInstance(com.topografix.gpx._1._1.GpxType.class);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist * 1.609344;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}