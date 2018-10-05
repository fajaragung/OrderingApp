package com.robotsoftware.orderingapp.database.var;

public interface ConstDatabaseSQLite {

    /**
     * initially or create database information
     */
    String DATABASE_NAME = "user_order";
    String DATABASE_TABLE = "order_table";
    int DATABASE_VERSION = 1;

    /**
     * initially col database
     */
    String COL_NAME = "name";
    String COL_CHECKED = "checked";
    String COL_QUANTITY = "quantity";
    String COL_PRICE = "price";

    /**
     * initially for create database
     */
    String CREATE_TABLE_DATABASE = "CREATE TABLE " + DATABASE_TABLE +
            " ( " +
            COL_NAME + " TEXT, " +
            COL_CHECKED + " TEXT, " +
            COL_QUANTITY + " TEXT, " +
            COL_PRICE + " TEXT ) ";

}
