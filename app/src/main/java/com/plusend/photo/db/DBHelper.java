package com.plusend.photo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.plusend.photo.utils.log.Logger;

/**
 * ---------------------------------------------------------------
 * Describe:
 * 本类用于数据库的建立和更新
 * ---------------------------------------------------------------
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";
    /**
     * 数据库的名称
     */
    public static final String DB_NAME = "photo";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    public static DBHelper sDBHelper;

    private final String DB_DIR_PATH;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        DB_DIR_PATH = context.getFilesDir().getPath() + "/databases/";
        Logger.d(TAG, "DB_DIR_PATH : " + DB_DIR_PATH);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sDBHelper == null)
            sDBHelper = new DBHelper(context);
        return sDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPhoto = "CREATE TABLE photo (pic blob,note text,level integer,date integer)";
        db.execSQL(sqlPhoto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
