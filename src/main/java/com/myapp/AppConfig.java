package com.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.properties.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author berinle
 */
//@Configuration
public class AppConfig extends ServerProperties {

    private static Log log = LogFactory.getLog(AppConfig.class);

    public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
        super.customize(factory);
        factory.setPort(3000); //works here!
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"));

        log.info("AppConfig.customize: Done with custom configuration");
    }
}
