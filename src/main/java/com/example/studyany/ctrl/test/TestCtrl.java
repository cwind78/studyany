package com.example.studyany.ctrl.test;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCtrl {
    @GetMapping("/api1")
    @Hidden
    public String getApi1AuthTest() {
        return "api1";
    }

    @GetMapping("/api2")
    @Hidden
    public String getApi2AuthTest() {
        return "api2";
    }
}
