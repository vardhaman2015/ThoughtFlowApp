package pcube.servey.register;

import android.content.Context;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pcube.servey.R;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.utils.Utils;

public class RegisterPresenter implements IRegisterPresenter {
    IRegisterView view;
    Context context;


    public RegisterPresenter(IRegisterView view,Context context) {
        this.view = view;
        this.context = context;
    }

//    public List<UserTypeModel> getUserType()


//    public RegisterPresenter(Context context) {
//        this.context = context;
//    }

    @Override
    public void doRegister(String email, String password, String type) {

        if (email.trim().equalsIgnoreCase(""))
        {
            view.showMessage(context.getResources().getString(R.string.emailerror));
        }
        else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

            view.showMessage(context.getResources().getString(R.string.emailerror));

        } else if (password.trim().equalsIgnoreCase("")) {
            view.showMessage(context.getResources().getString(R.string.passworderror));
        } else if (type.trim().equalsIgnoreCase("")) {
            view.showMessage(context.getResources().getString(R.string.typeerror));
        } else {
            getRegister(email,password,type);
       }


    }

    private void getRegister(String email, String password, String type)
    {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);
            jsonObject.put("password",password);
            jsonObject.put("type", "3");
//            jsonObject.put("device_token", refreshedToken);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params", "" + jsonObject.toString());

        new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawDataWithoutProgress(context, Constant.register, "", jsonObject.toString(),
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.e("responsetesttscheck", response);

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getInt("status") == 1) {
                                view.showMessage(jsonObject1.getString("message"));
                                //Utils.displayToastMessage(getApplicationContext(),jsonObject1.getString("message"));

                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//
                            } else {
                                Utils.displayToastMessage(context, "error");
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
