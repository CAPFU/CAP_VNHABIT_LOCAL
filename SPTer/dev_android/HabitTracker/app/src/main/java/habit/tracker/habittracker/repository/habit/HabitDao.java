package habit.tracker.habittracker.repository.habit;

/**
 * Created by DatTVT1 on 10/16/2018
 */
public interface HabitDao {
    boolean saveHabit(HabitEntity habitEntity);
    boolean updateHabit(HabitEntity habitEntity);
    boolean deleteHabit(String habitId);
}
