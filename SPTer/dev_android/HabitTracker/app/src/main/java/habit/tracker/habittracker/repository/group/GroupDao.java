package habit.tracker.habittracker.repository.group;

public interface GroupDao {
    GroupEntity getGroup(String id);
    boolean save(GroupEntity groupEntity);
}
