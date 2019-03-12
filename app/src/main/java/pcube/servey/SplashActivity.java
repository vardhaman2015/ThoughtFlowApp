package pcube.servey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

import pcube.servey.database.SqliteHelper;
import pcube.servey.database.User;
import pcube.servey.databinding.ActivitySplashBinding;
import pcube.servey.home.HomeActivity;
import pcube.servey.login.LoginActivity;
import pcube.servey.networkUtils.Constant;
import pcube.servey.register.RegisterActivity;
import pcube.servey.utils.StorePrefs;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding activitySplashBinding;
    SqliteHelper sqliteHelper;
    final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding=DataBindingUtil.setContentView(this,R.layout.activity_splash);
      //Log.e("type","hiiii"+StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getApplicationContext()));


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Constant.token = refreshedToken;
//        Log.e("token",refreshedToken);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true))
        {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            sqliteHelper=new SqliteHelper(this);
            sqliteHelper.addUser(new User(null, "L1", "user1l1", "123456"));
            sqliteHelper.addUser(new User(null, "L2", "user122", "123456"));
            sqliteHelper.addUser(new User(null, "L2", "user123", "123456"));
            sqliteHelper.addUser(new User(null, "L3", "user134", "123456"));
            sqliteHelper.addUser(new User(null, "L3", "user135", "123456"));

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getApplicationContext())==null)
                {

                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
                else
                    {
//           activitySplashBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//               }
//           });
//           activitySplashBinding.btnCreateaccount.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
//               }
//           });
                }


            }
        }, 2000);

    }
}
