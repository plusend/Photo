package com.plusend.photo.db.handler;

import android.database.Cursor;
import android.database.SQLException;

import com.plusend.photo.model.Photo;
import com.plusend.photo.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by plusend on 16/2/2.
 */
public class PhotoCursorHandler implements CursorHandler<Photo> {
    @Override
    public List<Photo> handle(Cursor cursor) throws SQLException {
        List<Photo> pList = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            Photo photo = new Photo();
            photo.setPic(Utils.Bytes2Bimap(cursor.getBlob(cursor
                    .getColumnIndex("pic"))));
            photo.setLevel(cursor.getInt(cursor
                    .getColumnIndex("level")));
            photo.setNote(cursor.getString(cursor
                    .getColumnIndex("note")));
            photo.setDate(new Date(cursor.getLong(cursor
                    .getColumnIndex("date"))));
            pList.add(photo);
        }
        return pList;
    }
}
