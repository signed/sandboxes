package com.github.signed.sandboxes.spring.boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CatchAllController {

    @RequestMapping
    public String catchCall(HttpServletRequest request){
        return "default: "+request.getRequestURI();
    }
}
