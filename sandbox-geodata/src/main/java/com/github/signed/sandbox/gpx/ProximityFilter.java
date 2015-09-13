package com.github.signed.sandbox.gpx;

import java.util.List;
import java.util.stream.Collectors;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.WptType;

public class ProximityFilter {
    private final GpxDistanceCalculator gpxDistanceCalculator;

    public ProximityFilter(GpxDistanceCalculator gpxDistanceCalculator) {
        this.gpxDistanceCalculator = gpxDistanceCalculator;
    }

    public List<WptType> wayPointsCloseTo(GpxType track, List<WptType> points, double acceptableDistance) {
        return points.stream().filter(hut -> isCloseTo(track, hut, acceptableDistance)).collect(Collectors.toList());
    }

    private boolean isCloseTo(GpxType track, WptType gpxWayPoint, double acceptableDistance) {
        for (WptType wayPointOnTrack : track.getTrk().get(0).getTrkseg().get(0).getTrkpt()) {
            double distance = gpxDistanceCalculator.distanceInKilometers(gpxWayPoint.getLat().doubleValue(), gpxWayPoint.getLon().doubleValue(), wayPointOnTrack.getLat().doubleValue(), wayPointOnTrack.getLon().doubleValue());
            if (distance <= acceptableDistance) {
                return true;
            }
        }
        return false;
    }
}
