package org.jc1c.qr;

import org.jc1c.JServer;

import java.io.*;

public class App {

    public static void main(String[] args) throws IOException {

        JServer.builder()
                .withArgs(args)
                .withBacklog(3)
                .withThreadPool(3)
                .withHandlersController(AppHandlers.class)
                .build()
                .start();

    }

}
