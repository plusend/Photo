package com.plusend.photo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.Utils;

/**
 * Created by plusend on 16/1/24.
 */
public class PhotoDAO extends SQLiteOpenHelperBaseDAO<Photo> {

    private static final String TAG = "PhotoDAO";
    private static final String TABLE_NAME = "photo";
    private Context mContext;

    public PhotoDAO(Context context) {
        this.mContext = context;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected ContentValues beanToContentValues(Photo entity) {
        if (entity == null)
            return null;
        ContentValues values = new ContentValues();
        values.put("pic", Utils.Bitmap2Bytes(entity.getPic()));
        values.put("note", entity.getNote());
        if (entity.getDate() != null)
            values.put("date", entity.getDate().getTime());
        return values;
    }

    @Override
    protected SQLiteOpenHelper getSQLiteOpenHelper() {
        return DBHelper.getInstance(mContext);
    }

}
