package bucket;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static java.time.Instant.ofEpochSecond;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

public class BucketAssemblyLineTest {

    private final Instant earliest = ofEpochSecond(24);
    private final Duration inspectionRange = ofSeconds(3);
    private final Duration samplingRate = ofSeconds(1);
    private final BucketTest.BucketAssemblyLine bucketAssemblyLine = new BucketTest.BucketAssemblyLine(samplingRate, inspectionRange, earliest);

    @Test
    public void initializeBucketsProperly() throws Exception {
        assertThat(allBuckets(), hasSize(3));

        assertThat(firstBucket("[24 27[").earliest(), equalTo(ofEpochSecond(24)));
        assertThat(firstBucket("[24 27[").latest(), equalTo(ofEpochSecond(27)));

        assertThat(secondBucket("[25 28[").earliest(), equalTo(ofEpochSecond(25)));
        assertThat(secondBucket("[25 28[").latest(), equalTo(ofEpochSecond(28)));

        assertThat(thirdBucket("[26 29[").earliest(), equalTo(ofEpochSecond(26)));
        assertThat(thirdBucket("[26 29[").latest(), equalTo(ofEpochSecond(29)));
    }

    @Test
    public void distributeToInitialBuckets() throws Exception {
        BucketTest.Event event = new BucketTest.Event(earliest, "24");
        bucketAssemblyLine.putIntoBucket(event);

        assertThat(firstBucket("[24 27[").items().get(0), equalTo(event));
        assertThat(secondBucket("[25 28[").items(), empty());
        assertThat(thirdBucket("[26 29[").items(), empty());
    }

    @Test
    public void startDroppingBuckets() throws Exception {
        BucketTest.Event event = new BucketTest.Event(ofEpochSecond(27), "27");
        bucketAssemblyLine.putIntoBucket(event);

        assertThat(allBuckets(), hasSize(4));
        assertThat(firstBucket("[24 27[").items(), empty());
        assertThat(secondBucket("[25 28[").items().get(0), equalTo(event));
        assertThat(thirdBucket("[26 29[").items().get(0), equalTo(event));
        assertThat(fourthBucket("[27 30[").items().get(0), equalTo(event));
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
