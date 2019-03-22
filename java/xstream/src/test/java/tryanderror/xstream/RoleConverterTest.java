package tryanderror.xstream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.github.signed.xml.xstream.converter.RoleConverter;
import com.github.signed.xml.xstream.domain.Role;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class RoleConverterTest {
    private RoleConverter converter = new RoleConverter();

    @Test
    public void onlyConvertsTheRoleEnumeration() {
        assertThat(converter.canConvert(Role.class), is(true));
    }

    @Test
    public void doesNotConvertAnyOtherClass() {
        Class<?> anyOtherType = String.class;
        assertThat(converter.canConvert(anyOtherType), is(false));
    }

    @Test
    public void serializesConstantToXmlRepresentation() throws Exception {
        MarshallingContext anyContext = null;
        HierarchicalStreamWriter writer = mock(HierarchicalStreamWriter.class);
        converter.marshal(Role.ReserveProvider, writer, anyContext);
        verify(writer).addAttribute("v", "A04");
    }

    @Test
    public void deserializesToEnumConstant() throws Exception {
        UnmarshallingContext anyContext = null;
        HierarchicalStreamReader reader = mock(HierarchicalStreamReader.class);
        when(reader.getAttribute("v")).thenReturn("A27");
        assertThat(converter.unmarshal(reader, anyContext), is((Object) Role.TransmissionSystemOperator));
    }

    @Test(expected = RuntimeException.class)
    public void forUnknownXmlRepresentation() throws Exception {
        UnmarshallingContext anyContext = null;
        HierarchicalStreamReader reader = mock(HierarchicalStreamReader.class);
        when(reader.getAttribute("v")).thenReturn("UnknowXmlRepresentation");
        converter.unmarshal(reader, anyContext);
    }
}