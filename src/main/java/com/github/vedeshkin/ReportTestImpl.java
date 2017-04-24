package com.github.vedeshkin;

import net.sf.dynamicreports.report.exception.DRException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by vvedeshkin on 4/18/2017.
 */
public class ReportTestImpl {

    private static final Logger logger = Logger.getLogger(ReportTestImpl.class.getName());
    private String userName;
    int  uid;
    private Connection connection;
    private final String  query  = "SELECT UID FROM USERS WHERE USER_NAME = ?";
    private final String select = "SELECT * FROM STATUS WHERE UID = ";
    public ReportTestImpl(String userName)
    {
        this.userName = userName;

    }

    private void initialize() {
        uid = 0;
        try {
            connection = DBmanager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet rs =  statement.executeQuery();
            while (rs.next()) {
                uid = rs.getInt("UID");
                System.out.printf("UID for entity %s is %d \n",userName,uid);
            }

        }catch (SQLException se){
            logger.log(Level.SEVERE,"An error ocured during initialization",se);
            DBmanager.getInstance().shutdown();
        }
        if(uid != 0 )
        {
            System.out.printf("UID for entity %s is %d \n",userName,uid);
        }
    }

    private void build() {
        try{
            report()

                    .columns(
                            col.column("UID","UID", type.integerType()),
                            col.column("Status","STATUS",type.stringType()),
                            col.column("Begin","BEGIN",type.dateType()),
                            col.column("End","END",type.dateType()))
                    .setDataSource(select + uid,connection)
                    .show();
        }catch (DRException e) {
           logger.log(Level.SEVERE,"Build of report has been failed",e);
        }

    }


    public static void main(String[] args) {

        ReportTestImpl report = new ReportTestImpl("Ар Ти");
        report.initialize();
        report.build();

    }
}
