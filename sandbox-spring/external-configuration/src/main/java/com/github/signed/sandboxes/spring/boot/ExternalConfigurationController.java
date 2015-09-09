package com.github.signed.sandboxes.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toMap;

@RestController
public class ExternalConfigurationController {

    private final Environment environment;

    public ExternalConfigurationController(Environment environment){
        this.environment = environment;
    }

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {
        List<String> propertyKeys = new ArrayList<>();
        propertyKeys.add("application.global");
        Map<String, String> collect = propertyKeys.stream().filter(environment::containsProperty).collect(toMap(t -> t, environment::getProperty));
        return ResponseEntity.ok(collect);
    }

}
