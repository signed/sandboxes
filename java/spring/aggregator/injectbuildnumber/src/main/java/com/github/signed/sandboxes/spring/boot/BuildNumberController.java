package com.github.signed.sandboxes.spring.boot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BuildNumberController {

    private final BuildNumberControllerConfiguration.ApplicationVersion applicationVersion;

    public BuildNumberController(BuildNumberControllerConfiguration.ApplicationVersion applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {

        ApplicationVersionTO entity = new ApplicationVersionTO();
        entity.commit_hash = applicationVersion.commitHash;
        entity.version = applicationVersion.version;
        entity.timestamp = applicationVersion.timestamp;
        entity.build_number = applicationVersion.buildNumber;

        return ResponseEntity.ok(entity);
    }


}
