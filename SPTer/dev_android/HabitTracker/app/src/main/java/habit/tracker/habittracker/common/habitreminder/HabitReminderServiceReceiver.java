package habit.tracker.habittracker.common.habitreminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.util.Date;

import habit.tracker.habittracker.LoginActivity;
import habit.tracker.habittracker.R;
import habit.tracker.habittracker.common.util.AppGenerator;
import habit.tracker.habittracker.common.util.MySharedPreference;

public class HabitReminderServiceReceiver extends BroadcastReceiver {

    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String userId = extras.getString(HabitReminderManager.USER_ID);
            String remindId = extras.getString(HabitReminderManager.REMIND_ID);
            String remindText = extras.getString(HabitReminderManager.REMIND_TEXT);
            String remindTitle = extras.getString(HabitReminderManager.REMIND_TITLE);
            String endTime = extras.getString(HabitReminderManager.END_TIME);

            if (remindId == null || remindText == null || remindTitle == null) {
                return;
            }

            if (!TextUtils.isEmpty(endTime)) {
                Date endDate = AppGenerator.getDate(endTime, AppGenerator.YMD);
                if (endDate != null && endDate.compareTo(new Date()) < 0) {
                    Intent i = new Intent(context, HabitReminderServiceReceiver.class);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Integer.parseInt(remindId), i, 0);
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (alarmMgr != null) {
                        alarmMgr.cancel(alarmIntent);
                    }

                    return;
                }
            }

            if (notificationManager == null) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            // Create an explicit intent for an Activity in your app
            Intent appIntent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

            String defaultId = "habittracker";
            Notification notification = null;
            long[] pattern = {100, 200, 300, 400, 500, 400, 300, 200, 400};

            String soundStrUri = MySharedPreference.get(context, userId + "_sound");
            Uri soundUri = null;
            if (soundStrUri != null) {
                soundUri = Uri.parse(soundStrUri);
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();

                NotificationChannel mChannel = notificationManager.getNotificationChannel(defaultId);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(defaultId, remindTitle, NotificationManager.IMPORTANCE_HIGH);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(pattern);
                    if (soundUri != null) {
                        mChannel.setSound(soundUri, att);
                    }
                    notificationManager.createNotificationChannel(mChannel);
                }

                notification = new NotificationCompat.Builder(context, defaultId)
                        .setSmallIcon(R.drawable.app_launcher)
                        .setContentTitle("VN Habit Tracker: " + remindTitle)
                        .setContentText(remindText)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true).build();
            } else {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, defaultId)
                        .setSmallIcon(R.drawable.app_launcher)
                        .setContentTitle("VN Habit Tracker: " + remindTitle)
                        .setVibrate(pattern)
                        .setContentText(remindText);
                if (soundUri != null) {
                    builder.setSound(soundUri);
                }
                builder.setContentIntent(pendingIntent).setAutoCancel(true);
                notification = builder.build();
            }

            notificationManager.notify(0, notification);
        }
    }
}
