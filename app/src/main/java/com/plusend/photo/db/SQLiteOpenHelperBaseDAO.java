package com.plusend.photo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.plusend.photo.db.handler.CursorHandler;

import java.util.List;

/**
 * 针对使用 SQLiteOpenHelper 实现CRUD操作 接口基类
 */
public abstract class SQLiteOpenHelperBaseDAO<T> {

    private DatabaseManager mDatabaseManager;

    public long insert(T entity) {

        long result = -1;
        if (mDatabaseManager == null)
            mDatabaseManager = DatabaseManager.getInstance(getSQLiteOpenHelper());
        try {
            result = mDatabaseManager.getWritableDatabase().insert(getTableName(), null, beanToContentValues(entity));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }
        return result;
    }

    public int batch(List<T> list) {

        if (list == null || list.size() < 1) {
            return 0;
        }
        int result = 0;
        long id = 0;
        for (T t : list) {
            id = insert(t);
            if (id > 0) {
                result++;
            }
        }

        return result;
    }

    public int delete(String whereClause, String[] whereArgs) {

        int result = 0;
        if (mDatabaseManager == null)
            mDatabaseManager = DatabaseManager.getInstance(getSQLiteOpenHelper());
        try {
            result = mDatabaseManager.getWritableDatabase().delete(getTableName(), whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }
        return result;
    }

    public int update(T entity, String whereClause, String[] whereArgs) {

        int result = -1;
        if (mDatabaseManager == null)
            mDatabaseManager = DatabaseManager.getInstance(getSQLiteOpenHelper());
        try {
            result = mDatabaseManager.getWritableDatabase().update(getTableName(), beanToContentValues(entity),
                    whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDatabaseManager.closeDatabase();
        }
        return result;

    }

    public <D> List<D> query(String[] columns, String selection, String[] selectionArgs, CursorHandler<D> handler) {

        List<D> d = null;
        Cursor cursor = null;
        if (mDatabaseManager == null)
            mDatabaseManager = DatabaseManager.getInstance(getSQLiteOpenHelper());
        try {
            cursor = mDatabaseManager.getReadableDatabase().query(getTableName(), columns, selection,
                    selectionArgs, null, null, "date asc");
            d = handler != null ? handler.handle(cursor) : null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mDatabaseManager.closeDatabase();
        }
        return d;
    }

    public boolean find(String[] columns, String selection, String[] selectionArgs) {

        return count(columns, selection, selectionArgs) > 0;
    }

    public int count(String[] columns, String selection, String[] selectionArgs) {
        int result = 0;
        Cursor cursor = null;
        if (mDatabaseManager == null)
            mDatabaseManager = DatabaseManager.getInstance(getSQLiteOpenHelper());
        try {
            cursor = mDatabaseManager.getReadableDatabase().query(getTableName(), columns, selection,
                    selectionArgs, null, null, null);
            if (cursor != null) {
                result = cursor.getCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            mDatabaseManager.closeDatabase();
        }

        return result;
    }

    protected abstract String getTableName();

    protected abstract ContentValues beanToContentValues(T entity);

    protected abstract SQLiteOpenHelper getSQLiteOpenHelper();
}
