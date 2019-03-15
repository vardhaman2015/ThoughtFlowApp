package pcube.servey.register;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pcube.servey.R;
import pcube.servey.database.SqliteHelper;
import pcube.servey.database.User;
import pcube.servey.databinding.ActivityRegisterBinding;
import pcube.servey.networkUtils.Constant;
import pcube.servey.utils.Utils;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {

    ActivityRegisterBinding activityRegisterBinding;
    RegisterPresenter registerPresenter;
  //  List<UserTypeModel> userlist=new ArrayList<UserTypeModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerPresenter=new RegisterPresenter(this,getApplicationContext());
        //registerPresenter.getUserType();

        Log.e("userlist",""+Constant.userTypeModelList.size());

        activityRegisterBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=activityRegisterBinding.etEmail.getText().toString();
                String password=activityRegisterBinding.etPassword.getText().toString();
                registerPresenter.doRegister(email,password,"3");
            }
        });



    }

    @Override
    public void showMessage(String msg) {
        Utils.displayToastMessage(getApplicationContext(),msg);
    }
}
