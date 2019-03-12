package pcube.servey.networkUtils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import pcube.servey.R;
import pcube.servey.utils.Utils;


/**
 * Created by chinmay on 15/01/18.
 */

public class BackGroundTask {






       public static class BackGroundTaskPOSTWithHeaderWithRawData extends AsyncTask<Object, Void, ServiceResponse> {

        public OnTaskCompleted listener;
        private String url;
        private String params;
        private Context context;
        private ProgressDialog progressDialog;
        private String header;


        public BackGroundTaskPOSTWithHeaderWithRawData(Context context, String url,  String header,String params,OnTaskCompleted listener) {
            this.context = context;
            this.url = url;
            this.params = params;
            this.header = header;
            this.listener = listener;

            Log.e("===url====>>>",""+url);
            Log.e("===params====>>>",""+params);
            Log.e("====header===>>>",""+header);


            //Assigning call back interface  through constructor
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, null, null);

            Drawable d = new ColorDrawable(ContextCompat.getColor(context, R.color.chart_transparent));
            d.setAlpha(200);
            progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            progressDialog.getWindow().setBackgroundDrawable(d);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.show();
        }

        @Override
        protected ServiceResponse doInBackground(Object... params) {

            if (NetworkUtil.isNetworkAvailable(context)) {
                synchronized (this) {

                    url = url.replaceAll(" ", "%20");
                    Log.i("Url With HEADER: ", " ------> " + url);

                    try {
                        ServiceResponse serviceResponse = ServiceHandler.makeServiceCallPOSTWithHeaderWithRawData(context, url, header,this.params);
                        Log.i("Response: ", " ------> " + serviceResponse.getResponse());
                        return serviceResponse;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Utils.displayToastMessage(context, e.getMessage());
                        return null;
                    }
                }
            }
            else
            {

                Utils.displayToastMessage(context,"No internet");

//                DialogInternet dialogCountry = new DialogInternet();
//                dialogCountry.show(((AppCompatActivity)context).getSupportFragmentManager(), "Data");

                return null;
            }
        }

        @Override
        protected void onPostExecute(ServiceResponse result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (result != null) {

                if (result.getStatus().equals(ServiceHandler.STATUS_SUCCESS))
                    listener.onTaskCompleted(result.getResponse());
                else {
                    listener.onError(result.getResponse());
                }
            }
        }
    }

    }