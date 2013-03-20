package beans;

import javax.ejb.Stateful;
import javax.enterprise.inject.Model;

@Stateful
@Model
public class GreetingBean {
    public String getGreeting() {
        return "Hello world2";
    }
}