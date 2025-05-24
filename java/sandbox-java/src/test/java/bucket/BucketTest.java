package bucket;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class BucketTest {

    @Test
    void sample() {
        Instant now = Instant.now();
        Instant earlier = now.minus(20, ChronoUnit.MINUTES);
        List<Event> events = Arrays.asList(new Event(now, "now"), new Event(earlier, "earlier"));
        List<Event> sortedEvents = events.stream().sorted(byTimeStamp()).collect(Collectors.toList());
        Duration samplingRate = Duration.ofMinutes(1);
        Duration inspectionRange = Duration.ofMinutes(10);

        Instant earliest = sortedEvents.get(0).timestamp;
        Instant latest = sortedEvents.get(sortedEvents.size() - 1).timestamp;

        BucketAssemblyLine bucketAssemblyLine = new BucketAssemblyLine(samplingRate, inspectionRange, earlier);


    }

    private Comparator<Event> byTimeStamp() {
        return new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.timestamp.compareTo(o2.timestamp);
            }
        };
    }

    public static class BucketAssemblyLine {
        private final LinkedList<Bucket> allBuckets = new LinkedList<>();
        private final LinkedList<Bucket> assemblyLine = new LinkedList<>();
        private final Duration samplingRate;
        private final Duration inspectionRange;

        public BucketAssemblyLine(Duration samplingRate, Duration inspectionRange, Instant earliest) {
            this.samplingRate = samplingRate;
            this.inspectionRange = inspectionRange;

            addBucket(new Bucket(earliest, earliest.plus(inspectionRange)));
        }

        public void putIntoBucket(Event event) {
            assemblyLine.removeIf(bucket -> !event.timestamp.isBefore(bucket.latest));

            Instant nextNewBucketStart = allBuckets.peekLast().earliest().plus(samplingRate);
            if (wouldAlreadyBeAddedToTheNextBucket(event, nextNewBucketStart)) {
                for (; wouldAlreadyBeAddedToTheNextBucket(event, nextNewBucketStart); nextNewBucketStart = nextNewBucketStart.plus(samplingRate)) {
                    Bucket newBucket = new Bucket(nextNewBucketStart, nextNewBucketStart.plus(inspectionRange));
                    addBucket(newBucket);
                }
            }
            Boolean addedToAtLeastOneBucket = assemblyLine.stream().reduce(Boolean.FALSE, (aBoolean, aBucket) -> aBucket.put(event), (a, b) -> a || b);

            if (!addedToAtLeastOneBucket) {
                throw new IllegalStateException("There was no bucket to take the event");
            }
        }

        private boolean wouldAlreadyBeAddedToTheNextBucket(Event event, Instant nextNewBucketStart) {
            return !nextNewBucketStart.isAfter(event.timestamp);
        }

        public List<Bucket> allBuckets() {
            return allBuckets;
        }

        private void addBucket(Bucket bucket) {
            allBuckets.add(bucket);
            assemblyLine.add(bucket);
        }

    }

    public static class Bucket {
        private final List<Event> events = new LinkedList<>();
        private final Instant earliest;
        private final Instant latest;

        public Bucket(Instant earliest, Instant latest) {
            this.latest = latest;
            this.earliest = earliest;
        }

        public Instant earliest() {
            return earliest;
        }

        public Instant latest() {
            return latest;
        }

        public boolean put(Event event) {
            if (!event.timestamp.isBefore(latest)) {
                return false;
            }
            if (earliest().isAfter(event.timestamp)) {
                return false;
            }
            return this.events.add(event);
        }

        public List<Event> items() {
            return events;
        }
    }

    public static class Event {
        public final Instant timestamp;
        public final String data;

        public Event(Instant timestamp, String data) {
            this.timestamp = timestamp;
            this.data = data;
        }
    }
}
