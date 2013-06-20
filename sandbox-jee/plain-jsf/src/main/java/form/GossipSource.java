package form;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import java.util.logging.Logger;

public class GossipSource implements javax.faces.event.ActionListener {
    private final Logger logger = Logger.getLogger(GossipSource.class.getName());
    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        logger.info("some told us a secret!");
    }
}