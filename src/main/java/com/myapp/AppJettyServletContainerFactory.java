package com.myapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;

/**
 * @author berinle
 */
public class AppJettyServletContainerFactory extends JettyEmbeddedServletContainerFactory {

    //http://stackoverflow.com/questions/14362245/programatically-configure-ssl-for-jetty-9-embedded

    @Override
    protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("path/to/keystore");
        sslContextFactory.setKeyStorePassword("password");

        return super.getJettyEmbeddedServletContainer(server);
    }

    @Override
    public EmbeddedServletContainer getEmbeddedServletContainer(ServletContextInitializer... initializers) {
        return super.getEmbeddedServletContainer(initializers);
    }
}
