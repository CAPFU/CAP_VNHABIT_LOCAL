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
    @SerializedName("user_icon")
    @Expose
    private String userIcon;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("user_description")
    @Expose
    private String userDescription;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("user_score")
    @Expose
    private String userScore;
    @SerializedName("continue_using_date")
    @Expose
    private String continueUsingDate;

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

    public String getUserIcon() {
        return userIcon;
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

    public String getUserScore() {
        return userScore;
    }

    public String getContinueUsingDate() {
        return continueUsingDate;
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

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
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

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public void setContinueUsingDate(String continueUsingDate) {
        this.continueUsingDate = continueUsingDate;
    }
}
