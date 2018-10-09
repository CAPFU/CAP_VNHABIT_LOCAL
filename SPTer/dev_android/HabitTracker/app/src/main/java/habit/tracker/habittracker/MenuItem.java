package habit.tracker.habittracker;

public class MenuItem {
    private String category;
    private String description;
    private String period;
    private int type;
    private String number;
    private String count;
    private String unit;
    private int color;

    public MenuItem() {}

    public MenuItem(String category, String description, String period, int type, String number, String count, String unit, int color) {
        this.category = category;
        this.description = description;
        this.period = period;
        this.type = type;
        this.number = number;
        this.count = count;
        this.unit = unit;
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPeriod() {
        return period;
    }

    public int getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getCount() {
        return count;
    }

    public String getUnit() {
        return unit;
    }

    public int getColor() {
        return color;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
