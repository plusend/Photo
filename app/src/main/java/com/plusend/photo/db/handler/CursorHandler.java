package com.plusend.photo.db.handler;

import android.database.Cursor;
import android.database.SQLException;

import java.util.List;

/**
 * Convert a Cursor row into a JavaBean.
 * The cursor close in DAO Class,so you don't need to close the cursor
 *
 * @param <T>
 */
public interface CursorHandler<T> {

    //Convert a Cursor row into a JavaBean.
    List<T> handle(Cursor cursor) throws SQLException;
}
