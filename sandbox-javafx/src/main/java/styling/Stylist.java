package styling;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Stylist implements ChangeListener<String> {
    private StyleSink victim;

    public Stylist(StyleSink victim) {
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
