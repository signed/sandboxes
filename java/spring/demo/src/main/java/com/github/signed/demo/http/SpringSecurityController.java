package com.github.signed.demo.http;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityController {

    @GetMapping(path = "/security/admin")
    @Secured("ROLE_ADMIN") //TODO move this into the security configuration? If the paths are not listed in the security configuration it appears that the user lookup is not triggered
    public String admin() {
        return "Congratulations! You are an Admin";
    }
}
