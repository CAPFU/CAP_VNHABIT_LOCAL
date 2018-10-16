package habit.tracker.habittracker.repository.habit;

/**
 * Created by DatTVT1 on 10/16/2018
 */
public class HabitEntity {
    private String habitId;
    private String userId;
    private String groupId;
    private String monitorId;
    private String habitName;
    private String habitTarget;
    private String habitType;
    private String monitorType;
    private String monitorUnit;
    private String monitorNumber;
    private String startDate;
    private String endDate;
    private String createdDate;
    private String habitColor;
    private String habitDescription;

    public String getHabitId() {
        return habitId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public String getHabitName() {
        return habitName;
    }

    public String getHabitTarget() {
        return habitTarget;
    }

    public String getHabitType() {
        return habitType;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public String getMonitorUnit() {
        return monitorUnit;
    }

    public String getMonitorNumber() {
        return monitorNumber;
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

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public void setHabitTarget(String habitTarget) {
        this.habitTarget = habitTarget;
    }

    public void setHabitType(String habitType) {
        this.habitType = habitType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public void setMonitorUnit(String monitorUnit) {
        this.monitorUnit = monitorUnit;
    }

    public void setMonitorNumber(String monitorNumber) {
        this.monitorNumber = monitorNumber;
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
