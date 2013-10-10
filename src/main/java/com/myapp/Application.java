package com.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static final Log log = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setShowBanner(true);
        application.run(args);
    }

    //=================================== method 1
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
//                factory.setPort(3000); //getting overridden (see https://github.com/spring-projects/spring-boot/issues/84 workaround for now... use application.properties or ServerProperties) (BUG)
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"));
                log.info("Application.customize");
            }
        };
    }

}