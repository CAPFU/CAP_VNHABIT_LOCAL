package habit.tracker.habittracker.api.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("user")
    @Expose
    private User user;

    public UserResponse(String result, User user) {
        this.result = result;
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
