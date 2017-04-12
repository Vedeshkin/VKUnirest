package com.github.vedeshkin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by vvedeshkin on 4/12/2017.
 */
public class UserDaoImpl implements UserDAO {
    @Override
    public void storeUser(UserBean user) {

    }

    @Override
    public void storeUsers(List<UserBean> users) {
        String query = "INSERT INTO USERS (UID,USER_NAME) VALUES (?,?)";
        PreparedStatement statement = null;
        try{
            statement = DBmanager.getInstance().getConnection().prepareStatement(query);
            for (UserBean u: users )
            {
                statement.setInt(1,u.getUID());
                statement.setString(2,u.getUserName());
                statement.execute();
            }
            statement.close();
        } catch (SQLException se )
        {
            se.printStackTrace();
        }

    }

    @Override
    public List<UserBean> getAllUsers() {
        return null;
    }

    @Override
    public void deleteAllUsers() {
        String query = "TRUNCATE TABLE USERS";
        Statement st = null;
        try{
            st = DBmanager.getInstance().getConnection().createStatement();
            int count = st.executeUpdate(query);
            System.out.printf("Query has been executed:%s\n",query);
            System.out.printf("Rows affected: %d \n",count);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
