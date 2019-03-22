package com.github.signed.xml.xstream.converter;



import com.github.signed.xml.xstream.domain.Role;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class RoleConverter implements Converter {
	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Role.class.equals(type);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String attribute = reader.getAttribute("v");
		return Role.valueOfXmlRepresentation(attribute);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,	MarshallingContext context) {
		Role role = (Role) source;
		writer.addAttribute("v", role.xmlRepresentation());
	}
}