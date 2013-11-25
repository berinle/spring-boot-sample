package com.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 * @author berinle
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(String message) {
        Context context = new Context();
        context.setVariable("user", "John Doe");

        String htmlContent = templateEngine.process("email.html", context);
        System.out.println("htmlContent = " + htmlContent);
    }
}
