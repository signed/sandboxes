package styling;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Stylist implements ChangeListener<String> {
    private Manican victim;

    public Stylist(Manican victim) {
        this.victim = victim;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
        if(s1.isEmpty()){
            victim.clearStyle();
        }else{
            victim.setStyle(s1);
        }
    }
}
