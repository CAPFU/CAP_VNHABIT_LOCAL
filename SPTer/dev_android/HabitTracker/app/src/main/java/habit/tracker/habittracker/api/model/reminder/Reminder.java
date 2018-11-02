package habit.tracker.habittracker.api.model.reminder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reminder {
    @SerializedName("reminder_id")
    @Expose
    private String reminderId;
    @SerializedName("remind_text")
    @Expose
    private String remindText;
    @SerializedName("reminder_time")
    @Expose
    private String reminderTime;
    @SerializedName("repeat_type")
    @Expose
    private String repeatType;
    @SerializedName("habit_id")
    @Expose
    private String habitId;
    private int serverId;

    public String getReminderId() {
        return reminderId;
    }

    public String getRemindText() {
        return remindText;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public String getHabitId() {
        return habitId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public void setRemindText(String remindText) {
        this.remindText = remindText;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
