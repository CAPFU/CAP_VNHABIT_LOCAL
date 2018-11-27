package habit.tracker.habittracker.repository.group;

public interface GroupSchema {
    String GROUP_TABLE = "group_tb";
    String GROUP_ID = "group_id";
    String GROUP_NAME = "group_name";
    String GROUP_DESCRIPTION = "group_description";
    String IS_DELETE = "is_delete";
    String IS_LOCAL = "is_local";
    String CREATE_GROUP_TABLE =
            "CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " ("
                    + GROUP_ID + " TEXT PRIMARY KEY NOT NULL, "
                    + GROUP_NAME + " TEXT, "
                    + GROUP_DESCRIPTION + " TEXT, "
                    + IS_DELETE + " TEXT, "
                    + IS_LOCAL + " TEXT"
                    + ")";
    String[] GROUP_COLUMNS = {GROUP_ID, GROUP_NAME, GROUP_DESCRIPTION, IS_DELETE, IS_LOCAL};
}
