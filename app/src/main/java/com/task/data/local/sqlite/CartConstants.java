package com.task.data.local.sqlite;

public class CartConstants {

    //************** Database Name ********************
    public static final String DATABASE_NAME = "task_sqlite_k";

    //************** Database Version Name ********************
    public static final int DATABASE_VERSION = 1;

    //************** Table Name ********************
    public static final String TABLE_NUMBER_FIRST_NAME  = "first_table";

    //**************SQL To Drop Table********************
    public static final String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + TABLE_NUMBER_FIRST_NAME;

    //************** First Table Field Name ********************
    public static final String FIRST_TABLE_COLUMN_1     = "_id";

    public static final String FIRST_TABLE_COLUMN_2     = "category_id";
    public static final String FIRST_TABLE_COLUMN_3     = "category_name";

    public static final String FIRST_TABLE_COLUMN_4     = "item_id";
    public static final String FIRST_TABLE_COLUMN_5     = "item_name";
    public static final String FIRST_TABLE_COLUMN_6     = "item_price";
    public static final String FIRST_TABLE_COLUMN_7     = "item_image";
    public static final String FIRST_TABLE_COLUMN_8     = "item_quantity";
    public static final String FIRST_TABLE_COLUMN_9     = "item_total_price";

    public static final String[] FIRST_TABLE_COLUMNS = {FIRST_TABLE_COLUMN_1,FIRST_TABLE_COLUMN_2,FIRST_TABLE_COLUMN_3,FIRST_TABLE_COLUMN_4,FIRST_TABLE_COLUMN_5,FIRST_TABLE_COLUMN_6,FIRST_TABLE_COLUMN_7,FIRST_TABLE_COLUMN_8,FIRST_TABLE_COLUMN_9};

    //************** Create First Table Query ********************
    public static final String CREATE_TABLE_1 = "CREATE TABLE " + TABLE_NUMBER_FIRST_NAME + " ("+
            FIRST_TABLE_COLUMN_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FIRST_TABLE_COLUMN_2+" TEXT, "+
            FIRST_TABLE_COLUMN_3+" TEXT, "+
            FIRST_TABLE_COLUMN_4+" TEXT, "+
            FIRST_TABLE_COLUMN_5+" TEXT, "+
            FIRST_TABLE_COLUMN_6+" TEXT, "+
            FIRST_TABLE_COLUMN_7+" TEXT, "+
            FIRST_TABLE_COLUMN_8+" TEXT, "+
            FIRST_TABLE_COLUMN_9+" TEXT )";
}