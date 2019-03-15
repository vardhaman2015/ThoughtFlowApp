package pcube.servey.networkUtils;


import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

import pcube.servey.utils.Utils;



/**
 * Created by chinmay on 15/01/18.
 */

public class ServiceHandler {

    public static final String TAG = "ServiceHandler";

    public final static int GET = 1;
    public final static int POST = 2;
    public final static int NO_PARAMS = 1;
    public final static int HAS_PARAMS = 2;

    public final static String STATUS_SUCCESS = "success";
    public final static String STATUS_ERROR = "error";
    public final static String RESPONSE_STATUS = "status";
    public final static String RESPONSE_MESSAGE = "message";
    public final static String RESPONSE_DATA = "data";
    public final static String RESPONSE_ID = "id";


    public ServiceHandler() {

    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     */

    public static ServiceResponse makeServiceCallWithoutParams(Context context, String url, int method) {
        // http client
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        String responseBody;
        int responseCode = 0;

        try {
            // Checking http request method type
            if (method == POST) {

                RequestBody body = RequestBody.create(null, new byte[0]);
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")

                        .build();

            } else if (method == GET) {

                request = new Request.Builder()
                        .url(url)
                        .get()
                       .addHeader("content-type", "application/x-www-form-urlencoded")

                        .build();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.displayToastMessage(context, e.getMessage());
            return null;
        }


        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.getMessage());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);

    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     */
    public static ServiceResponse makeServiceCall(Context context, String url, int method, String params) {
        // http client
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = null;
        String responseBody;
        int responseCode = 0;

        try {

            // Checking http request method type
            if (method == POST) {
                RequestBody body = RequestBody.create(mediaType, params);
                request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/x-www-form-urlencoded")


                        .build();

            } else if (method == GET) {

                request = new Request.Builder()
                        .url(url + params)
                        .get()
                        .addHeader("content-type", "application/x-www-form-urlencoded")

                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Utils.displayToastMessage(context, e.getMessage());
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }

        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);
    }

    public static ServiceResponse makeServiceCallWithRawData(Context context, String url, String params) {
        // http client
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Request request;
        String responseBody;
        int responseCode = 0;

        try {
            RequestBody body = RequestBody.create(mediaType, params);
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "e599cda7-feb5-25f8-f9d9-a7ef22b44f65")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            Utils.displayToastMessage(context, e.getMessage());
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }

        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);
    }

    public static ServiceResponse makeServiceCallGETWithHeader(Context context, String url,String header) {
        // http client
        OkHttpClient client = new OkHttpClient();
        Request request;
        String responseBody;
        int responseCode = 0;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        try {

            request = new Request.Builder()
                    .url(url)
                    .get()
                 //  .addHeader("Authorization", header)
                    .build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.displayToastMessage(context, e.getMessage());
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }

        try
        {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);
    }

    public static ServiceResponse makeServiceCallPOSTWithHeader(Context context, String url,String header, String params) {
        // http client
        OkHttpClient client = new OkHttpClient();
         MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        //MediaType mediaType = MediaType.parse("application/json");
        Log.e("testparams",params+"header  "+header);
        Request request;
        String responseBody;
        int responseCode = 0;

        try {
            RequestBody body = RequestBody.create(mediaType, params);
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", header)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.displayToastMessage(context, e.getMessage());
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }

        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);
    }


    public static ServiceResponse makeServiceCallPOSTWithHeaderWithRawData(Context context, String url,String header, String params) {
        // http client
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        //MediaType mediaType = MediaType.parse("application/json");
        Log.e("testparams",params+"header  "+header);
        Request request;
        String responseBody;
        int responseCode = 0;

        try {
            RequestBody body = RequestBody.create(mediaType, params);
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", header)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.displayToastMessage(context, e.getMessage());
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }

        try {
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
            responseCode = response.code();

        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResponse(responseCode, STATUS_ERROR, e.toString());
        }
        return new ServiceResponse(responseCode, STATUS_SUCCESS, responseBody);
    }


   }