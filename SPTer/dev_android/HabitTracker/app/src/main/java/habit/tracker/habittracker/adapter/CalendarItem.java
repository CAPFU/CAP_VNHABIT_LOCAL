package habit.tracker.habittracker.adapter;

public class CalendarItem {
    private String text;
    boolean isSelected;
    boolean isClickable;

    public CalendarItem(String text, boolean isSelected, boolean isClickable) {
        this.text = text;
        this.isSelected = isSelected;
        this.isClickable = isClickable;
    }

    public String getText() {
        return text;
    }

    public boolean isClickable() {
        return isClickable;
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

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
}
