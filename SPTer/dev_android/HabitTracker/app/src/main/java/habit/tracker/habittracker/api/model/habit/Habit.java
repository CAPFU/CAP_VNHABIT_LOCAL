package habit.tracker.habittracker.api.model.habit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Habit {

    @SerializedName("habit_id")
    @Expose
    private String habitId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("catagory_id")
    @Expose
    private String catagoryId;
    @SerializedName("habit_name")
    @Expose
    private String habitName;
    @SerializedName("habit_type")
    @Expose
    private String habitType;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("goal_number")
    @Expose
    private String goalNumber;
    @SerializedName("goal_time")
    @Expose
    private String goalTime;
    @SerializedName("count_type")
    @Expose
    private String countType;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("habit_color")
    @Expose
    private String habitColor;
    @SerializedName("habit_description")
    @Expose
    private String habitDescription;

    public String getHabitId() {
        return habitId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCatagoryId() {
        return catagoryId;
    }

    public String getHabitName() {
        return habitName;
    }

    public String getHabitType() {
        return habitType;
    }

    public String getUnit() {
        return unit;
    }

    public String getGoalNumber() {
        return goalNumber;
    }

    public String getGoalTime() {
        return goalTime;
    }

    public String getCountType() {
        return countType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getHabitColor() {
        return habitColor;
    }

    public String getHabitDescription() {
        return habitDescription;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCatagoryId(String catagoryId) {
        this.catagoryId = catagoryId;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setGoalNumber(String goalNumber) {
        this.goalNumber = goalNumber;
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setHabitColor(String habitColor) {
        this.habitColor = habitColor;
    }

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }
}
