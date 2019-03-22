package dozer;

import beans.ClassWithCollections;
import org.dozer.DozerBeanMapper;
import testsuite.EmptyCollections_Test;

public class EmptyCollectionsDozer_Test extends EmptyCollections_Test {

    @Override
    public ClassWithCollections emptyCollectionsWhileMapping(ClassWithCollections classWithCollections) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setCustomFieldMapper(new DoNotMapContentOfCollection());

        return mapper.map(classWithCollections, ClassWithCollections.class);
    }
}
