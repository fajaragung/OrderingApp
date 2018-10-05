package com.robotsoftware.orderingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.robotsoftware.orderingapp.activities.models.UserOrder;
import com.robotsoftware.orderingapp.database.var.ConstDatabaseSQLite;

import java.util.ArrayList;

public class DatabaseSQLite extends SQLiteOpenHelper implements ConstDatabaseSQLite {

    /**
     * constructor
     *
     * @param context
     */
    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * this method for create database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_DATABASE);
    }

    /**
     * this method for upgrade table database
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    /**
     * this for insert data to database sqlite
     */
    public void addDataOrder(UserOrder userOrder) {

        SQLiteDatabase dbSQL = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, userOrder.getName());
        values.put(COL_CHECKED, userOrder.getChecked());
        values.put(COL_QUANTITY, userOrder.getQuantity());
        values.put(COL_PRICE, userOrder.getPrice());

        dbSQL.insert(DATABASE_TABLE, null, values);
        dbSQL.close();
    }

    /**
     * this for display all data on database to adapter layout
     */
    public ArrayList<UserOrder> displaysDataOrder() {

        ArrayList<UserOrder> userOrders = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE;

        SQLiteDatabase dbSQL = this.getReadableDatabase();
        Cursor cursor = dbSQL.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

            do {

                UserOrder userOrder = new UserOrder();
                userOrder.setName(": " + cursor.getString(0));
                userOrder.setChecked(": " + cursor.getString(1));
                userOrder.setQuantity(": " + cursor.getString(2) + " Qty");
                userOrder.setPrice(": $" + cursor.getString(3) + ".00");

                userOrders.add(userOrder);
            } while (cursor.moveToNext());
        }

        return userOrders;
    }

    /**
     * this delete row of database
     */
    public void deleteOrder(String name) {

        SQLiteDatabase dbSQL = this.getWritableDatabase();
        dbSQL.delete(DATABASE_TABLE, COL_NAME + " = ? ", new String[]{name});
        dbSQL.close();
    }
}
