package styling;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Magnifier implements EventHandler<KeyEvent> {
    private Scalable scalable;

    public Magnifier(Scalable scalable) {
        this.scalable = scalable;
    }

    @Override
    public void handle(KeyEvent keyEvent) {

        KeyCode pressedKey = keyEvent.getCode();
        if(KeyCode.PLUS == pressedKey || KeyCode.ADD == pressedKey){
            scalable.incrementScale();
        }else if(KeyCode.MINUS == pressedKey || KeyCode.SUBTRACT == pressedKey){
            scalable.decrementScale();
        }else if(KeyCode.DIGIT0 == pressedKey ||KeyCode.NUMPAD0 == pressedKey){
            scalable.resetScale();
        }
    }
}
