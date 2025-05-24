package bucket;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static java.time.Instant.ofEpochSecond;
import static org.assertj.core.api.Assertions.assertThat;

class BucketAssemblyLineTest {

    private final Instant earliest = ofEpochSecond(24);
    private final Duration inspectionRange = ofSeconds(3);
    private final Duration samplingRate = ofSeconds(1);
    private final BucketTest.BucketAssemblyLine bucketAssemblyLine = new BucketTest.BucketAssemblyLine(samplingRate, inspectionRange, earliest);

    @Test
    void initiallyThereIsASingleBucket() {
        assertThat(allBuckets()).hasSize(1);

        assertThat(firstBucket("[24 27[").earliest()).isEqualTo(ofEpochSecond(24));
        assertThat(firstBucket("[24 27[").latest()).isEqualTo(ofEpochSecond(27));
    }

    @Test
    void secondBucketInterval() {
        putEventIntoBuckets(25);
        assertThat((secondBucket("[25 28[").earliest())).isEqualTo(ofEpochSecond(25));
        assertThat((secondBucket("[25 28[").latest())).isEqualTo(ofEpochSecond(28));
    }

    @Test
    void thirdBucketInterval() {
        putEventIntoBuckets(26);

        assertThat(thirdBucket("[26 29[").earliest()).isEqualTo(ofEpochSecond(26));
        assertThat(thirdBucket("[26 29[").latest()).isEqualTo(ofEpochSecond(29));
    }

    @Test
    void distributeToInitialBuckets() {
        BucketTest.Event event = putEventIntoBuckets(24);

        assertThat(firstBucket("[24 27[").items().getFirst()).isEqualTo(event);
    }

    @Test
    void startDroppingBuckets() {
        BucketTest.Event event = putEventIntoBuckets(27);

        assertThat(allBuckets()).hasSize(4);
        assertThat(firstBucket("[24 27[").items()).isEmpty();
        assertThat(secondBucket("[25 28[").items().getFirst()).isEqualTo(event);
        assertThat(thirdBucket("[26 29[").items().getFirst()).isEqualTo(event);
        assertThat(fourthBucket("[27 30[").items().getFirst()).isEqualTo(event);
    }

    private BucketTest.Event putEventIntoBuckets(int seconds) {
        BucketTest.Event event = new BucketTest.Event(ofEpochSecond(seconds), Integer.toString(seconds));
        bucketAssemblyLine.putIntoBucket(event);
        return event;
    }

    private BucketTest.Bucket fourthBucket(String s) {
        return allBuckets().get(3);
    }

    private BucketTest.Bucket thirdBucket(String s) {
        return allBuckets().get(2);
    }

    private BucketTest.Bucket secondBucket(String s) {
        return allBuckets().get(1);
    }

    private BucketTest.Bucket firstBucket(String s) {
        return allBuckets().get(0);
    }

    private List<BucketTest.Bucket> allBuckets() {
        return bucketAssemblyLine.allBuckets();
    }
}
