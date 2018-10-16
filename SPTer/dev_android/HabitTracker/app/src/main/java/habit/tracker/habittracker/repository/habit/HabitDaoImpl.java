package habit.tracker.habittracker.repository.habit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import habit.tracker.habittracker.repository.DatabaseContentProvider;

/**
 * Created by DatTVT1 on 10/16/2018
 */
public class HabitDaoImpl extends DatabaseContentProvider implements HabitDao, HabitSchema {

    public HabitDaoImpl(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public boolean saveHabit(HabitEntity habitEntity) {
        return false;
    }

    @Override
    public boolean updateHabit(HabitEntity habitEntity) {
        return false;
    }

    @Override
    public boolean deleteHabit(String habitId) {
        return false;
    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    protected <T> T cursorToEntity(Cursor cursor) {
        return null;
    }
}
