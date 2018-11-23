package habit.tracker.habittracker.api.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("user_description")
    @Expose
    private String userDescription;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("last_login_time")
    @Expose
    private String lastLoginTime;
    @SerializedName("continue_using_count")
    @Expose
    private String continueUsingCount;
    @SerializedName("current_continue_using_count")
    @Expose
    private String currentContinueUsingCount;
    @SerializedName("best_continue_using_count")
    @Expose
    private String bestContinueUsingCount;
    @SerializedName("user_score")
    @Expose
    private String userScore;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getRealName() {
        return realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getContinueUsingCount() {
        return continueUsingCount;
    }

    public String getCurrentContinueUsingCount() {
        return currentContinueUsingCount;
    }

    public String getBestContinueUsingCount() {
        return bestContinueUsingCount;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setContinueUsingCount(String continueUsingDate) {
        this.continueUsingCount = continueUsingDate;
    }

    public void setCurrentContinueUsingCount(String currentContinueUsingCount) {
        this.currentContinueUsingCount = currentContinueUsingCount;
    }

    public void setBestContinueUsingCount(String bestContinueUsingCount) {
        this.bestContinueUsingCount = bestContinueUsingCount;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }
}
