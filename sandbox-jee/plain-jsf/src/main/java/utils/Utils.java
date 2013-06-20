package utils;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class Utils {
    public static <T>  T getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object bean;

        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }

        if (bean == null) {
            throw new FacesException("Managed bean with name '" + beanName
                    + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
        }

        return (T)bean;
    }

}
