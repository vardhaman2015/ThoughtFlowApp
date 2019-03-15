package pcube.servey.NavgationDrawerUtils;

/*
 * Created by Chinmay on 12/28/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pcube.servey.R;
import pcube.servey.SplashActivity;
import pcube.servey.answerquestion.Fragment_AnswerQuestion;
import pcube.servey.answerquestion.Fragment_Response;
import pcube.servey.help.Fragment_Help;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.postquestion.Fragment_PostQuestion;
import pcube.servey.setting.Fragment_Setting;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavDrawerItem> data;
    private LayoutInflater inflater;
    private Context context;
    private int currentPosition = 0;
    Fragment fragment;
    Button btnOk;
    TextView tvDisclaimerDesc;
    String UserType = "";

    NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());


        holder.navImage.setImageDrawable(current.getThumbnail());


        if (position == currentPosition) {
            holder.TopLayout.setBackgroundResource(R.drawable.item_pressed);

        }
        else {
            holder.TopLayout.setBackgroundResource(R.drawable.item_normal);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView navImage;
        LinearLayout TopLayout;



        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            navImage = itemView.findViewById(R.id.navImage);
            TopLayout = itemView.findViewById(R.id.TopLayout);
            title.setOnClickListener(this);
            TopLayout.setOnClickListener(this);
            navImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            currentPosition = getLayoutPosition();
            Log.d("currentPosition", "=======" + currentPosition);
            Log.d("position", "=======" + getLayoutPosition());
            //Reloading
            notifyDataSetChanged();

            fragment = null;
            String slug = data.get(currentPosition).getSlug();

            switch (slug) {

                case "openservy":
                    if (!StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,context).equalsIgnoreCase("3"))
                    {
                        fragment = new Fragment_AnswerQuestion();
                    }
                    else
                        {
                        Toast.makeText(context,"Can't view ",Toast.LENGTH_SHORT).show();
                    }


                    break;
                case "postquestion":
                    if (StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,context).equalsIgnoreCase("3"))
                    {
                        fragment = new Fragment_PostQuestion();
                    }
                    else {
                        Toast.makeText(context,"Can't add ",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case "setting":
                    fragment = new Fragment_Setting();
                    break;
                case "help":
                    fragment = new Fragment_Help();
                    break;
                case "response":
                    if (StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,context).equalsIgnoreCase("3"))
                    {
                        fragment = new Fragment_Response();
                    }
                    else
                        {
                        Toast.makeText(context,"Can't view ",Toast.LENGTH_SHORT).show();
                    }

                    //fragment = new Fragment_Dealer_retailer_Locater_list();
                    break;

                case "materialTransaction":
                   // fragment = new Fragment_Material_Transaction();
                    break;
                case "orders":
                   // fragment = new Fragment_Orders();
                    break;
                case "receipts":
                   // fragment = new Fragment_Receipts();
                    break;

                case "referral":
                  //  fragment = new Fragment_Referral();
                    //fragment = new Fragment_Referrals_list();
                    break;
                case "events":
                   // fragment = new Fragment_Events();
                    break;
                case "conversionFormulas":
                 //   fragment = new Fragment_Conversion_Formulas();
                    break;





                case "logout":

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.app_name));
                builder.setMessage("Dou you want to logout?");
                builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which)
                    {
                        JSONObject jsonObject = new JSONObject();

                        try {


                            jsonObject.put("user_id", StorePrefs.getDefaults(StorePrefs.PREFS_USER_ID, context));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("params", "12333" + jsonObject.toString());

                        new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(context, Constant.logout, "", jsonObject.toString(),
                                new OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String response)
                                    {
                                        Log.e("responsetesttscheck", response);

                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            if (jsonObject1.getInt("status") == 1)
                                            {
                                                Utils.displayToastMessage(context, jsonObject1.getString("message"));
                                                dialog.dismiss();
                                                StorePrefs.clearAllDefaults(context);
                                                Intent intent = new Intent(context, SplashActivity.class);
                                                context.startActivity(intent);
                                            }
                                            else {
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


                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                    //logout();
                    break;

                case "reset":

                    if (StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,context).equalsIgnoreCase("3"))
                    {
                        new BackGroundTask.BackGroundTaskGETWithHeader(context, Constant.resetdata,  "",
                                new OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String response)
                                    {
                                        Log.e("responsetesttscheck", response);

                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            if (jsonObject1.getInt("status") == 1)
                                            {
                                                Utils.displayToastMessage(context, jsonObject1.getString("message"));

                                                StorePrefs.clearAllDefaults(context);
                                                Intent intent = new Intent(context, SplashActivity.class);
                                                context.startActivity(intent);
                                            }
                                            else {
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
                    else {
                        Toast.makeText(context,"Can't reset ",Toast.LENGTH_SHORT).show();
                    }


                    break;
                default:
                    break;
            }

            if (fragment != null) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                                R.anim.enter_from_right, R.anim.exit_to_left);
                        fragmentTransaction.replace(R.id.container_body, fragment).commit();

                    }
                }, 275);
            }
        }
    }

//    public void logout() {
//        new android.support.v7.app.AlertDialog.Builder(context)
//                .setMessage("Are you sure you want to Logout?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        StorePrefs.clearDefaults("LoggedIn", context);
//                        StorePrefs.clearDefaults("regId", context);
//
//                        Intent intent = new Intent(context, MobileInputActivity.class);
//                        context.startActivity(intent);
//                        ((FragmentActivity) context).finish();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }

//    public void showDialog(Context activity, String msg) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.fragment_disclaimer);
//
//        Spanned htmlAsSpanned = Html.fromHtml(msg); // used by TextView
//
//
//        tvDisclaimerDesc = dialog.findViewById(R.id.tvDisclaimerDesc);
//        tvDisclaimerDesc.setText(htmlAsSpanned);
//        btnOk = dialog.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
}
