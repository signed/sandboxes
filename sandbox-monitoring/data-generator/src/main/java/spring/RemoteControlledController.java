package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.buffer.BufferMetricReader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteControlledController {


    private final CounterService counterService;
    private final GaugeService gaugeService;
    private final BufferMetricReader bufferMetricReader;

    @Autowired
    public RemoteControlledController(CounterService counterService, GaugeService gaugeService, BufferMetricReader bufferMetricReader) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
        this.bufferMetricReader = bufferMetricReader;
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/resources/{id}/property")
    @ResponseBody
    String pathVariable(@PathVariable(name = "id") String id) {
        return "Hello " + id;

    }

}
