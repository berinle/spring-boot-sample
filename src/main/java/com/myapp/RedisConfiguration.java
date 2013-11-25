package com.myapp;

import com.myapp.messaging.Receiver;
import com.myapp.service.EmailService;
import com.myapp.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.thymeleaf.spring3.SpringTemplateEngine;

/**
 * @author berinle
 */
@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer container(final JedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer() {{
            setConnectionFactory(connectionFactory);
        }};

        container.addMessageListener(listenerAdapter(), new PatternTopic("testChannel"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(receiver(), "receiveMessage");
    }

    @Bean
    StringRedisTemplate template(JedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    Receiver receiver(){
        Receiver receiver = new Receiver();
        receiver.setEmailService(emailService());
        return receiver;
    }

    @Bean
    EmailService emailService(){
        return new EmailServiceImpl();
    }

}
