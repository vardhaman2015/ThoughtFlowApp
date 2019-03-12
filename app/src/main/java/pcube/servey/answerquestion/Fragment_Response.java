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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pcube.servey.R;
import pcube.servey.database.SqliteHelper;
import pcube.servey.model.AnswerQuestion;
import pcube.servey.networkUtils.BackGroundTask;
import pcube.servey.networkUtils.Constant;
import pcube.servey.networkUtils.OnTaskCompleted;
import pcube.servey.postquestion.Fragment_PostQuestion;
import pcube.servey.postquestion.PostQuestion;
import pcube.servey.postquestion.VideoPlayActivity;
import pcube.servey.utils.StorePrefs;
import pcube.servey.utils.Utils;

public class Fragment_Response extends Fragment {
    ArrayList<PostQuestion>answerQuestions=new ArrayList<>();
    SqliteHelper sqliteHelper;
    RecyclerView rv_answer;
    LinearLayoutManager layoutManager;
    AskMeAdapter askMeAdapter;
    AlertDialog alertDialog;
    String question_id="";
     TextView tv_question,tvoptiond,tvoptionc,tvoptionb,tvoptiona,tv_total,tvpercentaged,tvpercentagec,tvpercentageb,tvpercentagea;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_responsegraph, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().setTitle("Response Question");
      /// sqliteHelper=new SqliteHelper(getContext());
       //answerQuestions=sqliteHelper.getAllAnswer();
        rv_answer=view.findViewById(R.id.rv_answer);

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
                                    answerQuestions.add(postQuestion);
                                }
                                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                rv_answer.setLayoutManager(layoutManager);
                                askMeAdapter = new AskMeAdapter(getActivity(),answerQuestions);
                                rv_answer.setAdapter(askMeAdapter);
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_question_answer, parent, false);

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



            holder.tv_question.setText(postQuestionArrayList.get(position).getQuestion());

            holder.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    question_id=postQuestionArrayList.get(position).getId();

                   getDialog();
                }

                private void getDialog() {
                    final AlertDialog.Builder dialogBuilder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogStyle);
                    } else {
                        dialogBuilder = new AlertDialog.Builder(getActivity());
                    }






                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_view_response, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setCancelable(true);

                    tv_question = dialogView.findViewById(R.id.tv_question);
                    tvoptiona = dialogView.findViewById(R.id.tvoptiona);
                    tvoptionb = dialogView.findViewById(R.id.tvoptionb);
                    tvoptionc = dialogView.findViewById(R.id.tvoptionc);
                    tvoptiond = dialogView.findViewById(R.id.tvoptiond);
                    tv_total = dialogView.findViewById(R.id.tv_total);
                    tvpercentaged = dialogView.findViewById(R.id.tvpercentaged);
                    tvpercentagec = dialogView.findViewById(R.id.tvpercentagec);
                    tvpercentageb = dialogView.findViewById(R.id.tvpercentageb);
                    tvpercentagea = dialogView.findViewById(R.id.tvpercentagea);



                    tv_question.setText(postQuestionArrayList.get(position).getQuestion());

                        getResponse(question_id);



//


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

                private void getResponse(String question_id) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("question_id",question_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new BackGroundTask.BackGroundTaskPOSTWithHeaderWithRawData(getActivity(), Constant.singlequestionresponse, "", jsonObject.toString(),
                            new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String response) {
                                    Log.e("responsetesttscheck", "single"+response);

                                    try {
                                        JSONObject jsonObject1=new JSONObject(response);
                                        if(jsonObject1.getInt("status")==1)
                                        {
                                            JSONObject data=jsonObject1.getJSONObject("data");
                                            JSONArray answer_info=data.getJSONArray("answer_info");
                                            for (int i=0;i<answer_info.length();i++)
                                            {
                                                tv_total.setText("Total user : "+answer_info.getJSONObject(i).getString("TotalAnswered"));
                                                tvoptiona.setText(answer_info.getJSONObject(i).getString("AOption"));
                                                tvoptionb.setText(answer_info.getJSONObject(i).getString("BOption"));
                                                tvoptionc.setText(answer_info.getJSONObject(i).getString("COption"));
                                                tvoptiond.setText(answer_info.getJSONObject(i).getString("DOption"));
                                                float a=(Float.parseFloat(answer_info.getJSONObject(i).getString("AOption"))*100)/Float.parseFloat(answer_info.getJSONObject(i).getString("TotalAnswered"));
                                                float b=(Float.parseFloat(answer_info.getJSONObject(i).getString("BOption"))*100)/Float.parseFloat(answer_info.getJSONObject(i).getString("TotalAnswered"));
                                                float c=(Float.parseFloat(answer_info.getJSONObject(i).getString("COption"))*100)/Float.parseFloat(answer_info.getJSONObject(i).getString("TotalAnswered"));
                                                float d=(Float.parseFloat(answer_info.getJSONObject(i).getString("DOption"))*100)/Float.parseFloat(answer_info.getJSONObject(i).getString("TotalAnswered"));
                                            tvpercentagea.setText(""+a+"%");
                                            tvpercentageb.setText(""+b+"%");
                                            tvpercentagec.setText(""+c+"%");
                                            tvpercentaged.setText(""+d+"%");
                                            }


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
            });




        }
        @Override
        public int getItemCount() {
            return postQuestionArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_question;
            LinearLayout ll_main;
//            Button btnSubmit;
//            RadioGroup rg;
//            RadioButton rb1,rb2,rb3,rb4;



            public ViewHolder(View itemView) {
                super(itemView);
                tv_question = itemView.findViewById(R.id.tv_question);
                ll_main = itemView.findViewById(R.id.ll_main);
//                rg = itemView.findViewById(R.id.rg);
//                rb1 = itemView.findViewById(R.id.rb1);
//                rb2 = itemView.findViewById(R.id.rb2);
//                rb3 = itemView.findViewById(R.id.rb3);
//                rb4 = itemView.findViewById(R.id.rb4);
//                btnSubmit = itemView.findViewById(R.id.btnSubmit);


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
