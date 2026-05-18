package de.oumaima.expensetracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from expense tracker!";
    }

    @GetMapping("/version")
    public String version() {
        return "0.0.1-SNAPSHOT";
    }

}
