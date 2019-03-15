package pcube.servey.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import pcube.servey.R;
import pcube.servey.database.SqliteHelper;
import pcube.servey.databinding.ActivityLoginBinding;
import pcube.servey.forgot.ForgotPasswordActivity;
import pcube.servey.home.HomeActivity;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.register.RegisterActivity;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;


public class LoginActivity extends AppCompatActivity implements ILognView {
    LoginPresenter loginPresenter;
    ActivityLoginBinding activityLoginBinding;
    SqliteHelper sqliteHelper;
    String refreshedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginPresenter=new LoginPresenter(this);
        sqliteHelper = new SqliteHelper(this);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Constant.token = refreshedToken;
        activityLoginBinding.btnCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // loginPresenter.doLogin(activityLoginBinding.etEmail.getText().toString().trim(),activityLoginBinding.etPassword.getText().toString().trim());

                String Email = activityLoginBinding.etEmail.getText().toString().trim();
                String Password = activityLoginBinding.etPassword.getText().toString().trim();


                //Authenticate user
//                User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));
//
//                //Check Authentication is successful or not
//                if (currentUser != null)
//                {
//                    Snackbar.make(activityLoginBinding.btnLogin, "Successfully Logged in!"+currentUser.userName, Snackbar.LENGTH_LONG).show();
//
//
//                      StorePrefs.setDefaults(StorePrefs.PREFS_USER_TYPE,currentUser.userName,getApplicationContext());
//                 //     StorePrefs.setDefaults(StorePrefs.,currentUser.email,getApplicationContext());
//                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//
//                    //User Logged in Successfully Launch You home screen activity
//                       /* Intent intent=new Intent(LoginActivity.this,HomeScreenActivity.class);
//                        startActivity(intent);
//                        finish();*/
//                }
//                else {
//
//                    //User Logged in Failed
//                    Snackbar.make(activityLoginBinding.btnLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
//
//                }
                if (Email.equals(""))
                {
                    Utils.displayToastMessage(getApplicationContext(),"Please fill ");
                }
                else if (Password.equals(""))
                {
                    Utils.displayToastMessage(getApplicationContext(),"Please fill ");
                }
                else {
                    JSONObject jsonObject = new JSONObject();

                    try {

                        jsonObject.put("email", Email);
                        jsonObject.put("password", Password);
                        jsonObject.put("device_type", "android");
                        jsonObject.put("device_token", refreshedToken);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("params",""+jsonObject.toString());

                    new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(LoginActivity.this, Constant.login, "", jsonObject.toString(),
                            new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String response) {
                                    Log.e("responsetesttscheck", response);

                                    try {
                                        JSONObject jsonObject1=new JSONObject(response);
                                        if(jsonObject1.getInt("status")==1)
                                        {
                                            Utils.displayToastMessage(getApplicationContext(),jsonObject1.getString("message"));
                                            StorePrefs.setDefaults(StorePrefs.PREFS_USER_TYPE,jsonObject1.getJSONObject("data").getJSONObject("userdata").getString("role_id"),getApplicationContext());
                                            StorePrefs.setDefaults(StorePrefs.PREFS_USER_ID,jsonObject1.getJSONObject("data").getJSONObject("userdata").getString("id"),getApplicationContext());
                                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//
                                        }
                                        else {
                                            Utils.displayToastMessage(getApplicationContext(),"error");
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            }).execute();
                }

            }
        });
        activityLoginBinding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });

    }

    @Override
    public void showMessage(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
