package hello.controller;

import org.springframework.web.bind.annotation.RestController;

import hello.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    public HelloController() {
        System.out.println("hi");
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
