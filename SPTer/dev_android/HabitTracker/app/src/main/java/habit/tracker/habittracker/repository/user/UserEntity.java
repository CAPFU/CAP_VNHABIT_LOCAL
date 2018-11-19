package habit.tracker.habittracker.repository.user;

public class UserEntity {
    private String userId;
    private String username;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;
    private String password;
    private String userIcon;
    private String avatar;
    private String userDescription;
    private String createdDate;
    private String userScore;
    private String continueUsingDate;

    public UserEntity(String userId, String username, String email, String phone, String gender, String dateOfBirth,
                      String password, String userIcon, String avatar, String userDescription, String userScore, String continueUsingDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.userIcon = userIcon;
        this.avatar = avatar;
        this.userDescription = userDescription;
        this.userScore = userScore;
        this.continueUsingDate = continueUsingDate;
    }

    public UserEntity(){
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
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
