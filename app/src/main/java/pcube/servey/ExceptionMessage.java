package pcube.servey;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ExceptionMessage extends Activity implements View.OnClickListener {

    private TextView message;
    private TextView tv1;
    private TextView title;

    private Button btnShow;
    private Button btnRestart, btnShareCrashReport;
    String stackTrace = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            //
            // Initializing Activity
            //
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            //
            this.setContentView(R.layout.activity_exception_message);
            //
            tv1 = findViewById(R.id.tv1);
            title = findViewById(R.id.title);
            message = findViewById(R.id.message);
            title.setText("CRASH");
            stackTrace = getIntent().getStringExtra("stackTrace");
            // DeviceDetails.init(ExceptionMessage.this);
            //
            btnShow = findViewById(R.id.btnShow);
            btnRestart = findViewById(R.id.btnRestart);
            btnShareCrashReport = findViewById(R.id.btnShareCrashReport);
            //
            btnShow.setText("Show details");
            setErrorMessage();
            //
        } catch (Throwable ex) {
            Restart();
        }
    }


    private void Restart() {
//        LogCreator.log(this, getClass(), "Restart Button Clicked");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + 2000,
                PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(getApplicationContext(), SplashActivity.class), 0));
        //
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    private void mailTo() {

        String mailto = "mailto:soganijain1992@gmail.com" +
                "?cc=" + "" +
                "&subject=" + Uri.encode("Crash Report") +
                "&body=" + Uri.encode(stackTrace);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRestart:
                Restart();
                break;
            case R.id.btnShareCrashReport:
                mailTo();
                break;
            case R.id.btnShow:
                if (btnShow.getText().toString().equalsIgnoreCase("Show details")) {
                    btnShow.setText("Hide details");
                    tv1.setVisibility(View.GONE);
                    message.setVisibility(View.VISIBLE);
                } else {
                    btnShow.setText("Show details");
                    tv1.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void setErrorMessage(){
        final StringBuffer report = new StringBuffer();
        final String lineSeperator = "-------------------------------\n\n";
        final String SINGLE_LINE_SEP = "\n";

        report.append("--------- Stack trace ---------\n\n");
        report.append(stackTrace);
        // Getting the Device brand,model and sdk verion details.
        report.append(SINGLE_LINE_SEP);
        report.append("--------- Device ---------\n\n");
        report.append("Brand: ");
        report.append(Build.BRAND);
        report.append(SINGLE_LINE_SEP);
        report.append("Device: ");
        report.append(Build.DEVICE);
        report.append(SINGLE_LINE_SEP);
        report.append("Model: ");
        report.append(Build.MODEL);
        report.append(SINGLE_LINE_SEP);
        report.append("Id: ");
        report.append(Build.ID);
        report.append(SINGLE_LINE_SEP);
        report.append("Product: ");
        report.append(Build.PRODUCT);
        /*report.append(SINGLE_LINE_SEP);
        report.append("Mac Address: ");
        report.append(DeviceDetails.getMAC(ExceptionMessage.this));*/
        report.append(SINGLE_LINE_SEP);
        report.append(SINGLE_LINE_SEP);
        report.append("--------- Firmware ---------\n\n");
        report.append("SDK: ");
        report.append(Build.VERSION.SDK);
        report.append(SINGLE_LINE_SEP);
        report.append("Version: ");
        report.append(Build.VERSION.SDK_INT);
        report.append(SINGLE_LINE_SEP);
        report.append("Version Release: ");
        report.append(Build.VERSION.RELEASE);
       /* report.append(SINGLE_LINE_SEP);
        report.append("--------- Battery ---------\n\n");
        report.append("Battery Percent: ");
        report.append(DeviceDetails.getBatteryPercent());
        report.append(SINGLE_LINE_SEP);
        report.append("Battery Temp: ");
        report.append(DeviceDetails.getBatteryTemperature());
        report.append(SINGLE_LINE_SEP);
        report.append("Battery Voltage: ");
        report.append(DeviceDetails.getBatteryVoltage());
        report.append(SINGLE_LINE_SEP);
        report.append("Is Battery Charging: ");
        report.append(DeviceDetails.isBatteryCharging());*/

        report.append(SINGLE_LINE_SEP);
        report.append(SINGLE_LINE_SEP);

        Log.e("Report ::", report.toString());
        message.setText(report.toString());
    }


}
