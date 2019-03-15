package pcube.servey.networkUtils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import pcube.servey.register.UserTypeModel;


public class Constant {

    //public static final String baseURL = "https://beta.fansconnected.io/api/";
    public static final String baseURL = "https://beta.fansconnected.io/api/";
    public static String token="";
    public static String login="http://demo.centerserv.co.in/gameday/api/login";
    public static String questionlist="http://demo.centerserv.co.in/gameday/api/get_questions";
    public static String questionadd="http://demo.centerserv.co.in/gameday/api/questions_survey";
    public static String addedquestionlist="http://demo.centerserv.co.in/gameday/api/added_questions_survey";
    public static String addanswer="http://demo.centerserv.co.in/gameday/api/survey_store_question";
    public static String singlequestionresponse="http://demo.centerserv.co.in/gameday/api/survey_completed_question";
    public static String logout="http://demo.centerserv.co.in/gameday/api/logout";
    public static String resetdata="http://demo.centerserv.co.in/gameday/api/wipe_data";
    public static String register="http://demo.centerserv.co.in/gameday/api/register";
    public static String getuserdata="http://demo.centerserv.co.in/gameday/api/roleslist";




    public static List<UserTypeModel> userTypeModelList;
    public static String ForgotPasswordOtpRequest="http://demo.centerserv.co.in/gameday/api/forgot_password_otp_request";
}
