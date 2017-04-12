package com.github.vedeshkin;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class DBmanager {

    private Server h2tcpServer;
    private Server h2WebServer;
    private JdbcDataSource dataSource;
    JdbcConnectionPool connectionPool;
    private static DBmanager instance = null;


    private DBmanager() throws SQLException {
        h2tcpServer = Server.createTcpServer("-tcpPort","9000","-tcpAllowOthers").start();
        h2WebServer = Server.createWebServer("-webPort","9001","-webAllowOthers").start();
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:tcp:localhost:9000/~/VK;INIT=RUNSCRIPT FROM 'C:/sql/create.sql'");
        dataSource.setLoginTimeout(0);
        dataSource.setUser("sa");//should be in properties?
        dataSource.setPassword("sa");
        connectionPool = JdbcConnectionPool.create(dataSource);
        connectionPool.setLoginTimeout(0);
    }
    public static synchronized  DBmanager getInstance() {
        if (instance ==  null ) {
            try {
                instance = new DBmanager();
                return instance;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;

    }


    public void showStatus()
    {
        System.out.println(h2tcpServer.getStatus());
        System.out.println(h2tcpServer.getURL());
        System.out.println(h2WebServer.getStatus());
        System.out.println(h2WebServer.getURL());
    }
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();

    }
}
