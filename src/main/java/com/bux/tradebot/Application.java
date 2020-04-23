package com.bux.tradebot;


import com.bux.tradebot.config.ApplicationConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import picocli.CommandLine;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

@Log4j2
class Application {

    public static void main(String[] args) throws InterruptedException, DeploymentException, URISyntaxException, IOException {
        if (new CommandLine(new ApplicationCommand()).execute(args) != 0) {
            return;
        }
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        registerArgs(args, ctx);
        WebsocketClient websocketClient = ctx.getBean(WebsocketClient.class);
        websocketClient.start();

    }

    private static void registerArgs(String[] args, AnnotationConfigApplicationContext context) {
        PropertySource theSource = new SimpleCommandLinePropertySource(args);
        context.getEnvironment().getPropertySources().addFirst(theSource);
        context.register(ApplicationConfig.class);
        context.refresh();
    }
}