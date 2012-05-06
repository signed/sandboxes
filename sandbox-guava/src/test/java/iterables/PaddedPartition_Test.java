package iterables;

import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class PaddedPartition_Test {

    @Test
    public void doNotCreateAPartitionOnEmptyInput() throws Exception {
        Iterable<String> data = Collections.emptyList();
        Iterable<List<String>> lists = Iterables.paddedPartition(data, 3);
        assertThat(Iterables.size(lists), is(0) );
    }

    @Test
    public void padTheFirstPartitionToSpecifiedSize() throws Exception {
        Iterable<String> data = newArrayList("one");
        Iterable<List<String>> lists = Iterables.paddedPartition(data, 3);
        List<String> firstPartition = lists.iterator().next();
        assertThat(firstPartition, hasSize(3));
    }
}
