package pcube.servey.networkUtils;

/**
 * Created by chinmay on 09/05/18.
 */

public class ServiceResponse {


    int responseCode;
    String status;
    String response;

    public ServiceResponse(int responseCode, String status, String response) {
        this.responseCode = responseCode;
        this.status = status;
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
