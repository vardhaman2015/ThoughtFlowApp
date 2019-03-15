package pcube.servey.home;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;


import pcube.servey.NavgationDrawerUtils.FragmentDrawer;
import pcube.servey.R;
import pcube.servey.networkUtils.Constant;
import pcube.servey.utils.StorePrefs;


public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

        boolean doubleBackToExitPressedOnce = false;

        public DrawerLayout drawer;
        private FragmentDrawer drawerFragment;
        public Toolbar mToolbar;
        public Fragment fragment;
        Context context;
        String clickLayout = "";
        String message = "Fragment_Dashboard";

        private ActionBarDrawerToggle mDrawerToggle;
        private ActionBar mActionBar;
        private boolean mToolBarNavigationListenerIsRegistered = false;
        Dialog dialog;
        Button btnOk;
        TextView tvDisclaimerDesc;
        LinearLayout LLHeader;

        public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;

        private NotificationManager notificationManager;
        private NotificationCompat.Builder notificationBuilder;
        private String notificationTitle;
        private String notificationText;
        private Bitmap icon;
        private int currentNotificationID = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);

                mToolbar = findViewById(R.id.toolbar);
                drawer = findViewById(R.id.drawer_layout);
//                LLHeader = findViewById(R.id.LLHeader);
//                LLHeader.setClickable(true);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                setSupportActionBar(mToolbar);
                mActionBar = getSupportActionBar();

                mDrawerToggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(mDrawerToggle);
                mDrawerToggle.syncState();
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                dialog = new Dialog(getApplicationContext());

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Constant.token = refreshedToken;
                //Log.e("token",refreshedToken);
//        SSLCertificateHandler.init();


                //
        /*notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_shree_jung_rodhak);
        setDataForSimpleNotification();*/


/*
        if (!hasPermissions(this, PERMISSIONS))
        {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);*/


                drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
                drawerFragment.setUp(R.id.fragment_navigation_drawer, drawer, mToolbar);
                drawerFragment.setDrawerListener(this);
//                Log.e("usertype",StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getApplicationContext()));

//        fragment = new Fragment_Dashboard();
//        // fragment = new Dashboard1();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container_body, fragment).commit();
//
//        //Initializing our broadcast receiver
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//
////When the broadcast received
//
//@Override
//public void onReceive(Context context, Intent intent) {
//
//        // checking for type intent filter
//        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//        // gcm successfully registered
//        // now subscribe to `global` topic to receive app wide notifications
//        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//        displayFirebaseRegId();
//
//        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//        // new push notification is received
//                   /* String message = intent.getStringExtra("message");
//                    String type = intent.getStringExtra("type");
//                     Toast.makeText(getApplicationContext(), "Note : " + message, Toast.LENGTH_LONG).show();*/
//
//        }
//        }
//        };

     /*   // Check if Android M or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }*/

        }


        @Override
        public void onBackPressed() {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                }
                else {

                        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

                        if (backStackEntryCount > 0) {
                                getFragmentManager().popBackStackImmediate();

                                if (backStackEntryCount == 1)
                                {
                                        showUpButton(false);
                                        super.onBackPressed();
                                }
                                else
                                        super.onBackPressed();

                        }
                        else {
                                if (doubleBackToExitPressedOnce) {
                                        super.onBackPressed();
                                        return;
                                }

                                this.doubleBackToExitPressedOnce = true;
                                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                                doubleBackToExitPressedOnce = false;
                                        }
                                }, 2000);

                        }

                }
        }


        //Registering receiver on activity resume
        @Override
        protected void onResume() {
                super.onResume();
                Log.w("MainActivity", "onResume");

                // register GCM registration complete receiver
        }

        //Unregister receiver on activity paused
        @Override
        protected void onPause() {

                super.onPause();
        }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cp, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        switch (myActionMenuItem.getItemId()) {
            case R.id.changePassword:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.container_body, new Fragment_ChangePassword()).commit();
        }

        return true;
    }*/

        public void onDrawerItemSelected(View view, int position) {
//        displayView(position);
        }


        @Override
        protected void onDestroy() {
                super.onDestroy();

        }

        public void closeDrawer() {
                drawer.closeDrawer(GravityCompat.START);
        }

        public void showUpButton(boolean show) {
                // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
                // when you enable on one, you disable on the other.
                // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
                if (show) {
                        // Remove hamburger
                        mDrawerToggle.setDrawerIndicatorEnabled(false);
                        // Show back button
                        mActionBar.setDisplayHomeAsUpEnabled(true);
                        // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
                        // clicks are disabled i.e. the UP button will not work.
                        // We need to add a listener, as in below, so DrawerToggle will forward
                        // click events to this listener.
                        if (!mToolBarNavigationListenerIsRegistered) {
                                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                onBackPressed();
                                        }
                                });

                                mToolBarNavigationListenerIsRegistered = true;
                        }

                } else {
                        // Remove back button
                        mActionBar.setDisplayHomeAsUpEnabled(false);
                        // Show hamburger
                        mDrawerToggle.setDrawerIndicatorEnabled(true);
                        // Remove the/any drawer toggle listener
                        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        drawer.openDrawer(GravityCompat.START);
                                }
                        });
                        mToolBarNavigationListenerIsRegistered = false;
                }

                // So, one may think "Hmm why not simplify to:
                // .....
                // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
                // mDrawer.setDrawerIndicatorEnabled(!enable);
                // ......
                // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
        }
}
