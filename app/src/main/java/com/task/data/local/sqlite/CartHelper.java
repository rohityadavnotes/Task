package com.task.data.local.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class CartHelper  extends SQLiteOpenHelper {

    public CartHelper(Context context) {
        super(context, CartConstants.DATABASE_NAME, null, CartConstants.DATABASE_VERSION);
        Log.e("DATABASE OPERATION", "Database created / opened.....");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CartConstants.CREATE_TABLE_1);
            Log.e("DATABASE OPERATION", "Table create..." + CartConstants.CREATE_TABLE_1);
        } catch (Exception e) {
            Log.e("onCreate Error", "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try
        {
            Log.w(CartHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            sqLiteDatabase.execSQL(CartConstants.DROP_TABLE_1);
            onCreate(sqLiteDatabase);
        }
        catch (Exception e)
        {
            Log.e("onUpgrade Error", "" + e);
        }
    }

    public boolean insertOperationIntoFirstTable(Cart modelObject) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CartConstants.FIRST_TABLE_COLUMN_2, modelObject.getCategoryId());
        values.put(CartConstants.FIRST_TABLE_COLUMN_3, modelObject.getCategoryName());

        values.put(CartConstants.FIRST_TABLE_COLUMN_4, modelObject.getItemId());
        values.put(CartConstants.FIRST_TABLE_COLUMN_5, modelObject.getItemName());
        values.put(CartConstants.FIRST_TABLE_COLUMN_6, modelObject.getItemPrice());
        values.put(CartConstants.FIRST_TABLE_COLUMN_7, modelObject.getItemImage());
        values.put(CartConstants.FIRST_TABLE_COLUMN_8, modelObject.getItemQuantity());
        values.put(CartConstants.FIRST_TABLE_COLUMN_9, modelObject.getItemTotalPrice());

        long newRowId = sqLiteDatabase.insert(CartConstants.TABLE_NUMBER_FIRST_NAME, null, values);
        Log.e("ROW RESULT INSERT", "" + newRowId);

        sqLiteDatabase.close();

        if (newRowId == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<Cart> getAllRowsFromFirstTable()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Cart> arrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(CartConstants.TABLE_NUMBER_FIRST_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                Integer rowId                   = cursor.getInt(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_1));

                String categoryId               = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_2));
                String categoryName             = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_3));

                String itemId                   = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_4));
                String itemName                 = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_5));
                String itemPrice                = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_6));
                String itemImage                = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_7));
                String itemQuantity             = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_8));
                String itemTotalPrice           = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_9));

                arrayList.add(new Cart(rowId,categoryId,categoryName,itemId,itemName,itemPrice,itemImage,itemQuantity,itemTotalPrice));
            }
        }

        sqLiteDatabase.close();
        cursor.close();
        return arrayList;
    }

    public Cart getSingleRowsFromFirstTable(String id)
    {
        Cart cart = new Cart();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM "+CartConstants.TABLE_NUMBER_FIRST_NAME+" WHERE "+CartConstants.FIRST_TABLE_COLUMN_4+"="+id;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.getCount() > 0)
        {
            while(cursor.moveToNext())
            {
                Integer rowId                   = cursor.getInt(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_1));

                String categoryId               = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_2));
                String categoryName             = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_3));

                String itemId                   = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_4));
                String itemName                 = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_5));
                String itemPrice                = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_6));
                String itemImage                = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_7));
                String itemQuantity             = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_8));
                String itemTotalPrice           = cursor.getString(cursor.getColumnIndex(CartConstants.FIRST_TABLE_COLUMN_9));

                cart = new Cart(rowId,categoryId,categoryName,itemId,itemName,itemPrice,itemImage,itemQuantity,itemTotalPrice);
            }
        }

        sqLiteDatabase.close();
        cursor.close();
        return cart;
    }

    public boolean checkProductIdExist(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+CartConstants.FIRST_TABLE_COLUMN_4+" FROM "+CartConstants.TABLE_NUMBER_FIRST_NAME+" WHERE "+CartConstants.FIRST_TABLE_COLUMN_4+"="+id;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            sqLiteDatabase.close();
            Log.d("Record  Already Exists", "Table is:"+CartConstants.TABLE_NUMBER_FIRST_NAME+" ColumnName:"+CartConstants.FIRST_TABLE_COLUMN_2);
            return true;
        }
        return false;
    }

    public boolean updateOperationIntoFirstTable(Cart modelObject)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CartConstants.FIRST_TABLE_COLUMN_2, modelObject.getCategoryId());
        values.put(CartConstants.FIRST_TABLE_COLUMN_3, modelObject.getCategoryName());

        values.put(CartConstants.FIRST_TABLE_COLUMN_4, modelObject.getItemId());
        values.put(CartConstants.FIRST_TABLE_COLUMN_5, modelObject.getItemName());
        values.put(CartConstants.FIRST_TABLE_COLUMN_6, modelObject.getItemPrice());
        values.put(CartConstants.FIRST_TABLE_COLUMN_7, modelObject.getItemImage());
        values.put(CartConstants.FIRST_TABLE_COLUMN_8, modelObject.getItemQuantity());
        values.put(CartConstants.FIRST_TABLE_COLUMN_9, modelObject.getItemTotalPrice());

        long newRowId= sqLiteDatabase.update(CartConstants.TABLE_NUMBER_FIRST_NAME,
                values,
                CartConstants.FIRST_TABLE_COLUMN_4 + " = ?",
                new String[] {String.valueOf(modelObject.getItemId())});

        Log.e("ROW RESULT UPDATE", ""+newRowId);

        sqLiteDatabase.close();

        if(newRowId > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean updateProductQuantity(Cart modelObject)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CartConstants.FIRST_TABLE_COLUMN_8, modelObject.getItemQuantity());
        values.put(CartConstants.FIRST_TABLE_COLUMN_9,modelObject.getItemTotalPrice());

        long newRowId= sqLiteDatabase.update(CartConstants.TABLE_NUMBER_FIRST_NAME,
                values,
                CartConstants.FIRST_TABLE_COLUMN_4 + " = ?",
                new String[] {String.valueOf(modelObject.getItemId())});

        Log.e("ROW RESULT UPDATE", ""+newRowId);

        sqLiteDatabase.close();

        if(newRowId > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean deleteOperationIntoFirstTableSingleRow(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try
        {
            int rowDeleted = sqLiteDatabase.delete(CartConstants.TABLE_NUMBER_FIRST_NAME,CartConstants.FIRST_TABLE_COLUMN_4 + " =?", new String[] {String.valueOf(id)});
            if(rowDeleted != 0){
                Log.e("DELETE","SUCCESS");
                return true;
            } else {
                Log.e("DELETE","FAILED");
                return false;
            }
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void clearCart()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try
        {
            sqLiteDatabase.execSQL("DELETE FROM " +  CartConstants.TABLE_NUMBER_FIRST_NAME);
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CartConstants.TABLE_NUMBER_FIRST_NAME);
        return numRows;
    }
}
