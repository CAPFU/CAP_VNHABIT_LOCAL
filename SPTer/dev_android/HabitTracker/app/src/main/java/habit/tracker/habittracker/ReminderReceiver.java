package habit.tracker.habittracker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {
    int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        count = count + 1;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("VN Habit Tracker")
                        .setContentText("coount: " + count);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        Toast.makeText(context, "remind on repeat", Toast.LENGTH_LONG).show();
    }
}
