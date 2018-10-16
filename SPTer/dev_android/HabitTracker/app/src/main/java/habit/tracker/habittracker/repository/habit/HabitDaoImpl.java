package habit.tracker.habittracker.repository.habit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import habit.tracker.habittracker.api.model.habit.Habit;
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

    public HabitEntity convert(Habit habit) {
        if (habit != null) {
            HabitEntity entity = new HabitEntity();
            entity.setHabitId(habit.getHabitId());
            entity.setUserId(habit.getUserId());
            entity.setGroupId(habit.getGroupId());
            entity.setMonitorId(habit.getMonitorId());
            entity.setHabitName(habit.getHabitName());
            entity.setHabitTarget(habit.getHabitTarget());
            entity.setHabitType(habit.getHabitType());
            entity.setMonitorType(habit.getMonitorType());
            entity.setMonitorUnit(habit.getMonitorUnit());
            entity.setMonitorNumber(habit.getMonitorNumber());
            entity.setStartDate(habit.getStartDate());
            entity.setEndDate(habit.getEndDate());
            entity.setHabitColor(habit.getHabitColor());
            entity.setHabitDescription(habit.getHabitDescription());
            return entity;
        }
        return null;
    }
}
