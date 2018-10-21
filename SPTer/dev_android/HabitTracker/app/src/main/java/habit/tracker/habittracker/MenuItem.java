package habit.tracker.habittracker;

public class MenuItem {
    private String id;
    private String name;
    private String description;
    private String period;
    private int type;
    private String number;
    private String count;
    private String unit;
    private String color;

    public MenuItem() {}

    public MenuItem(String id, String name, String description, String period, int type, String number, String count, String unit, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.period = period;
        this.type = type;
        this.number = number;
        this.count = count;
        this.unit = unit;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getColor() {
        return color;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setColor(String color) {
        this.color = color;
    }
}
