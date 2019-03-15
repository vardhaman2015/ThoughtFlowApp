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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import pcube.servey.database.SqliteHelper;
import pcube.servey.database.User;
import pcube.servey.databinding.ActivitySplashBinding;
import pcube.servey.home.HomeActivity;
import pcube.servey.login.LoginActivity;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.register.RegisterActivity;
import pcube.servey.register.UserTypeModel;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding activitySplashBinding;
    SqliteHelper sqliteHelper;
    final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding=DataBindingUtil.setContentView(this,R.layout.activity_splash);
      //Log.e("type","hiiii"+StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getApplicationContext()));

setExceptionHandler();
        getUserType();
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

    void getUserType()
    {


        Constant.userTypeModelList= new ArrayList<UserTypeModel>();


        new BackGroundTask.BackGroundTaskGETWithHeaderWithoutProgress(SplashActivity.this, Constant.getuserdata,  "",
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response)
                    {

                        Log.e("responsetesttscheck", response);

                        try {

                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getInt("status") == 1)
                            {
                                JSONArray data=jsonObject1.getJSONArray("data");
                                for(int i=0;i<data.length();i++)
                                {
                                    UserTypeModel userTypeModel=new UserTypeModel(data.getJSONObject(i));
                                    Constant.userTypeModelList.add(userTypeModel);

                                }

                                Log.e("responsetesttscheck", "size>>>"+ Constant.userTypeModelList.size());
                            }
                            else {
                                Utils.displayToastMessage(SplashActivity.this, "error");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error)
                    {
                        Utils.displayToastMessage(SplashActivity.this, "error");

                    }
                }).execute();








        //   return userTypeModelList;
    }

    private void setExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            // android.os.Process.killProcess(android.os.process.myPid());
            StringWriter stackTrace = new StringWriter();
            StackTraceElement[] stackTraces;
            //
//            LogCreator.log(getApplicationContext(), Thread.UncaughtExceptionHandler.class, exception);
            //
            if (exception != null) {
                Throwable t = exception.getCause();
                if (t != null) {
                    stackTraces = t.getStackTrace();
                    if (stackTraces == null)
                        stackTraces = exception.getStackTrace();
                } else
                    stackTraces = exception.getStackTrace();
                //
                if (t != null)
                    t.printStackTrace(new PrintWriter(stackTrace));
                else
                    exception.printStackTrace(new PrintWriter(stackTrace));
            } else
                stackTraces = null;
            //
            //Intent crashedIntent = new Intent(thisActivity, ExceptionMessage.class);
            Intent crashedIntent = new Intent(getApplicationContext(), ExceptionMessage.class);
            crashedIntent.putExtra("stackTrace", stackTrace.toString());
            crashedIntent.putExtra("stackTraces", stackTraces != null ? stackTraces[0] : null);
            crashedIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(crashedIntent);
            //
            // android.os.Process.killProcess(android.os.process.myPid());
            System.exit(0);
        });
    }
}
