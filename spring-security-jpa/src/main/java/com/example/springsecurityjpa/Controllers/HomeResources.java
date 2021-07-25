package com.example.springsecurityjpa.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResources {
    @GetMapping("/")
    public String home(){
        return "<h1>Hello.<h1>";
    }

    @GetMapping("/user")
    public String homeUser(){
        return "<h1>Hello User.<h1>";
    }

    @GetMapping("/admin")
    public String homeAdmin(){
        return "<h1>Hello Admin.<h1>";
    }

}
