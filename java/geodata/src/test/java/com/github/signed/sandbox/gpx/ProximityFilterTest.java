package com.github.signed.sandbox.gpx;

import static com.github.signed.sandbox.gpx.ProximityFilterTest.TestFiles.black_forrest_huts;
import static com.github.signed.sandbox.gpx.ProximityFilterTest.TestFiles.schluchtensteig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.WptType;

public class ProximityFilterTest {

    public static final double One_Kilometer = 1.0;
    private final GpxPersister gpxPersister = new GpxPersister();
    private final GpxDistanceCalculator gpxDistanceCalculator = new GpxDistanceCalculator();
    private final ProximityFilter proximityFilter = new ProximityFilter(gpxDistanceCalculator);


    public static class TestFiles {

        public static Path schluchtensteig = Paths.get("src/test/resources/tracks/schluchtensteig.gpx");
        public static Path black_forrest_huts = Paths.get("src/test/resources/huts/blackforsthuts.gpx");

    }

    @Test
    public void removeHutsThatAreMoreThanOneKilometerAwayFromTheTrack() throws Exception {
        GpxType track = gpxPersister.load(schluchtensteig);
        GpxType hutsGpx = gpxPersister.load(black_forrest_huts);

        List<WptType> closeByShelters = proximityFilter.wayPointsCloseTo(track, hutsGpx.getWpt(), One_Kilometer);

        hutsGpx.getWpt().clear();
        hutsGpx.getWpt().addAll(closeByShelters);

        String xml = gpxPersister.toXml(hutsGpx);
        System.out.println(xml);
    }

    public static class Lists {
        public static <T> T last(List<T> list) {
            return list.get(list.size() - 1);
        }
    }

    @Test
    public void takeTheLastPointOfEachTrackAndConvertItIntoAWayPoint() throws Exception {

        List<Path> gpxFiles = new ArrayList<>();
        gpxFiles.add(TestFiles.schluchtensteig);

        List<WptType> lastWayPointsOfEachTrack = gpxFiles.stream()
                .map(gpxPersister::load)
                .flatMap(gpxType -> gpxType.getTrk().stream())
                .map(trkType -> Lists.last(trkType.getTrkseg()))
                .map(segment -> Lists.last(segment.getTrkpt()))
                .collect(Collectors.toList());


        GpxType gpxType = new GpxType();
        gpxType.getWpt().addAll(lastWayPointsOfEachTrack);

        String xml = gpxPersister.toXml(gpxType);

        System.out.println(xml);
    }
}