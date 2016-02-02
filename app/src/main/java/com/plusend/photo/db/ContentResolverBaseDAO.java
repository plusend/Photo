package com.plusend.photo.db;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.plusend.photo.db.handler.CursorHandler;

/**
 * 针对使用 ContentResolver 实现CRUD操作 接口基类
 */
public abstract class ContentResolverBaseDAO<T> {

	public Uri insert(Uri url, T entity) {

		return getContentResolver().insert(url, beanToContentValues(entity));
	}

	public int batch(Uri url, List<T> list) {

		if (list == null || list.size() < 1) {

			return 0;
		}
		int result = 0;
		for (T t : list) {
			insert(url, t);
		}

		return result;
	}

	public int delete(Uri url, String where, String[] selectionArgs) {

		return getContentResolver().delete(url, where, selectionArgs);
	}

	public int update(Uri uri, T entity, String where, String[] selectionArgs) {

		return getContentResolver().update(uri, beanToContentValues(entity), where, selectionArgs);
	}

	public <D> List<D> query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			CursorHandler<D> handler) {

		Cursor cursor = null;
		try {
			cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
			return handler != null ? handler.handle(cursor) : null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public boolean find(Uri uri, String[] projection, String selection, String[] selectionArgs) {

		return count(uri, projection, selection, selectionArgs) > 0;
	}

	public int count(Uri uri, String[] projection, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		try {
			cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null) {
				return cursor.getCount();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return 0;
	}

	protected abstract ContentValues beanToContentValues(T entity);

	protected abstract ContentResolver getContentResolver();

}
