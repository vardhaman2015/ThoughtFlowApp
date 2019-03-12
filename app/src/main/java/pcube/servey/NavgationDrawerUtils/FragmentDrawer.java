package pcube.servey.NavgationDrawerUtils;

/**
 * Created by  on 12/28/2016.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pcube.servey.R;


public class FragmentDrawer extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private String[] titles = null;
    private FragmentDrawerListener drawerListener;
    private ImageView prof_img;
    private TextView tv_NavUsername, tvAppVersion;
    String App_Version = "";


    RelativeLayout loadingPanel;

    String imageUrl;

    InputMethodManager inputMethodManager;


    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;

    }

    public List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();



        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_openservylist),
                getActivity().getResources().getDrawable(R.drawable.open_survey),"openservy"));


        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_postquestion),
                getActivity().getResources().getDrawable(R.drawable.post_question),"postquestion"));
//        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_postquestion),
//                getActivity().getResources().getDrawable(R.drawable.check_icon),"postquestion"));
        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_notification),
                getActivity().getResources().getDrawable(R.drawable.notification),"notification"));

        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_response),
                getActivity().getResources().getDrawable(R.drawable.reply),"response"));
        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_setting),
                getActivity().getResources().getDrawable(R.drawable.reply),"setting"));
        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_help),
                getActivity().getResources().getDrawable(R.drawable.help),"help"));
        data.add(new NavDrawerItem(getActivity().getResources().getString(R.string.nav_logout),
                getActivity().getResources().getDrawable(R.drawable.reply),"logout"));


        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
      /*  titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);

        List<String> list = new ArrayList<>(Arrays.asList(titles));*/

        inputMethodManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

       /* Gson gson = new Gson();
        String json = StorePrefs.getDefaults("customersPermission", getActivity());
        BeanPermission customersPermission = gson.fromJson(json, BeanPermission.class);

        if (customersPermission.getPermission_view().equals("0"))
            list.remove("Customers");

        json = StorePrefs.getDefaults("receiptsPermission", getActivity());
        BeanPermission receiptsPermission = gson.fromJson(json, BeanPermission.class);

        if (receiptsPermission.getPermission_view().equals("0"))
            list.remove("Material Receipt Entry");

        json = StorePrefs.getDefaults("ordersPermission", getActivity());
        BeanPermission ordersPermission = gson.fromJson(json, BeanPermission.class);

        if (ordersPermission.getPermission_view().equals("0"))
            list.remove("Order Requirement Entry");

        titles = list.toArray(new String[0]);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = layout.findViewById(R.id.drawerList);
        tvAppVersion = layout.findViewById(R.id.tvAppVersion);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // SSLCertificateHandler.init();

       // imageUrl = baseUrlForImage + StorePrefs.getDefaults("IMAGE", getActivity())+"?token="+StorePrefs.getDefaults("token", getActivity());


       //   aniimageView =  layout.findViewById(R.id.aniimageView);
        loadingPanel =  layout.findViewById(R.id.loadingPanel);


        //


        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            App_Version = pInfo.versionName;
            Log.d("App_Version", "========" + App_Version);

            tvAppVersion.setText("v." + App_Version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        tv_NavUsername = (TextView) layout.findViewById(R.id.tv_NavUsername);



        //latest one
     /*   Glide.with(getActivity())
                 .load(imageUrl)
               // .load(HeadersClass.getUrlWithHeaders(baseUrlForImage + StorePrefs.getDefaults("IMAGE", getActivity())))
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(prof_img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        prof_img.setImageDrawable(circularBitmapDrawable);
                    }
                });*/





          /* Glide.with(getActivity())
                // .load(baseUrlForImage + voterId)
                .load(HeadersClass.getUrlWithHeaders(baseUrlForImage + StorePrefs.getDefaults("IMAGE", getActivity())))
                .thumbnail(1.0f)
                .placeholder(R.drawable.profile_icon).dontAnimate()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .crossFade()
                .centerCrop()
                .into(prof_img);*/


      /*  if (StorePrefs.getDefaults("IMAGE", getActivity()) != null) {

            Log.e("In part", "In image part");
            Glide.with(this)
                   // .load(baseUrlForImage + StorePrefs.getDefaults("IMAGE", getActivity()))
                    .load(HeadersClass.getUrlWithHeaders(baseUrlForImage + StorePrefs.getDefaults("IMAGE", getActivity())))
                    .asBitmap()
                    .error(R.drawable.profile_icon)
                    .centerCrop()
                    .into(new BitmapImageViewTarget(prof_img) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            prof_img.setImageDrawable(circularBitmapDrawable);
                        }
                    });




        }*/



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(view, position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                try{
                    //showDialogImage();
                    //showImageWithGLide();
                }
                catch (Exception e ){

                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);


                /*try{
                    showDialogImage();}
                catch (Exception e ){

                }*/



            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);


                try{
                    //showDialogImage();
                    //showImageWithGLide();
                }
                catch (Exception e ){

                }



            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    private boolean userPermissionExists(String strJsonArray, String permissionToFind){
        //return strJsonArray.contains(permissionToFind);
        return strJsonArray.toString().contains(permissionToFind);
    }

    private static boolean userPermissionExistsForDealers(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }



/*    public void showImageWithGLide(){
        GlideApp.with(getActivity())
                .load(imageUrl)
                .placeholder(R.drawable.profile_icon)
                .transform(new CircleCrop())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(prof_img);
    }*/

}
