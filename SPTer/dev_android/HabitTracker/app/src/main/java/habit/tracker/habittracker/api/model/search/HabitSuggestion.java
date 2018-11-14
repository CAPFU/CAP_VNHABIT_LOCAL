package habit.tracker.habittracker.api.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HabitSuggestion {
    @SerializedName("habit_name_id")
    @Expose
    private String habitNameId;
    @SerializedName("habit_name_uni")
    @Expose
    private String habit_name_uni;
    @SerializedName("habit_name")
    @Expose
    private String habitName;
    @SerializedName("habit_name_count")
    @Expose
    private String habitNameCount;
    private String group;
    private boolean isHeader = false;

    public HabitSuggestion(String group, boolean isHeader) {
        this.group = group;
        this.isHeader = isHeader;
    }

    public HabitSuggestion(String habitNameId, String habit_name_uni, String habitName, String habitNameCount, boolean isHeader) {
        this.habitNameId = habitNameId;
        this.habit_name_uni = habit_name_uni;
        this.habitName = habitName;
        this.habitNameCount = habitNameCount;
        this.isHeader = isHeader;
    }

    public String getHabitNameId() {
        return habitNameId;
    }

    public String getHabitNameUni() {
        return habit_name_uni;
    }

    public String getHabitName() {
        return habitName;
    }

    public String getHabitNameCount() {
        return habitNameCount;
    }

    public String getGroup() {
        return group;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHabitNameId(String habitNameId) {
        this.habitNameId = habitNameId;
    }

    public void setHabitNameUni(String habit_name_uni) {
        this.habit_name_uni = habit_name_uni;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setHabitNameCount(String habitNameCount) {
        this.habitNameCount = habitNameCount;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
