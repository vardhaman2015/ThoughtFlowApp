package pcube.servey.register;

import org.json.JSONObject;

public class UserTypeModel {
    JSONObject jsonObject;

    public UserTypeModel(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
