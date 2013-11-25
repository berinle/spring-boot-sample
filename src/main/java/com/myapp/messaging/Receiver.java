package com.myapp.messaging;

import com.myapp.service.EmailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author berinle
 */
@Component
public class Receiver {

    private Log log = LogFactory.getLog(Receiver.class);

    @Autowired
    private EmailService emailService;

    public void receiveMessage(String message){
        log.info("Received <" + message + ">");

        Assert.notNull(emailService);

        emailService.sendEmail(message);
    }
}
