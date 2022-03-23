package com.example.randomwordgenerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper
{
    private Context context;
    private static final String DATABASE_NAME = "zodynas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME1 =     "LietuviuKalbosZodynas";
    private static final String TABLE_NAME2 =     "Zargonas";
    private static final String TABLE_NAME3 =     "TarptautinisZodynas";

    private final int TABLE_SIZE1 = 73612;
    private final int TABLE_SIZE2 = 6376;
    private final int TABLE_SIZE3 = 21901;

    private static final String COLUMN_ID =      "_id";
    private static final String COLUMN_PHRASE =  "phrase";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query1 = "CREATE TABLE " + TABLE_NAME1 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PHRASE + " TEXT);";
        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHRASE + " TEXT);";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + TABLE_NAME3 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHRASE + " TEXT);";
        db.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        //        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        //        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        //        onCreate(db);
    }


    public String getRandomWord(int table_id, int word_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        if(table_id == 0)
            c = db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_ID + " = " + word_id, null);
        else if(table_id == 1)
            c = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_ID + " = " + word_id, null);
        else if(table_id == 2)
            c = db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE " + COLUMN_ID + " = " + word_id, null);

        if(c == null)
            return "-1";

        c.moveToFirst();
        String word = c.getString(1);
        c.close();
        return word;
    }


    public int getTableSize(int table_id)
    {
        if(table_id == 0) return TABLE_SIZE1;
        if(table_id == 1) return TABLE_SIZE2;
        if(table_id == 2) return TABLE_SIZE3;
        return 0;
    }


}
