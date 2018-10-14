package habit.tracker.habittracker.repository.user;

public interface UserDao {
    UserEntity getUser(int userId);
    UserEntity getUSer(String username, String password);
    boolean saveUser(UserEntity userEntity);
    boolean deleteUser(String userId);
}
