package pcube.servey.answerquestion;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pcube.servey.R;
import pcube.servey.database.SqliteHelper;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.postquestion.PostQuestion;
import pcube.servey.postquestion.VideoPlayActivity;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;

public class Fragment_AnswerQuestion extends Fragment {

    RecyclerView rv_question;
    SqliteHelper sqliteHelper;
    LinearLayoutManager layoutManager;
    AskMeAdapter askMeAdapter;
    ArrayList<PostQuestion> postQuestionAdds = new ArrayList<>();
    String answer = "";
    AlertDialog alertDialog;
    String question_id = "", question_type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_responsequestion, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Questions");
        sqliteHelper = new SqliteHelper(getContext());
//        postQuestionAdds=sqliteHelper.getAllBuffaloRateChartPtoPSnf();
        // Log.e("cvhkjhkjvhjkvh",""+postQuestionAdds.size());
        rv_question = view.findViewById(R.id.rv_question);


        getData();

        return view;
    }

    private void getData() {


        new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(getActivity(), Constant.addedquestionlist, "", "",
                new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.e("responsetesttscheck", response);

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (jsonObject1.getInt("status") == 1) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    PostQuestion postQuestion = new PostQuestion(jsonArray.getJSONObject(i).getString("id"),
                                            jsonArray.getJSONObject(i).getString("question"), jsonArray.getJSONObject(i).getString("a"), jsonArray.getJSONObject(i).getString("b"), jsonArray.getJSONObject(i).getString("c"), jsonArray.getJSONObject(i).getString("d"), "", jsonArray.getJSONObject(i).getString("type"));
                                    postQuestionAdds.add(postQuestion);
                                }
                                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                rv_question.setLayoutManager(layoutManager);
                                askMeAdapter = new AskMeAdapter(getActivity(), postQuestionAdds);
                                rv_question.setAdapter(askMeAdapter);
                                askMeAdapter.notifyDataSetChanged();

                            } else {
                                Utils.displayToastMessage(getContext(), "error");
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


        public AskMeAdapter(Context context, ArrayList<PostQuestion> postQuestionArrayList) {
            this.context = context;
            this.postQuestionArrayList = postQuestionArrayList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_question_response, parent, false);

            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


//        private void deleteFile(final int position) {
//
//            answerlist.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, answerlist.size());
//            notifyDataSetChanged();
//            getCreatorProfile();
//        }
            Log.e("type",""+postQuestionArrayList.get(position).getType()+StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE, getContext()));
            if (StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE, getContext()).equals("5"))
            {

                if (postQuestionArrayList.get(position).getType().equals("video"))
                {
                    holder.ll_main.setVisibility(View.GONE);

                }
                else
                    {
                    holder.ll_main.setVisibility(View.VISIBLE);
                        holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion() + "   Question type " + postQuestionArrayList.get(position).getType());
                }
            }
            else
            {
                holder.ll_main.setVisibility(View.VISIBLE);

                holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion() + "   Question type " + postQuestionArrayList.get(position).getType());
            }


//            holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//            {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (checkedId)
//                    {
//                        case 0:
//                            answer=holder.rb1.getText().toString();
//                            break;
//                        case 1:
//                            answer=holder.rb2.getText().toString();
//                            break;
//                        case 2:
//                            answer=holder.rb3.getText().toString();
//                            break;
//                        case 3:
//                            answer=holder.rb4.getText().toString();
//                            break;
//
//                    }
//                    // checkedId is the RadioButton selected
//                }
//            });

            holder.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question_id = postQuestionArrayList.get(position).getId();
                    question_type = postQuestionArrayList.get(position).getType();
                    getDialog();

                }

                private void getDialog() {
                    final AlertDialog.Builder dialogBuilder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);
                    } else {
                        dialogBuilder = new AlertDialog.Builder(getActivity());
                    }
                    RadioGroup rg;
                    final RadioButton rb1, rb2, rb3, rb4;
                    final LinearLayout ll_playvideo, ll_playaudio, ll_main;
                    final TextView tv_question;
                    final Button btnSubmit;

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_view_question, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setCancelable(true);
                    ll_playaudio = dialogView.findViewById(R.id.ll_playaudio);
                    ll_playvideo = dialogView.findViewById(R.id.ll_playvideo);
                    tv_question = dialogView.findViewById(R.id.tv_question);
                    btnSubmit = dialogView.findViewById(R.id.btnSubmit);

                    rg = dialogView.findViewById(R.id.rg);
                    rb1 = dialogView.findViewById(R.id.rb1);
                    rb2 = dialogView.findViewById(R.id.rb2);
                    rb3 = dialogView.findViewById(R.id.rb3);
                    rb4 = dialogView.findViewById(R.id.rb4);
                    tv_question.setText(postQuestionArrayList.get(position).getQuestion());

                    rb1.setText(postQuestionArrayList.get(position).getOptiona());
                    rb2.setText(postQuestionArrayList.get(position).getOptionb());
                    rb3.setText(postQuestionArrayList.get(position).getOptionc());
                    rb4.setText(postQuestionArrayList.get(position).getOptiond());
                    if (StorePrefs.getDefaults(StorePrefs.PREFS_USER_TYPE, getContext()).equals("5"))
                    {
                        ll_playvideo.setVisibility(View.GONE);
                        ll_playaudio.setVisibility(View.GONE);
                    }
                    else {
                        if(question_type.equals("video"))
                        {
                            ll_playvideo.setVisibility(View.VISIBLE);
                            ll_playaudio.setVisibility(View.GONE);
                        }
                        else
                        {
                            ll_playvideo.setVisibility(View.GONE);
                        }
                    }

                    if(question_type.equals("audio"))
                    {
                        ll_playvideo.setVisibility(View.GONE);
                        ll_playaudio.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        ll_playaudio.setVisibility(View.GONE);
                    }

                    ll_playaudio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaPlayer mPlayer2;
                            mPlayer2 = MediaPlayer.create(getActivity(), R.raw.audioquestion);
                            mPlayer2.start();
                        }
                    });

                    ll_playvideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(),VideoPlayActivity.class));
                        }
                    });
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId)
                    {
                        case 0:
                            answer="A";
                            break;
                        case 1:
                            answer="B";
                            break;
                        case 2:
                            answer="C";
                            break;
                        case 3:
                            answer="D";
                            break;

                    }
                    // checkedId is the RadioButton selected
                }
            });
//

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if (rb1.isChecked()) {
                        // answer=postQuestionArrayList.get(position).getOptiona();
                        answer ="A";
                    } else if (rb2.isChecked()) {
                        //answer=postQuestionArrayList.get(position).getOptionb();
                        answer = "B";
                    } else if (rb3.isChecked()) {
                        //answer=postQuestionArrayList.get(position).getOptionc();
                        answer = "C";
                    } else if (rb4.isChecked())
                    {
                        //answer=postQuestionArrayList.get(position).getOptiond();
                        answer = "D";
                    }


//                            if (answer.equals(""))
//                            {
//                                Toast.makeText(getActivity(),"Please fill answer",Toast.LENGTH_SHORT).show();
//
//                            }
//                            else {

//                                if(answer.equals(""))
//                                {
//                                    Toast.makeText(getActivity(),"Please fill answer",Toast.LENGTH_SHORT).show();
//                                }
//                                else
//                                {
//                                    Log.e("tetsaJD","D'GJGH");
//
//
//                                    getAddAnswer();
//                                }

                                    getAddAnswer();

                            //}
                            // sqliteHelper.addAnswer(new AnswerQuestion(postQuestionArrayList.get(position).getId(),answer));
                        }

                        private void getAddAnswer() {

                            JSONObject jsonObject = new JSONObject();

                            try {

                                jsonObject.put("question_id", question_id);
                                jsonObject.put("question_type", question_type);
                                jsonObject.put("answer", answer);
                                jsonObject.put("user_id", StorePrefs.getDefaults(StorePrefs.PREFS_USER_ID, getActivity()));
                                jsonObject.put("user_type", StorePrefs.getDefaults(StorePrefs.PREFS_USER_ID, getActivity()));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("params", "12333" + jsonObject.toString());

                            new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(getActivity(), Constant.addanswer, "", jsonObject.toString(),
                                    new OnTaskCompleted() {
                                        @Override
                                        public void onTaskCompleted(String response) {
                                            Log.e("responsetesttscheck", response);

                                            try {
                                                JSONObject jsonObject1 = new JSONObject(response);
                                                if (jsonObject1.getInt("status") == 1) {
                                                    Utils.displayToastMessage(getActivity(), jsonObject1.getString("message"));
                                                    alertDialog.dismiss();
                                                } else {
                                                    Utils.displayToastMessage(getActivity(), "error");
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


                    alertDialog = dialogBuilder.create();

//        if(cardname.equalsIgnoreCase("nothing"))
//        {
//            llcard.setVisibility(View.GONE);
//        }
//        else {
//            tv_cardno.setText(cardno);
//            tv_cardname.setText(cardname);
//        }

                    alertDialog.show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return postQuestionArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_question;
            Button btnSubmit;
            LinearLayout ll_main;


            public ViewHolder(View itemView) {
                super(itemView);
                tv_question = itemView.findViewById(R.id.tv_question);

                ll_main = itemView.findViewById(R.id.ll_main);
                btnSubmit = itemView.findViewById(R.id.btnSubmit);


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
