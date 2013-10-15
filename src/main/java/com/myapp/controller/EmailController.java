package com.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 * @author berinle
 */
@Controller
public class EmailController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHome(){
        return "index";
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String sendEmail(){
        System.out.println("EmailController.sendEmail");

        Context context = new Context();
        context.setVariable("user", "John Doe");

        String htmlContent = templateEngine.process("email.html", context);
        System.out.println("htmlContent = " + htmlContent);

        return "confirm";
    }
}
