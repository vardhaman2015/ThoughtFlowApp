package pcube.servey.Notifcation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import pcube.servey.R;
import pcube.servey.SplashActivity;


public class PushedMessageProcessor {

    public static void process(Context context, String message)
    {
        try {
            JSONObject json = new JSONObject(message);
            createLocalNotification(context, json.getString("body"));

        }
        catch (JSONException e) {
            createLocalNotification(context, "hii");
            e.printStackTrace();
        }
    }
        private static void createLocalNotification(Context context, String title) {
        String CHANNEL_ID = "P3Channel";
        String CHANNEL_NAME = "P3";
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("P3 App");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            try {
                notificationManager.createNotificationChannel(channel);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.splash_icon)
                .setContentTitle(title)
                .setContentText(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Click here to open"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();
        // Issue the notification.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
//        TasksActivity.msgCount++;
    }

}
