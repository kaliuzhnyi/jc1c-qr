package org.jc1c.qr;

import org.jc1c.JServer;

import java.io.*;

public class App {

    public static Integer QRCodeWidth = 500;
    public static Integer QRCodeHeight = 500;

    public static void main(String[] args) throws IOException {

        String hostname = (args.length >= 1 && !args[0].isBlank())
                ? args[0]
                : null;

        Integer port = (args.length >= 2 && !args[1].isBlank())
                ? Integer.valueOf(args[1])
                : null;

        String apiKey = (args.length >= 3 && !args[2].isBlank())
                ? args[2]
                : null;

        JServer.builder()
                .withHostname(hostname)
                .withPort(port)
                .withApiKey(apiKey)
                .withBacklog(3)
                .withThreadPool(3)
                .withHandlersController(AppHandlers.class)
                .build()
                .start();

    }

}
