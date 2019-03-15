package pcube.servey.otp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pcube.servey.R;
import pcube.servey.databinding.ActivityForgotPasswordBinding;

public class OtpVerificationActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding activityForgotPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_otp_verification);
        //activityForgotPasswordBinding.btnSubmit
    }
}
