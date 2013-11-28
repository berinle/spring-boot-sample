package com.myapp;

import com.myapp.messaging.Receiver;
import com.myapp.service.EmailService;
import com.myapp.service.EmailServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author berinle
 */
@Configuration
public class RedisConfiguration {

    private static final Log log = LogFactory.getLog(RedisConfiguration.class);

    @Bean
    JedisConnectionFactory connectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        //attempt to read values dynamically
        try {
            String rediscloud_url = System.getenv("REDISCLOUD_URL");
            if(null != rediscloud_url){
                URI uri = new URI(rediscloud_url);
                connectionFactory.setHostName(uri.getHost());
                connectionFactory.setPort(uri.getPort());
                connectionFactory.setPassword(uri.getUserInfo().split(":", 2)[1]);
            }
        } catch (URISyntaxException e) {
            log.error(e);
        }

        return connectionFactory;
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
        return new MessageListenerAdapter(new Receiver(emailService()), "receiveMessage");
    }

    @Bean
    StringRedisTemplate template(JedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    EmailService emailService(){
        return new EmailServiceImpl();
    }

}
