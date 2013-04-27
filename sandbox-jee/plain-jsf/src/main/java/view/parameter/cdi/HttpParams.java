package view.parameter.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class HttpParams{
   @Produces @HttpParam("")
   public String getParamValue(InjectionPoint ip) {
       FacesContext facesContext = FacesContext.getCurrentInstance();
       ExternalContext externalContext = facesContext.getExternalContext();
       String parameter = ip.getAnnotated().getAnnotation(HttpParam.class).value();
       return externalContext.getRequestParameterMap().get(parameter);
   }
}