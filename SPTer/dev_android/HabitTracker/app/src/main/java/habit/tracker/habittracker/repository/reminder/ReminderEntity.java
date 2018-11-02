package habit.tracker.habittracker.repository.reminder;

public class ReminderEntity {
    private String reminderId;
    private String habitId;
    private String reminderTime;
    private String repeatType;
    private int serverId;

    public String getReminderId() {
        return reminderId;
    }

    public String getHabitId() {
        return habitId;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public int getServerId() {
        return serverId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
