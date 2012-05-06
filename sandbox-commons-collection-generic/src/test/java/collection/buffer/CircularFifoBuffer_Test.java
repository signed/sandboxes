package collection.buffer;

import org.apache.commons.collections15.buffer.CircularFifoBuffer;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class CircularFifoBuffer_Test {

    @Test
    public void doNotRemoveItemsOnIterate() throws Exception {
        CircularFifoBuffer<String> buffer = new CircularFifoBuffer<String>(3);
        buffer.add("first");
        Iterator<String> iterator = buffer.iterator();
        iterator.next();
        assertThat(buffer, hasSize(1));
    }
}
