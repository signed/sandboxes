package com.github.signed.sandboxes.spring.boot;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toMap;

@RestController
public class ExternalConfigurationController {

    private final Environment environment;

    public ExternalConfigurationController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home(@RequestParam(value = "propertyKey") String propertyKey) {
        List<String> propertyKeys = Collections.singletonList(propertyKey);
        Map<String, String> collect = propertyKeys.stream().filter(environment::containsProperty).collect(toMap(t -> t, environment::getProperty));
        collect = new HashMap<>();
        String property = environment.getProperty("application.global");
        System.out.println("\'"+property+"\'");
        collect.put("application.global", property);
        return ResponseEntity.ok(collect);
    }

}
