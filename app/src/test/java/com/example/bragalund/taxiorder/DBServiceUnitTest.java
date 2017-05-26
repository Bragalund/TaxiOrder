package com.example.bragalund.taxiorder;

import android.content.Context;

import com.example.bragalund.taxiorder.DB.DBService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class DBServiceUnitTest {

    @Mock
    Context mMockContext;

    // To get this test to work, you have to have a running H2 database on your machine.

    private final String URL = "jdbc:h2:tcp://localhost/~/test";
    private final String USERNAME = "test";
    private final String PASSWORD = "test";

    Connection connection = null;

    @Before
    public void createConnectionToDatabase() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection to DB created.");
            Statement statement = connection.createStatement();
            statement.execute(createDBServiceObject().createDatabase());
            System.out.println("Database created.");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classException) {
            classException.printStackTrace();
        }
    }

    @After
    public void dropTableInH2Database() {
        try {
            DBService dbService = createDBServiceObject();
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("drop table " + dbService.TABLE_ORDER + ";");
            System.out.println("Table dropped... ");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classException) {
            classException.printStackTrace();
        }
    }

    //Util-method
    private DBService createDBServiceObject() {
        return new DBService(mMockContext, DBService.DB_NAME, null, DBService.DATABASE_VERSION);
    }

    @Test
    public void createDatabaseTest() {
        try {
            DBService dbService = createDBServiceObject();
            Statement statement = connection.createStatement();
            assertTrue(statement.execute("Select * from " + dbService.TABLE_ORDER + ";"));
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void createDummyDataTest() {
        try {

            DBService dbService = createDBServiceObject();
            Statement statement = connection.createStatement();
            statement.execute(dbService.insertDummyData1());
            statement.execute(dbService.insertDummyData2());
            statement.execute(dbService.insertDummyData3());
            statement.execute(dbService.insertDummyData4());

            System.out.println("Inserted dummy data.");
            String SQL = "select count(*) from " + dbService.TABLE_ORDER + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                assertEquals(4, resultSet.getInt(1));
            } else {
                fail();
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            fail();
        }
    }


    //TODO mock DB_NAME, use another database than this.getReadableDatabase()
    @Test
    public void getAllOrdersTest() {

        fail(); //Fails because test not implemented

        try {
            DBService dbService = createDBServiceObject();
            Statement statement = connection.createStatement();

            statement.execute(dbService.insertDummyData1());
            statement.execute(dbService.insertDummyData2());
            statement.execute(dbService.insertDummyData3());
            statement.execute(dbService.insertDummyData4());

            System.out.println("Inserted dummy data.");
            String SQL = "select * from " + dbService.TABLE_ORDER + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }


}
