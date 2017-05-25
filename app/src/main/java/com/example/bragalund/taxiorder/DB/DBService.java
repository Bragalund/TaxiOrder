package com.example.bragalund.taxiorder.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBService extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    public static final String DB_NAME = "sqlitedatabase.db";
    public static final int DATABASE_VERSION = 1;
    private String TABLE_ORDER = "ORDER";
    private String FIRST_COLUMN_ORDER_ID = "ORDERID";
    private String SECOND_COLUMN_CURRENT_ADDRESS = "ADDRESS";
    private String THIRD_COLUMN_DESTINATION_ADDRESS = "DESTINATION_ADDRESS";
    private int FOURTH_COLUMN_HOUR = 0;
    private int FIFTH_COLUMN_MIN = 0;
    private String SIXTH_COLUMN_CUSTOMER_PHONE_NUMBER = "PHONENUMBER";

    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_ORDER + " ("
                + FIRST_COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SECOND_COLUMN_CURRENT_ADDRESS + " VARCHAR, "
                + THIRD_COLUMN_DESTINATION_ADDRESS + " VARCHAR, "
                + FOURTH_COLUMN_HOUR + " INTEGER, "
                + FIFTH_COLUMN_MIN + " INTEGER, "
                + SIXTH_COLUMN_CUSTOMER_PHONE_NUMBER + " VARCHAR);");

        //Just creating som default data in the database for testing purposes
        db.execSQL("INSERT INTO " + TABLE_ORDER
                + " VALUES(1,'Tøyenveien 3','Smeltedigelen 1',14,15, '22334455');");

        db.execSQL("INSERT INTO " + TABLE_ORDER
                + " VALUES(2,'Smeltedigelen 2','Christian Krogs gate 4',16,05, '99887766');");

        db.execSQL("INSERT INTO " + TABLE_ORDER
                + " VALUES(3,'Arbeidergata 4','Trollveien 6',04,40, '12345678');");

        db.execSQL("INSERT INTO " + TABLE_ORDER
                + " VALUES(4,'Kantarellveien 3','Soppveien 2',13,46, '62536475');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER + ";");
        onCreate(db);
    }

    public void insertOrderIntoDatabase() {
        database = this.getReadableDatabase();

    }

    public void updateOrderInDatabase() {
        database = this.getReadableDatabase();
        //database.update();
        database.close();
    }

    public void deleteOrder() {
        database = this.getReadableDatabase();
        //database.delete();
        database.close();
    }

    public ArrayList<Order> getAllOrders() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ORDER, null, null, null, null, null, null, null);
        ArrayList<Order> orders = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Order order = new Order(null, "", "", 0, 0, "");
                order.setOrderID(cursor.getString(0));
                order.setCurrentAddress(cursor.getString(1));
                order.setDestinationAddress(cursor.getString(2));
                order.setHour(cursor.getInt(3));
                order.setMin(cursor.getInt(4));
                order.setCustomerPhoneNumber(cursor.getString(5));
                orders.add(order);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }


}
