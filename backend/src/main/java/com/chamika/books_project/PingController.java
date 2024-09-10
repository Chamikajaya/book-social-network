package com.chamika.books_project;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {

    @GetMapping
    public String sayPong() {
        return " I ❤️❤️❤️ DevOps. Pong Pong Pong";
    }

}
