package com.github.vedeshkin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vvedeshkin on 4/12/2017.
 */
public class StatusDAOImpl implements StatusDAO {

    private static Logger logger = Logger.getLogger(StatusDAOImpl.class.getName());
    private  static  StatusDAOImpl instance = null;
    private StatusDAOImpl() {
    }

    public static synchronized StatusDAOImpl getInstance()
    {
        if(instance == null){
            instance = new StatusDAOImpl();
        }
        return instance;
    }
    @Override
    public   void storeStatus(Status status) {
        String query = "INSERT INTO STATUS (UID,STATUS,BEGIN,END) VALUES (?,?,?,?)";
        PreparedStatement statement = null;
        try{
          Connection conn = DBmanager.getInstance().getConnection();

                statement =  conn.prepareStatement(query);
                statement.setInt(1,status.getUID());
                statement.setString(2,status.toString());
                statement.setTimestamp(3,Timestamp.from(status.getTimestamp()));
                statement.setTimestamp(4,Timestamp.from(Instant.now()));
                statement.execute();
            statement.close();
            conn.close();
            logger.info("Entity has  saved in DB");
        } catch (SQLException se )
        {
            logger.log(Level.WARNING,"An error occured during the query execution.",se);
        }
    }
}
