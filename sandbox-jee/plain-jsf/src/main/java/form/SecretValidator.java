package form;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.logging.Logger;

@FacesValidator("validator.secret")
public class SecretValidator implements Validator {

    private Logger logger = Logger.getLogger("blub");

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(!(o instanceof String)){
                throw new ValidatorException(new FacesMessage("Common knowledge", "Everybody knows that"));
        }
        String secret = (String)o;
        if("Life on mars".equals(secret)){
            throw new ValidatorException(new FacesMessage("I cant check that", "Where the hell should I get a spacecraft that travels to mars?"));
        }
    }
}
