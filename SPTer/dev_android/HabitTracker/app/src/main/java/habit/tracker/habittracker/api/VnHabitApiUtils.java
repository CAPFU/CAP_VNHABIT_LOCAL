package habit.tracker.habittracker.api;

import habit.tracker.habittracker.api.remote.RetrofitClient;
import habit.tracker.habittracker.api.service.VnHabitApiService;

public class VnHabitApiUtils {
    public static final String BASE_URL = "https://rocky-dusk-97160.herokuapp.com/api/";

    public static VnHabitApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(VnHabitApiService.class);
    }
}
