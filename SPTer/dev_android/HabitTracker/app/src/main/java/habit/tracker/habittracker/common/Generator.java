package habit.tracker.habittracker.common;

import java.util.UUID;

public class Generator {

    public static String getNewId() {
        String id =  UUID.randomUUID().toString();
        if (id.length() > 11) {
            return id.substring(0, 11);
        }
        return id;
    }
}
