package com.myapp;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author berinle
 */
@Component
public class AppTomcatConnectorCustomizer implements TomcatConnectorCustomizer {

    @Value("${server.port}")
    Integer serverPort;

    String keyAlias = "tomcat";
    String password = "password";

    @Override
    public void customize(Connector connector) {

        Assert.notNull(serverPort);

        connector.setPort(serverPort);
        connector.setSecure(true);
        connector.setScheme("https");
        connector.setAttribute("keyAlias", keyAlias);
        connector.setAttribute("keystorePass", password);
        try {
            connector.setAttribute("keystoreFile", ResourceUtils.getFile("src/ssl/tomcat.keystore").getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        connector.setAttribute("clientAuth", "false");
        connector.setAttribute("sslProtocol", "TLS");
        connector.setAttribute("SSLEnabled", true);
        System.out.println("AppTomcatConnectorCustomizer.customize done");
    }
}
