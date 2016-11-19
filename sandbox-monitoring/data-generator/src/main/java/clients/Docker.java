package clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Docker {

    public String dockerIp() {
        try {
            Process process = Runtime.getRuntime().exec("docker-machine ip docker-vm");
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ip = reader.readLine();
            reader.close();
            return ip;
        } catch (Exception e) {
            return "localhost";
        }

    }
}
