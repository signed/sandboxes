package view.parameter.cdi;

import javax.enterprise.util.Nonbinding;
import javax.xml.ws.BindingType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BindingType

@Retention(RetentionPolicy.RUNTIME)

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})

public @interface HttpParam {

   @Nonbinding public String value();

}