package com.example.bragalund.taxiorder.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBService extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    public static final String DB_NAME = "sqlitedatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_ORDER = "TABLE_TAXIORDER";
    private String FIRST_COLUMN_ORDER_ID = "ORDERID";
    private String SECOND_COLUMN_CURRENT_ADDRESS = "ADDRESS";
    private String THIRD_COLUMN_DESTINATION_ADDRESS = "DESTINATIONADDRESS";
    private String FOURTH_COLUMN_HOUR = "HOUR";
    private String FIFTH_COLUMN_MIN = "MIN";

    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public String createDatabase() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + "("
                + FIRST_COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, "
                + SECOND_COLUMN_CURRENT_ADDRESS + " VARCHAR, "
                + THIRD_COLUMN_DESTINATION_ADDRESS + " VARCHAR, "
                + FOURTH_COLUMN_HOUR + " INTEGER, "
                + FIFTH_COLUMN_MIN + " INTEGER);";
    }

    public String dropDatabase() {
        return "DROP TABLE IF EXISTS " + TABLE_ORDER + ";";
    }

    public String insertDummyData1() {
        return "INSERT INTO " + TABLE_ORDER
                + " VALUES(1,'Tøyenveien 3','Smeltedigelen 1',14,15);";
    }

    public String insertDummyData2() {
        return "INSERT INTO " + TABLE_ORDER
                + " VALUES(2,'Smeltedigelen 2','Christian Krogs gate 4',16,05);";
    }

    public String insertDummyData3(){
        return "INSERT INTO " + TABLE_ORDER
                + " VALUES(3,'Arbeidergata 4','Trollveien 6',04,40);";
    }

    public String insertDummyData4(){
        return "INSERT INTO " + TABLE_ORDER
                + " VALUES(4,'Kantarellveien 3','Soppveien 2',13,46);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dropDatabase());
        db.execSQL(createDatabase());

        //Just creating som default data in the database for testing purposes
        db.execSQL(insertDummyData1());
        db.execSQL(insertDummyData2());
        db.execSQL(insertDummyData3());
        db.execSQL(insertDummyData4());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropDatabase());
        onCreate(db);
    }

    public void insertOrderIntoDatabase(Order order) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECOND_COLUMN_CURRENT_ADDRESS, order.getCurrentAddress());
        contentValues.put(THIRD_COLUMN_DESTINATION_ADDRESS, order.getDestinationAddress());
        contentValues.put(FOURTH_COLUMN_HOUR, order.getHour());
        contentValues.put(FIFTH_COLUMN_MIN, order.getMin());
        database.insert(TABLE_ORDER, null, contentValues);
        database.close();
    }

    //not working or implemented
    public void updateOrderInDatabase() {
        database = this.getReadableDatabase();
        //database.update();
        database.close();
    }

    //not implemented or working
    public void deleteOrder() {
        database = this.getReadableDatabase();
        //database.delete();
        database.close();
    }

    //Maybe working, but not tested or used
    public ArrayList<Order> getAllOrders() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ORDER, null, null, null, null, null, null, null);
        ArrayList<Order> orders = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Order order = new Order(0, "", "", 0, 0);
                order.setOrderID(cursor.getInt(0));
                order.setCurrentAddress(cursor.getString(1));
                order.setDestinationAddress(cursor.getString(2));
                order.setHour(cursor.getInt(3));
                order.setMin(cursor.getInt(4));
                orders.add(order);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }


}
