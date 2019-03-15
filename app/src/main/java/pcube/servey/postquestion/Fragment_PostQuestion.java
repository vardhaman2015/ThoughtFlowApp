package pcube.servey.postquestion;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pcube.servey.R;
import pcube.servey.database.PostQuestionPost;
import pcube.servey.database.SqliteHelper;
import pcube.servey.database.User;
import pcube.servey.home.HomeActivity;
import pcube.servey.login.LoginActivity;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;

public class Fragment_PostQuestion extends Fragment {

    ArrayList<PostQuestion>postQuestionArrayList=new ArrayList<>();
    LinearLayoutManager layoutManager;
    AskMeAdapter askMeAdapter;
    RecyclerView rv_question;
    SqliteHelper sqliteHelper;
    String question_id="",question_type="";
    MediaPlayer mPlayer2;
    boolean audio=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postquestion, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Post Question");
        mPlayer2 = MediaPlayer.create(getActivity(), R.raw.audioquestion);
      //  sqliteHelper = new SqliteHelper(getContext());
//        PostQuestion postQuestion=new PostQuestion("1","Which area is your primary focus area for the next year on your personal front","Health","Money","Security","Social Management","Health","text");
//        postQuestionArrayList.add(postQuestion);
//        PostQuestion postQuestion2=new PostQuestion("2","If you make profits, where will you invest your money in the coming quarters","Stocks","Real Estate","Govt Bonds","Social Management","Bank","video");
//        postQuestionArrayList.add(postQuestion2);
//
//        PostQuestion postQuestion3=new PostQuestion("3","How often do you like notifications on key updates delivered by our organization on projects","1 days","3 days","7 days","30 days","1 days","audio");
//        postQuestionArrayList.add(postQuestion3);
         rv_question=view.findViewById(R.id.rv_question);



         //Log.e("ddff",postQuestionArrayList.toString()+">>>>>"+postQuestionArrayList.size());



        setquestion();


        return view;
    }

    private void setquestion()
    {
        new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(getActivity(), Constant.questionlist, "", "",
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.e("responsetesttscheck", response);

                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            if(jsonObject1.getInt("status")==1)
                            {
                                JSONArray jsonArray=jsonObject1.getJSONArray("data");
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    PostQuestion postQuestion=new PostQuestion(jsonArray.getJSONObject(i).getString("id"),
                                            jsonArray.getJSONObject(i).getString("question"), jsonArray.getJSONObject(i).getString("a"), jsonArray.getJSONObject(i).getString("b"), jsonArray.getJSONObject(i).getString("c"), jsonArray.getJSONObject(i).getString("d"), "",jsonArray.getJSONObject(i).getString("type"));
                                    postQuestionArrayList.add(postQuestion);
                                }
                                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                rv_question.setLayoutManager(layoutManager);
                                askMeAdapter = new AskMeAdapter(getActivity(),postQuestionArrayList);
                                rv_question.setAdapter(askMeAdapter);
                                askMeAdapter.notifyDataSetChanged();

                            }
                            else {
                                Utils.displayToastMessage(getContext(),"error");
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

    private class AskMeAdapter extends RecyclerView.Adapter<AskMeAdapter.ViewHolder> {
        private String id;
        Context context;
        ArrayList<PostQuestion> postQuestionArrayList;



        public AskMeAdapter(Context context, ArrayList<PostQuestion> postQuestionArrayList)
        {
            this.context=context;
            this.postQuestionArrayList=postQuestionArrayList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_question, parent, false);

            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position)
        {




//        private void deleteFile(final int position) {
//
//            answerlist.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, answerlist.size());
//            notifyDataSetChanged();
//            getCreatorProfile();
//        }

            Log.e("size",""+postQuestionArrayList.size());

            if(postQuestionArrayList.get(position).getType().equalsIgnoreCase("audio"))
            {
//                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());
                 holder.llaudio.setVisibility(View.VISIBLE);
                holder.llvideo.setVisibility(View.GONE);

            }
            else
            {
                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());
                holder.llaudio.setVisibility(View.GONE);
            }

            if(postQuestionArrayList.get(position).getType().equalsIgnoreCase("video"))
            {
//                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());
                holder.llvideo.setVisibility(View.VISIBLE);
                holder.llaudio.setVisibility(View.GONE);

            }
            else
            {
                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());
                holder.llvideo.setVisibility(View.GONE);
            }

holder.tv_time.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //eReminderTime.setText( selectedHour + ":" + selectedMinute);
                Utils.displayToastMessage(getContext(),""+selectedHour + ":" + selectedMinute);
                holder.tv_time.setText(""+selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
});

       holder.btn_post.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               question_id=postQuestionArrayList.get(position).getId();
               question_type=postQuestionArrayList.get(position).getType();

             addQuestion();

//               int i=1;
//               sqliteHelper.addQuestion(new PostQuestionPost(String.valueOf(i), postQuestionArrayList.get(position).getQuestion(), postQuestionArrayList.get(position).getOptiona(), postQuestionArrayList.get(position).getOptionb(),postQuestionArrayList.get(position).getOptionc(),postQuestionArrayList.get(position).getOptiond(),postQuestionArrayList.get(position).getAnswer(),postQuestionArrayList.get(position).getType()));
//                i++;
//               Toast.makeText(getActivity(),"Question added",Toast.LENGTH_LONG).show();


           }

           private void addQuestion() {
               JSONObject jsonObject=new JSONObject();
               try {


                   jsonObject.put("question_id", question_id);
                   jsonObject.put("question_type", question_type);
                   jsonObject.put("user_id", StorePrefs.getDefaults(StorePrefs.PREFS_USER_ID,getActivity()));
                   jsonObject.put("user_type", StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE,getActivity()));


               } catch (JSONException e) {
                   e.printStackTrace();
               }
               Log.e("params",""+jsonObject.toString());

               new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(getActivity(), Constant.questionadd, "", jsonObject.toString(),
                       new OnTaskCompleted() {
                           @Override
                           public void onTaskCompleted(String response) {
                               Log.e("responsetesttscheck", response);

                               try {
                                   JSONObject jsonObject1=new JSONObject(response);
                                   if(jsonObject1.getInt("status")==1)
                                   {
                                       Utils.displayToastMessage(getActivity(),jsonObject1.getString("message"));

                                   }
                                   else {
                                       Utils.displayToastMessage(getActivity(),"error");
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
       if(postQuestionArrayList.get(position).getType().equalsIgnoreCase("video"))
       {
           holder.llvideo.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(getActivity(),VideoPlayActivity.class));
               }
           });
       }
       else
       {
           holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());

       }

            if(postQuestionArrayList.get(position).getType().equalsIgnoreCase("audio")) {
                holder.llaudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(audio==true)
                        {



                            mPlayer2.start();

                            Log.e("boolean","1"+audio);
                            audio=false;
                        }
                        else
                        {
                            Log.e("boolean","2"+audio);
//                                if(mPlayer2.isPlaying())
//                                {
//                                    mPlayer2.pause();
//                                }
                            mPlayer2.pause();
//
                            audio=true;

                        }

                    }
                });
            }
            else
            {
                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());

            }


        }
        @Override
        public int getItemCount() {
            return postQuestionArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_question,tv_time;
            Button btn_post;
            LinearLayout llvideo,llaudio;



            public ViewHolder(View itemView) {
                super(itemView);
                tv_question = itemView.findViewById(R.id.tv_question);
                btn_post = itemView.findViewById(R.id.btn_post);
                llvideo = itemView.findViewById(R.id.llvideo);
                llaudio = itemView.findViewById(R.id.llaudio);
                tv_time = itemView.findViewById(R.id.tv_time);
//                iv_user = itemView.findViewById(R.id.iv_user);
//                tv_message = itemView.findViewById(R.id.tv_message);
//                tv_like1 = itemView.findViewById(R.id.tv_like1);
//                spark_button = itemView.findViewById(R.id.spark_button);
//                rl_user = itemView.findViewById(R.id.rl_user);
//                iv_showtooltip = itemView.findViewById(R.id.iv_showtooltip);
//                tv_time = itemView.findViewById(R.id.tv_time);

            }
        }
    }








}
