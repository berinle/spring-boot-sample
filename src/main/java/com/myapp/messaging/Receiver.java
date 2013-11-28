package com.myapp.messaging;

import com.myapp.service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * @author berinle
 */
public class Receiver {

    private Log log = LogFactory.getLog(Receiver.class);

    private EmailService emailService;

    public Receiver(){}
    public Receiver(EmailService emailService){
        this.emailService = emailService;
    }

    public void receiveMessage(String message){
        log.info("Received <" + message + ">");

        Assert.notNull(emailService);

        emailService.sendEmail(message);
    }
}
