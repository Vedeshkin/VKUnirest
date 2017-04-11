package com.github.vedeshkin;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class DOA {

    private Server h2tcpServer;
    private Server h2WebServer;

    public DOA() throws SQLException {
        h2tcpServer = Server.createTcpServer("-tcpPort","9000","-tcpAllowOthers").start();
        h2WebServer = Server.createWebServer("-webPort","9001","-webAllowOthers").start();
    }
    public void showStatus()
    {
        System.out.println(h2tcpServer.getStatus());
        System.out.println(h2tcpServer.getURL());
        System.out.println(h2WebServer.getStatus());
        System.out.println(h2WebServer.getURL());
    }
}
