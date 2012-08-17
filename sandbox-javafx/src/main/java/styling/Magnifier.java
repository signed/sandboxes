package styling;

import com.google.common.base.Optional;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Magnifier implements EventHandler<KeyEvent> {
    private Optional<Scalable>scalable;

    public Magnifier(Scalable scalable) {
        this.scalable = Optional.fromNullable(scalable);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(!scalable.isPresent())return;

        KeyCode pressedKey = keyEvent.getCode();
        if(KeyCode.PLUS == pressedKey || KeyCode.ADD == pressedKey){
            scalable.get().incrementScale();
        }else if(KeyCode.MINUS == pressedKey || KeyCode.SUBTRACT == pressedKey){
            scalable.get().decrementScale();
        }else if(KeyCode.DIGIT0 == pressedKey ||KeyCode.NUMPAD0 == pressedKey){
            scalable.get().resetScale();
        }
    }

    public void actOn(Scalable scalable) {
        this.scalable = Optional.fromNullable(scalable);
    }
}
