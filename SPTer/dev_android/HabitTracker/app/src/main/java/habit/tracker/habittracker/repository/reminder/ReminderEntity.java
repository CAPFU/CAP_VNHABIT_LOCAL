package habit.tracker.habittracker.repository.reminder;

public class ReminderEntity {
    String reminderId;
    String reminderGroup;
    String repeatTime;
    String repeatRemain;
    String habitId;

    public String getReminderId() {
        return reminderId;
    }

    public String getReminderGroup() {
        return reminderGroup;
    }

    public String getRepeatTime() {
        return repeatTime;
    }

    public String getRepeatRemain() {
        return repeatRemain;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public void setReminderGroup(String reminderGroup) {
        this.reminderGroup = reminderGroup;
    }

    public void setRepeatTime(String repeatTime) {
        this.repeatTime = repeatTime;
    }

    public void setRepeatRemain(String repeatRemain) {
        this.repeatRemain = repeatRemain;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }
}
