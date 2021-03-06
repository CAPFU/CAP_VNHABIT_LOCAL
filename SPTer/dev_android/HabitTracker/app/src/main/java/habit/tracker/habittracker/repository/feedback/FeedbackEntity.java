package habit.tracker.habittracker.repository.feedback;

public class FeedbackEntity {
    private String feedbackId;
    private String userId;
    private int starNum;
    private String description;
    private boolean isUpdate = false;

    public String getFeedbackId() {
        return feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public int getStarNum() {
        return starNum;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
