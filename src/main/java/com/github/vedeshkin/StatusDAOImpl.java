package com.github.vedeshkin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by vvedeshkin on 4/12/2017.
 */
public class StatusDAOImpl implements StatusDAO {

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
            statement = DBmanager.getInstance().getConnection().prepareStatement(query);


                statement.setInt(1,status.getUID());
                statement.setString(2,status.toString());
                statement.setTimestamp(3,Timestamp.from(status.getTimestamp()));
                statement.setTimestamp(4,Timestamp.from(Instant.now()));
                statement.execute();
            statement.close();
            System.out.printf("Entity has  saved in DB\n");
        } catch (SQLException se )
        {
            se.printStackTrace();
        }
    }
}
