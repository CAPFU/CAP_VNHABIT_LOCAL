package habit.tracker.habittracker.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by DatTVT1 on 10/15/2018
 */
public abstract class DatabaseContentProvider {
    public SQLiteDatabase mDb;


    public long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

    public abstract int delete();

    protected abstract <T> T cursorToEntity(Cursor cursor);

    public DatabaseContentProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder) {

        final Cursor cursor = mDb.query(tableName, columns, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        return mDb.update(tableName, values, selection, selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }
}
