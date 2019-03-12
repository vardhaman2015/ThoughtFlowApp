package pcube.servey.networkUtils;

/**
 * Created by chinmay on 15/01/18.
 */


public interface OnTaskCompleted {

    void onTaskCompleted(String response);

    void onError(String error);
}
