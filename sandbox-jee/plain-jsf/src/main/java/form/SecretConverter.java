package form;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("converter.secrets")
public class SecretConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if( null == s || s.isEmpty()) {
            return s;
        }
        if("bunny".equals(s)){
            FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, "boring", "Thats no secret");
            throw new ConverterException(message);
        }
        return "[Las Vegas] "+s;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return "Your secret is save with me";
    }
}
