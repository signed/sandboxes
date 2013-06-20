package form;

import utils.Utils;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import java.util.logging.Logger;

public class GossipSource implements javax.faces.event.ActionListener {
    private final Logger logger = Logger.getLogger(GossipSource.class.getName());

    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        FormBean formBean = Utils.getManagedBean("formData");
        logger.info(formBean.getName() + " told us a secret!");
    }
}