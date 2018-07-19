package circle.com.circle.Data;

import com.google.firebase.iid.FirebaseInstanceId;

import circle.com.circle.Library.SharedPrefs;
import circle.com.circle.MyApplication;

/**
 * Created by skmishra on 12/14/2016.
 */
public class getUrl {


    private SharedPrefs mShared;


    public getUrl() {
        mShared = new SharedPrefs();
    }

    public final String BUILD_ACCESS_CODE ="CODE" ;
     public String returnBase()
    {
        return "http://192.168.219.1/circles/";
        // return "http://192.168.59.1/Plates/";
    }

}
