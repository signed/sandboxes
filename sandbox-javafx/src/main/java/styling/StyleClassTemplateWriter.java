package styling;

import com.google.common.base.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

public class StyleClassTemplateWriter implements EventHandler<ActionEvent> {
    private Optional<Exhibit> exhibit = Optional.absent();
    private StylePad styleSheetPad;

    public StyleClassTemplateWriter(Exhibit exhibit, StylePad styleSheetPad) {
        this.exhibit = Optional.fromNullable(exhibit);
        this.styleSheetPad = styleSheetPad;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if(!exhibit.isPresent())return;

        exhibit.get().putStyleClassesInto(new StyleClassSink() {
            @Override
            public void consume(List<String> styleClasses) {
                StringBuilder builder = new StringBuilder();
                for (String styleClass : styleClasses) {
                    builder.append(".").append(styleClass).append("{").append("\n");
                    builder.append("\n");
                    builder.append("}").append("\n");
                }
                styleSheetPad.showStyle(builder.toString());
            }
        });
    }

    public void actOn(Exhibit exhibit) {
        this.exhibit = Optional.fromNullable(exhibit);
    }
}
