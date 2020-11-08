package com.blax.springconfig.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class EnvironmentController {

    @Value("${com.blax.config.environment}")
    String environment;

    @Value("${com.blax.config.classpath}")
    String fromClasspath;

    @RequestMapping(value = "/environments", method = RequestMethod.GET)
    public String returnEnv() {
        return "Environments: " + environment + "\nfromClasspath: " + fromClasspath;
    }
}