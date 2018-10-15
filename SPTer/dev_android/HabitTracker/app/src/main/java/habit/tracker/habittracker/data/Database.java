package habit.tracker.habittracker.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import habit.tracker.habittracker.repository.user.UserDaoImpl;
import habit.tracker.habittracker.repository.user.UserSchema;

/**
 * Created by DatTVT1 on 10/12/2018
 */
public class Database {

    private static final String DATABASE_NAME = "vnhabit.db";
    private static final int DATABASE_VERSION = 3;
    private DatabaseHelper dbHelper;
    private final Context mContext;

    public static UserDaoImpl sUserDaoImpl;

    public Database(Context context) {
        this.mContext = context;
    }

    public Database open() throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        sUserDaoImpl = new UserDaoImpl(db);
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public static class DatabaseHelper extends SQLiteOpenHelper implements UserSchema {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            onCreate(db);
        }
    }
}
