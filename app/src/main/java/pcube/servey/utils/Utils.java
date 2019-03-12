package pcube.servey.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pcube.servey.R;


/**
 * Created by ivan.s.petrov on 22.3.2016.
 */
public class Utils {
    private static final String TAG = "Utils";
    static Dialog dialog;
    static Dialog dialog1;
    private static Context context;
    public static String packageName = "travller.pcube.fanconnect";
    public static Boolean ChatOn;

  /*  public static void setPlateQuantityIndicator(Context context, LayerDrawable icon, int quantity) {

        PlateIndicatorDrawable plateIndicator;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_plate_indicator);
        if (reuse != null && reuse instanceof PlateIndicatorDrawable) {
            plateIndicator = (PlateIndicatorDrawable) reuse;
        } else {
            plateIndicator = new PlateIndicatorDrawable(context);
        }

        plateIndicator.setCount(quantity);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_plate_indicator, plateIndicator);
    }*/



    public static boolean isValidEmail(String email)
    {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static String getPreviousDate(int numberOfDaysBack) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -numberOfDaysBack);

        return dateFormat.format(cal.getTime());
    }

    /**
     * Rounds a double value.
     *
     * @param number        the number that should be rounded
     * @param decimalPlaces the number of decimal places that are taken into consideration
     *                      when rounding
     * @return a rounded double value
     */
    public static double roundDouble(double number, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal numberToRound = new BigDecimal(number);
        numberToRound = numberToRound.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return numberToRound.doubleValue();
    }

    /**
     * Displays a warning toast.
     *
     * @param message the message to be displayed
     */
    public static void displayWarningToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.color.colorAccent);
        toast.show();
    }

    /**
     * Display a warning toast.
     * <p>
     * The method should be called from background threads for
     * displaying warning messages.
     *
     * @param context an Activity context
     * @param message
     */
    public static void displayWarningToastFromBackgroundThread(final Context context, final String message) {
        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.color.colorAccent);
                    toast.show();
                }
            });
        } catch (ClassCastException ex) {
            Log.e(TAG, "The given context could not be cast to Activity context", ex);
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }




    /* My Utility Functions */
    public static void displayToastMessage(final Context context, final String message) {
//        Toast.makeText(context, message, duration).show();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static int randomcolor( )
    {
        Random rnd = new Random();
        int color =Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;

    }
    public static String checkStringNull(String text) {

        if (text.trim().length() == 0 || text.trim().equalsIgnoreCase("null") || text.isEmpty()) {
            return "Not Available";
        }
        return text;
    }

    public static String resetNullString(String text) {

        if (text.trim().length() == 0 || text.trim().equalsIgnoreCase("null")) {
            return "";
        }
        return text;
    }

    public static String resetEmptyString(String text)
    {

        if (text.trim().length() == 0) {
            return "0";
        }

        return text;
    }

    public static String capitalizeFirstLetter(String capString)
    {
        capString = capString.substring(0, 1).toUpperCase() + capString.substring(1).toLowerCase();
        return capString;
    }

    public static String capitalizeFirstLetterOfWords(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static String checkSharedPrefsNull(Context context, String text) {
        String prefs = StorePrefs.getDefaults(text, context);

        if (prefs != null) {
            return prefs;
        }
        return "";
    }

    @SuppressWarnings("deprecation")
    public static String fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString().trim();
        } else {
            return Html.fromHtml(source).toString().trim();
        }
    }

    public static String dateFormatWithTime(String date) {

        String formattedString = "";

        if (date.trim().length() == 0 || date.trim().equalsIgnoreCase("null")) {
            return "Not Available";
        }

        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");
        Date date1 = null;

        try {
            date1 = readFormat.parse(date);
            formattedString = formatter.format(date1);

        }
        catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

        return formattedString;
    }

    public static String dateFormatToPost(String date) {

        String formattedString = "";

        if (date.trim().length() == 0 || date.trim().equalsIgnoreCase("null")) {
            return "Not Available";
        }

        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;

        try {
            date1 = readFormat.parse(date);
            formattedString = formatter.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }

        return formattedString;
    }

    public static Date stringToDateFormat(String date) {

        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;

        try {
            date1 = readFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // return date;
        }

        return date1;
    }

    public static String dateFormatToString(Date date) {

        String formattedString = "";

        if (date == null) {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedString = formatter.format(date);
        return formattedString;
    }

    public static Calendar yearMonthDayFromDate(String date) {

      /*  String dateFromDB = "";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date yourDate = parser.parse(date);*/

        String formattedString = "";
        DateFormat readFormat = new SimpleDateFormat("dd-M-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date1 = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date1 = readFormat.parse(date);
            formattedString = formatter.format(date1);
            date1 = formatter.parse(formattedString);
            calendar.setTime(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            calendar.setTime(new Date());
            return calendar;
        }

        return calendar;
    }

    public static String ageCalculator(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static boolean isTablet(Context context)
    {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null)
        {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }





    public static String convertPoundsToKg(String pounds) {

        if (pounds.trim().length() == 0 || pounds.trim().equalsIgnoreCase("null")) {
            return "";
        }

        // m(kg) = m(lb) × 0.45359237
        double weightInKg = Integer.parseInt(pounds) * 0.45359237;
        return String.valueOf(weightInKg);
    }

    public static String convertInchesToCm(String inches) {
        if (inches.trim().length() == 0 || inches.trim().equalsIgnoreCase("null")) {
            return "";
        }
        String inch = feetToInchString(inches);
        // cmHeight = inHeight * 2.54;
        double heightInCm = Double.parseDouble(inch) * 2.54;
        return String.valueOf(heightInCm);
    }

    /*public static boolean checkMaxCalorieConsumption(Context context, String calorie) {

        try {
            String mConsumed = resetEmptyString(resetNullString(calorie));
            String mTotalCalories = resetEmptyString(Utils.resetEmptyString(Utils.checkStringNull(StorePrefs.getDefaults(StorePrefs.PREFS_USER_CALORIES, context))));

            double dConsumed = Double.parseDouble(mConsumed);
            double dTotal = Double.parseDouble(mTotalCalories);

            double mPercent = dTotal * 0.05;

            if (dConsumed > mPercent) {
                return false;
            }

            Log.d(TAG, "PERCENTAGE ------> " + mPercent);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }*/

    public static float getPercentageString(String consumed, String total) {

        String mConsumed = resetEmptyString(resetNullString(consumed));
        String mTotal = resetEmptyString(resetNullString(total));

        double dConsumed = Double.parseDouble(mConsumed);
        double dTotal = Double.parseDouble(mTotal);
        double res = (dConsumed / dTotal) * 100;

        if(dConsumed > dTotal)
            return  100f;

        Log.d(TAG, "PERCENTAGE ------> " + res);
        return Float.parseFloat(String.valueOf(res));
    }

    public static String feetToInchString(String strFeet) {
        if (strFeet.trim().length() == 0 || strFeet.trim().equalsIgnoreCase("null")) {
            return strFeet;
        }



        double measurement = Double.parseDouble(strFeet);
        Log.d(TAG, "measurement -------->" + measurement);
       /* int feet = (int)measurement;
        Log.d(TAG, "feet -------->" + feet);
        double fraction = measurement - feet;
        Log.d(TAG, "fraction -------->" + fraction);
        int inches = (int)(12.0 * fraction);
        Log.d(TAG, "INCH -------->" + inches);*/
       // return String.valueOf(inches);
        double inches = measurement  *	12.0;
        Log.d(TAG, "INCH -------->" + inches);

        return String.valueOf(inches);
    }

    public static String getTimeCalculate(String created_at)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = created_at;
            Date eventDate = dateFormat.parse(endDate);
            Date currentDate = new Date();

//                if (currentDate.after(eventDate))
//                {
            long diff =  currentDate.getTime()-eventDate.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            diff -= days * (24 * 60 * 60 * 1000);
            long hours = diff / (60 * 60 * 1000);
            diff -= hours * (60 * 60 * 1000);
            long minutes = diff / (60 * 1000);
            diff -= minutes * (60 * 1000);
            long seconds = diff / 1000;
            long years=0;
            long month=0;
            Log.e("Timecalc","hour"+hours+"day"+days+"month"+month+"yr"+years);
            if(days>30)
            {
                month=month+1;

            }
            if(month>12)
            {
                years=years+1;
            }
            // holder.tv_time.setText("Published before " + days + " D : "+ hours+" H : " + minutes + " M : " + seconds +" S ");
            if(years==0 && month==0 && days==0)
            {
                // holder.tv_time.setText("Published before " + hours+" hours");
                return "" + hours+" hours";
            }
            else if(years==0 && month==0)
            {
                //holder.tv_time.setText("Published before " + days+" days");
                return "" + days+" days";
            }
            else if(years==0)
            {
                //holder.tv_time.setText("Published before " + month+" month");
                return "" + month+" month";
            }
            else
                {
                //  holder.tv_time.setText("Published before " + years+" year");
                return "" + years+" year";
            }


            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return created_at;
    }



    public static String nullCheckFunction(String text){
        String value = "";
        if (text.trim().length() == 0 || text.trim().equalsIgnoreCase("null") || TextUtils.isEmpty(text) ||
                text.isEmpty() || TextUtils.equals(text ,"null") || text == null){
            return "";
        }

        try{

            value = text;

        }catch (Exception e){
            e.printStackTrace();
            return text;
        }

        return value;
    }
//    static public void dialog(Context context, String message) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(context.getResources().getString(R.string.app_name));
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

//    static public void dialogAction(Context context, String message, final DialogListener listener) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(context.getResources().getString(R.string.app_name));
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                listener.onClick();
//            }
//        });
//        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//               // listener.onClick();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
//    }
//
//    public static void showDialogAction(final Context context, String msg, final View.OnClickListener listener) {
//        dialog = new Dialog(context);
//        dialog.setCancelable(false);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // dialog.setTitle("Please select an Event !");
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//        int screenWidth = display.getWidth();
//        // int screenHeight = display.getHeight();
//
//        dialog.setContentView(R.layout.dialog_msgshow);
//
//        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
//        TextView btnOK = (TextView) dialog.findViewById(R.id.btn_submit);
//        TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
//
//        tv_msg.setText(msg);
//
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ///listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//        dialog.getWindow().setLayout((int) (screenWidth / 1.1), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//    }
//    public static void showDialogActionpayout(final Context context, String msg, final View.OnClickListener listener) {
//        dialog = new Dialog(context);
//        dialog.setCancelable(false);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // dialog.setTitle("Please select an Event !");
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//        int screenWidth = display.getWidth();
//        // int screenHeight = display.getHeight();
//
//        dialog.setContentView(R.layout.dialog_msgshow);
//
//        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
//        TextView btnOK = (TextView) dialog.findViewById(R.id.btn_submit);
//        TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
//
//        tv_msg.setText(msg);
//
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        btn_cancel.setVisibility(View.GONE);
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ///listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//        dialog.getWindow().setLayout((int) (screenWidth / 1.1), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//    }
//    public static void displayInternetdialog(AppCompatActivity context1, String msg, final View.OnClickListener listener) {
//        dialog1 = new Dialog(((AppCompatActivity)context1));
//        dialog1.setCancelable(false);
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // dialog.setTitle("Please select an Event !");
//        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Display display = ((Activity) context1).getWindowManager().getDefaultDisplay();
//        int screenWidth = display.getWidth();
//        // int screenHeight = display.getHeight();
//
//        dialog1.setContentView(R.layout.dialog_msgshow);
//
//
//        TextView btnOK = (TextView) dialog1.findViewById(R.id.btn_submit);
//        TextView btn_cancel = (TextView) dialog1.findViewById(R.id.btn_cancel);
//
//
//
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(v);
//                dialog1.dismiss();
//            }
//        });
//
//        dialog1.show();
//        dialog1.getWindow().setLayout((int) (screenWidth / 1.1), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//    }
//    public static Date shiftTimeZone(Date date, TimeZone sourceTimeZone, TimeZone targetTimeZone) {
//        Calendar sourceCalendar = Calendar.getInstance();
//        sourceCalendar.setTime(date);
//        sourceCalendar.setTimeZone(sourceTimeZone);
//
//        Calendar targetCalendar = Calendar.getInstance();
//        for (int field : new int[] {Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
//            targetCalendar.set(field, sourceCalendar.get(field));
//        }
//        targetCalendar.setTimeZone(targetTimeZone);
//        System.out.println("........"+targetCalendar.getTimeZone());
//        return targetCalendar.getTime();
//    }
//
//    public static String dateFormatEarningToPost(String date) {
//        String formattedString = "";
//        if (date.trim().length() == 0 || date.trim().equalsIgnoreCase("null")) {
//            return "Not Available";
//        }
//
//        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd");
//        Date date1 = null;
//        try {
//            date1 = readFormat.parse(date);
//            formattedString = formatter.format(date1);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return date;
//        }
//
//        return formattedString;
//    }
//
//
//    public static String dateFormatEarningToPost1(String date) {
//        String formattedString = "";
//        if (date.trim().length() == 0 || date.trim().equalsIgnoreCase("null")) {
//            return "Not Available";
//        }
//
//        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//        Date date1 = null;
//        try {
//            date1 = readFormat.parse(date);
//            formattedString = formatter.format(date1);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return date;
//        }
//
//        return formattedString;
//    }
//    public static void generateFacebookKeyhash(Context mContext)
//    {
//        try {
//            PackageInfo info = mContext.getPackageManager().getPackageInfo(
//                    packageName,
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//    }
//
//    public static String html2text(String html)
//    {
//        return Jsoup.parse(html).text();
//    }


    public static void showSoftKeyboard(View view,Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                   context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
