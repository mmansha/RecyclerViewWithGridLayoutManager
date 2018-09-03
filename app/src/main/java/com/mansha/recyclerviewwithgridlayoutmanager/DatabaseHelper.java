package com.mansha.recyclerviewwithgridlayoutmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    protected static final String DB_NAME = "DadDatabase";
    private static final int DB_VERSION = 1;
//    protected static final String DB_TABLE_CATEGORY1 = "Category1";
    protected static final String COLUMN_CATEGORY_NAME = "CATEGORY_NAME";

    private SQLiteDatabase db;

    DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String db_create_statement = "CREATE TABLE " + DBContract.DB_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.COLUMN_CATEGORY_NAME + ", " +
                DBContract.COLUMN_CAPTION_NAME + ", " +
                DBContract.COLUMN_CAPTION_IMAGE_PATH + ", " +
                DBContract.COLUMN_CAPTION_SOUND_PATH + ");";
        db.execSQL(db_create_statement);
        insertData(db, "People", "Manoj", null, null );
        insertData(db, "People", "Chica", null, null );
    }

    protected static void insertData(SQLiteDatabase db, String categoryName, String captionName, String captionImagePath, String captionSoundPath){
        ContentValues captionValues = new ContentValues();
        captionValues.put(DBContract.COLUMN_CATEGORY_NAME, categoryName);
        captionValues.put(DBContract.COLUMN_CAPTION_NAME, captionName);
        captionValues.put(DBContract.COLUMN_CAPTION_IMAGE_PATH, captionImagePath);
        captionValues.put(DBContract.COLUMN_CAPTION_SOUND_PATH, captionSoundPath);
        db.insert(DBContract.DB_TABLE_NAME, null, captionValues);
    }


    public Cursor getAllRows(SQLiteDatabase db){
        return db.query(DBContract.DB_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public int getRowCount(SQLiteDatabase db){
        Cursor cursor = db.query(DBContract.DB_TABLE_NAME, new String[]{
                    DBContract.COLUMN_CATEGORY_NAME,
                    DBContract.COLUMN_CAPTION_NAME,
                    DBContract.COLUMN_CAPTION_IMAGE_PATH,
                    DBContract.COLUMN_CAPTION_SOUND_PATH
                },
                null, null, null, null, null);
        return  cursor.getCount();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DB_TABLE_NAME);
        onCreate(db);
    }
}
