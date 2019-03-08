package pcube.servey;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pcube.servey.databinding.ActivitySplashBinding;
import pcube.servey.login.LoginActivity;
import pcube.servey.register.RegisterActivity;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activitySplashBinding=DataBindingUtil.setContentView(this,R.layout.activity_splash);
        activitySplashBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        activitySplashBinding.btnCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}
