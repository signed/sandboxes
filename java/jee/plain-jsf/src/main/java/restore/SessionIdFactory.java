package restore;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

public class SessionIdFactory {

    @Produces
    public FacesContext context(){
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @Named("sessionId")
    public SessionId sessionId(FacesContext context) {
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        if( null == session) {
            return new SessionId("no session");
        }
        return new SessionId(session.getId());
    }
}
