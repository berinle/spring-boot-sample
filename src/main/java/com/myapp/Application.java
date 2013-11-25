package com.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static final Log log = LogFactory.getLog(Application.class);

    @Value("${mail.server.host}")
    private String host;

    @Value("${mail.server.port}")
    private Integer port;

    @Value("${mail.server.protocol}")
    private String protocol;
    @Value("${mail.server.username}")
    private String username;
    @Value("${mail.server.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setShowBanner(false);
        ConfigurableApplicationContext ctx = application.run(args);
        for(String beanName: ctx.getBeanDefinitionNames()){
            System.out.println("beanName = " + beanName);
        }

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


    //=================================== email beans
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail/");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(1);

        return templateResolver;
    }

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setProtocol(protocol);

        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");

        sender.setJavaMailProperties(props);
        return sender;
    }

}