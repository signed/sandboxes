package maps

import org.hamcrest.CoreMatchers
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

class AddElementsToMap {

    @Test
    public void addElementToEmptyMap(){
        def emptyMap = [:]
        emptyMap << ['key': 'value']

        assertThat(emptyMap.key, CoreMatchers.is('value'))
    }

    @Test
    public void overrideExistingEntries(){
        def mapWithOneElement = ['key':'initial']
        mapWithOneElement << ['key': 'replacement']

        assertThat(mapWithOneElement.key, CoreMatchers.is('replacement'))
    }

    @Test
    public void addElementWithNewKey(){
        def mapWithOneElement = ['key':'initial']
        mapWithOneElement << ['anotherKey': 'newValue']

        assertThat(mapWithOneElement.anotherKey, CoreMatchers.is('newValue'))
    }
}
