package pcube.servey.forgot;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import pcube.servey.R;
import pcube.servey.databinding.ActivityForgotPasswordBinding;
import pcube.servey.home.HomeActivity;
import pcube.servey.login.LoginActivity;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.otp.OtpVerificationActivity;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding activityForgotPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        activityForgotPasswordBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=activityForgotPasswordBinding.etEmail.getText().toString();
                if (email.trim().equalsIgnoreCase(""))
                {
                    Utils.displayToastMessage(getApplicationContext(),getResources().getString(R.string.emailerror));
                }
                else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                    Utils.displayToastMessage(getApplicationContext(),getResources().getString(R.string.emailerror));

                }
                else {
                    UpdateForgotPassword(email);
                }

            }
        });
    }

    private void UpdateForgotPassword(String email) {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",""+jsonObject.toString());

        new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(ForgotPasswordActivity.this, Constant.ForgotPasswordOtpRequest, "", jsonObject.toString(),
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.e("responsetesttscheck", response);

                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            if(jsonObject1.getInt("status")==1)
                            {
                                Utils.displayToastMessage(getApplicationContext(),jsonObject1.getString("message"));
                                 JSONObject data=jsonObject1.getJSONObject("data");
                                 String otp=data.optString("otp");
                                 startActivity(new Intent(getApplicationContext(), OtpVerificationActivity.class));

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
