package infrastructure.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;

import java.net.URI;

public class StartJmsServer {

    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName("in-memory");
        brokerService.setPersistent(false);

        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61616"));
        brokerService.addConnector(connector);

        brokerService.start();
    }
}
