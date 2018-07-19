package circle.com.circle.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import circle.com.circle.Activity.MainActivity;
import circle.com.circle.Activity.username;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Library.SharedPrefs;
import circle.com.circle.MyApplication;

/**
 * Created by skmishra on 12/14/2016.
 */
public class circleUser {
    private final circlesVolley mCirclesVolley;
    getUrl get;
    public circleUser() {
        get=new getUrl();
        mCirclesVolley=new circlesVolley();
    }

    public void register(String em, String us, String oType, String oId, String full, final username mref) {
        //Map params<String,String>=new HashMap<String,String>()
        full = full.replace(" ", "-");
        full = full.replaceAll("\\s+", "");
        us = us.replaceAll("\\s+", "");
        String url = get.returnBase() + "register.php?code=" + get.BUILD_ACCESS_CODE + "&email=" + em + "&full=" + full + "&roll_no=" + us + "&oType=" + oType + "&oId=" + oId;

        Log.e("URLS", url);
        mCirclesVolley.makeRequest(url, new VolleyResponse() {
            String[] mFunc;

            @Override
            public void onSuccess(String resp) {


                try {
                    mref.onSuccess(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError() {

            }
        });

    }
    public void initiate_login_oauth(final JSONObject object, final MainActivity mRef) {
        String email = null;
        String oauth_type = null;
        try {
            email = object.getString("email");
             oauth_type = object.getString("oauth_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = get.returnBase() + "login.php?code=" + get.BUILD_ACCESS_CODE + "&email=" + email + "&method=" + oauth_type;
        Log.e("RLSL", url);
        mCirclesVolley.makeRequest(url, new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {
                try {
                    int rets_id = 0;
                    JSONObject obs = new JSONObject(resp);
                    Log.e("RESPX", obs.toString());
                    Boolean canLogin = obs.getBoolean("state");
                    Boolean error = obs.getBoolean("error");
                    if (obs.has("sess_id")) {
                        rets_id = obs.getInt("sess_id");
                    }
                    mRef.handleOauthLogin(object, canLogin, rets_id, error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError() {

            }
        });


    }

    public void initiateUser(int id,Context mContext)
    {
        mContext= MyApplication.getAppContext();
        SharedPrefs mSharedPrefs;
        mSharedPrefs=new SharedPrefs();
        mSharedPrefs.putData(mContext, "userID", "" + id);

    }

    public boolean check_profile_ownership(Context context,String userID)
    {
        SharedPrefs mShared=new SharedPrefs();
        String user=mShared.getData(context,"userID");
        return user.equals(userID);
    }
    public String getUserId(Context context)
    {
        SharedPrefs mShared=new SharedPrefs();
        String shP= mShared.getData(context,"userID");
        return shP;
    }
}
