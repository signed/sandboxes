package sandbox.ribbon;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;

public class RibbonMain {

    public static void main(String[] args) throws Exception {
        read_and_print_initial_servers_from_properties_file();
        RestClient client = (RestClient) ClientFactory.getNamedClient("sample-client");  // 2
        do_20_requests_and_print_status_codes(client);
        printLoadBalanceStatsFor(client);
        change_servers();
        do_20_requests_and_print_status_codes(client);
        printLoadBalanceStatsFor(client);
    }

    private static void read_and_print_initial_servers_from_properties_file() throws IOException {
        ConfigurationManager.loadPropertiesFromResources("ribbon/sample-client.properties");  // 1
        System.out.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers"));
    }

    private static void do_20_requests_and_print_status_codes(RestClient client) throws URISyntaxException, com.netflix.client.ClientException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build();
        for (int i = 0; i < 20; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.out.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
            response.close();
        }
    }

    private static void printLoadBalanceStatsFor(RestClient client) {
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.out.println(lb.getLoadBalancerStats()); // 7
    }

    private static void change_servers() throws InterruptedException {
        ConfigurationManager.getConfigInstance().setProperty("sample-client.ribbon.listOfServers", "www.linkedin.com:80,www.google.com:80"); // 5
        System.out.println("changing servers ...");
        Thread.sleep(3000);
    }
}