package habit.tracker.habittracker.api.model.habit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HabitResult {
    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
