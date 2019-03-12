package pcube.servey.register;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pcube.servey.R;
import pcube.servey.database.SqliteHelper;
import pcube.servey.database.User;
import pcube.servey.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding=DataBindingUtil.setContentView(this,R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        activityRegisterBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String email = activityRegisterBinding.etEmail.getText().toString();
                String type = activityRegisterBinding.etType.getText().toString();
                String Password = activityRegisterBinding.etConfirmPassword.getText().toString();

                //Check in the database is there any user associated with  this email
//                if (sqliteHelper.isEmailExists(email))
//                {
//                    Snackbar.make( activityRegisterBinding.btnLogin, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
//
//                    //Email does not exist now add new user to database
//
//                }
//                else {

                    //Email exists with email input provided so show error user already exist


                if (!sqliteHelper.isEmailExists(email)) {

                    //Email does not exist now add new user to database
                    sqliteHelper.addUser(new User(null, type, email, Password));
                    Snackbar.make(activityRegisterBinding.btnLogin, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            finish();
                        }
                    }, Snackbar.LENGTH_LONG);
                }else {

                    //Email exists with email input provided so show error user already exist
                    Snackbar.make(activityRegisterBinding.btnLogin, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                }

            }

//            }
        });



    }
}
