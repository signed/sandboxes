package com.github.signed.sandbox.gpx;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import ca.carleton.gcrc.gpx.Gpx;
import ca.carleton.gcrc.gpx.GpxPoint;
import ca.carleton.gcrc.gpx.GpxWayPoint;
import ca.carleton.gcrc.gpx._11.Gpx11;

public class ProximityFilterTest {

    @Test
    public void testName() throws Exception {
        File hutsFile = new File("src/test/resources/huts/blackforsthuts.gpx");
        File trackFile = new File("src/test/resources/tracks/schluchtensteig.gpx");
        Gpx track = load(trackFile);
        Gpx hutsGpx = load(hutsFile);

        for (GpxWayPoint gpxWayPoint : hutsGpx.getWayPoints()) {
            if (isCloseTo(track, gpxWayPoint)) {

                System.out.println(gpxWayPoint.getName());
            }
        }
    }

    private boolean isCloseTo(Gpx track, GpxWayPoint gpxWayPoint) {
        for (GpxPoint waypointOnTrack : track.getTracks().get(0).getSegments().get(0).getPoints()) {
            double distance = distance(gpxWayPoint.getLat().doubleValue(), gpxWayPoint.getLong().doubleValue(), waypointOnTrack.getLat().doubleValue(), waypointOnTrack.getLong().doubleValue());
            if (distance < 1) {
                System.out.println(distance);
                return true;
            }
        }
        return false;
    }

    private Gpx load(File file) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(com.topografix.gpx._1._1.GpxType.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        com.topografix.gpx._1._1.GpxType fosterHome = ((JAXBElement<com.topografix.gpx._1._1.GpxType>) unmarshaller.unmarshal(file)).getValue();

        return new Gpx11(fosterHome);
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