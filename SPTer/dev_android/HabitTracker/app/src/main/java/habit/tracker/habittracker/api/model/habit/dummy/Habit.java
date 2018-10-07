package habit.tracker.habittracker.api.model.habit.dummy;

import java.util.Date;
import java.util.List;

public class Habit {
    private String id;
    private String name;
    private HabitType habitType;
    private TrackType trackType;
    private GoalType goalType;
    private String habitCountUnit;
    private String category;
    private List<Day> trackDay;
    private Date startDate;
    private Date endDate;
    private String color;

    public Habit() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HabitType getHabitType() {
        return habitType;
    }

    public TrackType getTrackType() {
        return trackType;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public String getHabitCountUnit() {
        return habitCountUnit;
    }

    public String getCategory() {
        return category;
    }

    public List<Day> getTrackDay() {
        return trackDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHabitType(HabitType habitType) {
        this.habitType = habitType;
    }

    public void setTrackType(TrackType trackType) {
        this.trackType = trackType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public void setHabitCountUnit(String habitCountUnit) {
        this.habitCountUnit = habitCountUnit;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTrackDay(List<Day> trackDay) {
        this.trackDay = trackDay;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
