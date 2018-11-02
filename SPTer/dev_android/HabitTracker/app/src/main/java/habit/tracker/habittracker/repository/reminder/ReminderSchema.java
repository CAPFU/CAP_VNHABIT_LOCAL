package habit.tracker.habittracker.repository.reminder;

public interface ReminderSchema {
    String REMINDER_TABLE = "reminder";
    String REMINDER_ID = "reminderId";
    String HABIT_ID = "habit_id";
    String REMINDER_TIME = "reminder_hour";
    String REPEAT_TYPE = "repeat_type";
    String SERVICE_ID = "service_id";
    String CREATE_REMINDER_TABLE =
            "CREATE TABLE IF NOT EXISTS " + REMINDER_TABLE + " ("
                    + REMINDER_ID + " TEXT PRIMARY KEY NOT NULL, "
                    + REMINDER_TIME + " TEXT, "
                    + REPEAT_TYPE + " TEXT, "
                    + HABIT_ID + " TEXT "
                    + SERVICE_ID + " INTEGER AUTOINCREMENT NOT NULL"
                    + ")";
    String[] REMINDER_COLUMNS = {REMINDER_ID, HABIT_ID, REMINDER_TIME, REPEAT_TYPE, SERVICE_ID};
}
