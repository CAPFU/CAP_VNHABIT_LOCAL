package habit.tracker.habittracker.repository.group;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import habit.tracker.habittracker.repository.DatabaseHelper;

public class GroupDaoImpl extends DatabaseHelper implements GroupDao {

    public GroupDaoImpl(SQLiteDatabase db){
        super(db);
    }

    @Override
    public GroupEntity getGroup(String id) {
        return null;
    }

    @Override
    public boolean save(GroupEntity groupEntity) {
        return false;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    protected <T> T cursorToEntity(Cursor cursor) {
        return null;
    }
}
