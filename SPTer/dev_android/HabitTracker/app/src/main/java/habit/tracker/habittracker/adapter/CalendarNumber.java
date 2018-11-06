package habit.tracker.habittracker.adapter;

public class CalendarNumber {
    private String text;
    boolean isSelected = false;

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
