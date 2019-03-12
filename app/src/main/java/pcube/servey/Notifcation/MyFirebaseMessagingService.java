package pcube.servey.Notifcation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import pcube.servey.R;
import pcube.servey.home.HomeActivity;
import pcube.servey.utils.StorePrefs;


/**
 * Created by wakeel on 8/1/17.
 */


public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String title = "";
    String message = "";
    private NotificationUtils notificationUtils;
    public AlertDialog.Builder dialogBuilder;
    public AlertDialog builder3;
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.e("kamesh", "----a----" + remoteMessage.getData());
        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        Log.e("kamesh", "----11----" + object.toString());
        Log.e("kamesh", "----type_id----" + remoteMessage.getData().get("type_id"));
        Log.e("kamesh", "----title----" + remoteMessage.getData().get("body"));
        Log.e("kamesh", "----sound----" + remoteMessage.getData().get("type"));
        Log.e("kamesh", "----sound----" + remoteMessage.getData().get("sound"));
        Log.e("kamesh", "----message----" + remoteMessage.getData().get("message"));

        if (remoteMessage == null)
        {
            return;
        }
        else if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Data Payload11111: " + remoteMessage.getData());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                Log.e("kamesh", "------2--->" + json.toString());
                handleDataMessage(json);
            } catch (Exception e)
            {
                Log.e(TAG, "Exception:" + e.getMessage());

            }
        }


    }



    private void handleDataMessage(JSONObject json)
    {

        try {
            JSONObject data = new JSONObject(json.toString());
            Log.e("kamesh", "------4--->" + data.toString());
            String message = data.getString("message");
            String title = data.getString("title");
            String type = data.getString("type");
            createLocalNotification(getApplicationContext(),title,message,type);

            } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }




    private void createLocalNotification(Context context, String title, String message,String type) {
        String CHANNEL_ID = "P3Channel";
        String CHANNEL_NAME = "P3";
        Log.e("kamesh", "------5--->" +title);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Log.e("kamesh", "------6--->" +title);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("P3 App");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            try {
                notificationManager.createNotificationChannel(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Create an explicit intent for an Activity in your app

        Log.e("kamesh", "------7--->" +title);
        intent = new Intent(context, HomeActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.splash_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.build();
        // Issue the notification.
        int id =(int) System.currentTimeMillis();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, notification);
        Log.e("kamesh", "------8--->" +title);

//        if(StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getApplicationContext()).equals("2"))
//        {
//            if(StorePrefs.getDefaults(StorePrefs.PREFS_ENABLESOUND,getApplicationContext()).equalsIgnoreCase("off"))
//            {
//                Log.e("testsound","offf");
//                final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSounddefault();
//            }
//            else
//                {
//                Log.e("testsound","onnn");
//                if (type.equalsIgnoreCase("Tip") || type.equalsIgnoreCase("subscribed") || type.equalsIgnoreCase("chat_paid") || type.equalsIgnoreCase("renewal_subscription"))
//                {
//                    Log.e("kamesh", "------type--->" + type.toString());
//                    final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                    notificationUtils.playNotificationSound();
//                }
//                else
//                    {
//
//                    Log.e("kamesh", "------type1--->" + type.toString());
//                        final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                    notificationUtils.playNotificationSounddefault();
//                }
//            }
//        }
//
//        else
//        {
//            if(StorePrefs.getDefaults(StorePrefs.PREFS_ENABLESOUND,getApplicationContext()).equalsIgnoreCase("off"))
//            {
//                final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSounddefault();
//            }
//            else
//                {
//                final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSounddefault();
//            }
//        }

        final AudioManager mode = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE); mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSounddefault();



    }
}