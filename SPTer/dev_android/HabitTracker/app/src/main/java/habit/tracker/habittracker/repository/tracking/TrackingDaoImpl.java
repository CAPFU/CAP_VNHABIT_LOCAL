package habit.tracker.habittracker.repository.tracking;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import habit.tracker.habittracker.repository.DatabaseHelper;

public class TrackingDaoImpl extends DatabaseHelper implements TrackingDao, TrackingSchema {
    private Cursor cursor;
    private ContentValues initialValues;

    public TrackingDaoImpl(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    protected TrackingEntity cursorToEntity(Cursor cursor) {
        TrackingEntity entity = new TrackingEntity();
        if (cursor != null) {
            if (cursor.getColumnIndex(TRACKING_ID) != 1) {
                entity.setTrackingId(cursor.getString(cursor.getColumnIndexOrThrow(TRACKING_ID)));
            }
            if (cursor.getColumnIndex(HABIT_ID) != 1) {
                entity.setHabitId(cursor.getString(cursor.getColumnIndexOrThrow(HABIT_ID)));
            }
            if (cursor.getColumnIndex(CURRENT_DATE) != 1) {
                entity.setCurrentDate(cursor.getString(cursor.getColumnIndexOrThrow(CURRENT_DATE)));
            }
            if (cursor.getColumnIndex(COUNT) != 1) {
                entity.setCount(cursor.getString(cursor.getColumnIndexOrThrow(COUNT)));
            }
            if (cursor.getColumnIndex(TRACKING_DESCRIPTION) != 1) {
                entity.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TRACKING_DESCRIPTION)));
            }
        }
        return entity;
    }

    @Override
    public TrackingEntity getTracking(String trackID) {
        final String selectionArgs[] = {String.valueOf(trackID)};
        final String selection = TRACKING_ID + " = ?";
        TrackingEntity entity = new TrackingEntity();
        cursor = super.query(TRACKING_TABLE, TRACKING_COLUMNS, selection, selectionArgs, trackID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entity = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return entity;
    }

    @Override
    public boolean saveTracking(TrackingEntity entity) {
        setContentValue(entity);
        try {
            return super.replace(TRACKING_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteTracking(TrackingEntity entity) {
        return false;
    }

    @Override
    public boolean updateTracking(TrackingEntity entity) {
        final String selectionArgs[] = {String.valueOf(entity.getTrackingId())};
        final String selection = TRACKING_ID + " = ?";
        setContentValue(entity);
        try {
            return super.update(TRACKING_TABLE, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex) {
            return false;
        }
    }

    private void setContentValue(TrackingEntity entity) {
        initialValues = new ContentValues();
        initialValues.put(TRACKING_ID, entity.getTrackingId());
        initialValues.put(HABIT_ID, entity.getHabitId());
        initialValues.put(CURRENT_DATE, entity.getCurrentDate());
        initialValues.put(COUNT, entity.getCount());
        initialValues.put(TRACKING_DESCRIPTION, entity.getDescription());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}
