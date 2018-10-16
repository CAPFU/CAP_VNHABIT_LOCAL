package habit.tracker.habittracker.repository;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import habit.tracker.habittracker.repository.habit.HabitDaoImpl;
import habit.tracker.habittracker.repository.habit.HabitSchema;
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
    public static HabitDaoImpl sHabitDaoImpl;

    public Database(Context context) {
        this.mContext = context;
    }

    public Database open() throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        sUserDaoImpl = new UserDaoImpl(db);
        sHabitDaoImpl = new HabitDaoImpl(db);
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public static class DatabaseHelper extends SQLiteOpenHelper implements UserSchema, HabitSchema {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_HABIT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + HABIT_TABLE);
            onCreate(db);
        }
    }
}