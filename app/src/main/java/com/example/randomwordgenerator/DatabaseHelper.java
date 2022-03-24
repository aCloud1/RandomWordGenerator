package com.example.randomwordgenerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    private SQLiteDatabase db;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        File db_folder = new File(context.getApplicationInfo().dataDir + "/databases");
        if(!db_folder.exists())
            db_folder.mkdir();

        File database = new File(context.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME);

        InputStream is;
        OutputStream os;
        try
        {
            is = context.getAssets().open(DATABASE_NAME);
            os = new FileOutputStream(database);
            byte[] buffer = new byte[1024];
            int length;

            while(true)
            {
                length = is.read(buffer);
                if(length <= 0)
                    break;
                os.write(buffer, 0, length);
            }

            os.flush();
            os.close();
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.db = this.getReadableDatabase();

    }

    @Override public void onCreate(SQLiteDatabase db) {}
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public String getRandomWord(int table_id, int word_id)
    {
        Cursor c = null;
        if(table_id == 0)
            c = this.db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE rowid = " + word_id, null);
        else if(table_id == 1)
            c = this.db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE rowid = " + word_id, null);
        else if(table_id == 2)
            c = this.db.rawQuery("SELECT * FROM " + TABLE_NAME3 + " WHERE rowid = " + word_id, null);

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
