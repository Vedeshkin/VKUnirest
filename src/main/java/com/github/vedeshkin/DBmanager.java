package com.github.vedeshkin;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class DBmanager {

    private Server h2tcpServer;
    private Server h2WebServer;
    private JdbcDataSource dataSource;
    JdbcConnectionPool connectionPool;
    private static DBmanager instance = null;
    private static PropertiesHolder ph = PropertiesHolder.getInstance();
    private static final Logger LOGGER = Logger.getLogger(DBmanager.class.getName());

    private DBmanager() throws SQLException {
        h2tcpServer = Server.createTcpServer("-tcpPort",ph.getProperty("dbTcpPort"),"-tcpAllowOthers").start();
        h2WebServer = Server.createWebServer("-webPort",ph.getProperty("dbWebPort"),"-webAllowOthers").start();
        dataSource = new JdbcDataSource();
        dataSource.setURL(ph.getProperty("jdbcConnectionString"));
        dataSource.setLoginTimeout(0);
        dataSource.setUser(ph.getProperty("dbUser"));
        dataSource.setPassword(ph.getProperty("dbPass"));
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
        LOGGER.info(h2tcpServer.getStatus());
        LOGGER.info(h2tcpServer.getURL());
        LOGGER.info(h2WebServer.getStatus());
        LOGGER.info(h2WebServer.getURL());
    }
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();

    }

    public void shutdown() {
        if (h2tcpServer != null)
            h2tcpServer.shutdown();
        if(h2WebServer != null)
        h2WebServer.shutdown();
    }
}
