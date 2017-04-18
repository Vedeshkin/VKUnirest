package com.github.vedeshkin;

import net.sf.dynamicreports.report.builder.QueryBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import javax.xml.transform.Templates;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by vvedeshkin on 4/18/2017.
 */
public class ReportTestImpl {

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
            se.printStackTrace();
            System.out.println("An error occurred  during initialization");
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
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        ReportTestImpl report = new ReportTestImpl("");
        report.initialize();
        report.build();

    }
}
