package habit.tracker.habittracker.repository.reminder;

import java.util.List;

public interface ReminderDao {
    boolean saveUpdateReminder(ReminderEntity entity);
    List<ReminderEntity> getRemindersByHabit(String habitId);
}
