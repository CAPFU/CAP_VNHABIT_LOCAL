package habit.tracker.habittracker.adapter;

public class CalendarItem {
    private String text;
    private String date;
    boolean isSelected;
    boolean isClickable;

    public CalendarItem(String text, String date, boolean isSelected, boolean isClickable) {
        this.text = text;
        this.date = date;
        this.isSelected = isSelected;
        this.isClickable = isClickable;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
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

    public void setDate(String date) {
        this.date = date;
    }
}
