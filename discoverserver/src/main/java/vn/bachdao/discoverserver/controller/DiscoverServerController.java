package vn.bachdao.discoverserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/discover")
public class DiscoverServerController {

    @GetMapping
    public String helloWorld() {
        return "Hello World from discoverserver";
    }
}
