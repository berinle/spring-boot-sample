package com.myapp.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.myapp.domain.MyThing;


@Controller
@RequestMapping("/hello-world")
public class MyController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    MyThing process(@RequestParam(value = "content", required = false, defaultValue = "content") String content) {
        return new MyThing(counter.incrementAndGet(), content );
    }

}