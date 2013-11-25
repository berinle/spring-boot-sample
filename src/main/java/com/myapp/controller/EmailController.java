package com.myapp.controller;

import com.myapp.service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private static final Log log = LogFactory.getLog(EmailController.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHome(){
        return "index";
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String sendEmail(){

        log.debug("EmailController.sendEmail");

        template.convertAndSend("testChannel", "hello world");

        return "confirm";
    }
}
