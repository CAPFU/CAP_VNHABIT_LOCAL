package habit.tracker.habittracker.repository.user;

public class UserSchema {
    public static final String TABLE_NAME = "user";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String GENDER = "gender";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String PASSWORD = "password";
    public static final String USER_ICON = "user_icon";
    public static final String AVATAR = "avatar";
    public static final String USER_DESCRIPTION = "user_description";

    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USERNAME + " TEXT,"
                    + EMAIL + " TEXT,"
                    + PHONE + " TEXT,"
                    + GENDER + " TEXT,"
                    + DATE_OF_BIRTH + " TEXT,"
                    + PASSWORD + " TEXT,"
                    + USER_ICON + " TEXT,"
                    + AVATAR + " TEXT,"
                    + USER_DESCRIPTION + " TEXT"
                    + ")";
}
