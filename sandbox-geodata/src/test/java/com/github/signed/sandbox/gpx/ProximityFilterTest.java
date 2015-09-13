package com.github.signed.sandbox.gpx;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.WptType;

public class ProximityFilterTest {

    public static final double One_Kilometer = 1.0;
    private final GpxPersister gpxPersister = new GpxPersister();
    private final GpxDistanceCalculator gpxDistanceCalculator = new GpxDistanceCalculator();
    private final ProximityFilter proximityFilter = new ProximityFilter(gpxDistanceCalculator);

    @Test
    public void removeHutsThatAreMoreThanOneKilometerAwayFromTheTrack() throws Exception {
        GpxType track = gpxPersister.load(new File("src/test/resources/tracks/schluchtensteig.gpx"));
        GpxType hutsGpx = gpxPersister.load(new File("src/test/resources/huts/blackforsthuts.gpx"));

        List<WptType> closeByShelters = proximityFilter.wayPointsCloseTo(track, hutsGpx.getWpt(), One_Kilometer);

        hutsGpx.getWpt().clear();
        hutsGpx.getWpt().addAll(closeByShelters);

        String xml = gpxPersister.toXml(hutsGpx);
        System.out.println(xml);
    }


}