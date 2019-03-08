package pcube.servey.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import pcube.servey.R;
import pcube.servey.databinding.ActivityLoginBinding;
import pcube.servey.forgot.ForgotPasswordActivity;
import pcube.servey.home.HomeActivity;


public class LoginActivity extends AppCompatActivity implements ILognView {
    LoginPresenter loginPresenter;
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginPresenter=new LoginPresenter(this);
        loginPresenter.doLogin("","");
        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
        activityLoginBinding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

    }

    @Override
    public void showMessage(String msg)
    {
        Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_LONG).show();
    }
}
