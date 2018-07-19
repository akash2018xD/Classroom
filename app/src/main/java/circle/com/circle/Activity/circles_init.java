package circle.com.circle.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import circle.com.circle.Adapters.circleInitAdapter;
import circle.com.circle.Data.circlesInit;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Main3Activity;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class circles_init extends Activity {

    private getUrl get;
    private ArrayList<circlesInit> mSingInner;
    RecyclerView mRc;
    circleInitAdapter mAdap;
    RelativeLayout mRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_init_circles);
        get=new getUrl();
        mRel=(RelativeLayout)findViewById(R.id.comProg);
        mRel.setVisibility(View.VISIBLE);
        mRc=(RecyclerView)findViewById(R.id.mrec);
        mAdap=new circleInitAdapter(this);
        mRc.setLayoutManager(new GridLayoutManager(this,3));
        mRc.setAdapter(mAdap);
         makeRequest();
    }

    public void register(View v)
    {

        Intent i=new Intent(this,Main3Activity.class);
        startActivity(i);
    }
    private void makeRequest() {

        final ArrayList<circlesInit> mArray=new ArrayList<>();
        circlesVolley mC=new circlesVolley();
        mC.makeRequest(get.returnBase() + "circle.php?code=drr", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {
                JSONArray mJSON = null;
                try {
                    mJSON = new JSONArray(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRel.setVisibility(View.GONE);
                for (int i = 0; i < mJSON.length(); i++) {
                    JSONObject mSingOb = null;
                    circlesInit mSing = new circlesInit();
                    try {
                        mSingOb = mJSON.getJSONObject(i);
                        mSing.setDisplay(mSingOb.getString("display_pic"));
                        mSing.setName(mSingOb.getString("name"));
                        mSing.setId(mSingOb.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /*

                    try {
                        JSONArray mJSONSubs = mSingOb.getJSONArray("subs");
                        if (mJSONSubs.length() > 0) {
                            mSing.setHasSubs(true);
                            for (int j = 0; j < mJSONSubs.length(); j++) {
                                mSingInner = new ArrayList<circlesInit>();
                                JSONObject mJSONX = mJSONSubs.getJSONObject(j);
                                circlesInit mSingObsInit = new circlesInit();
                                mSingObsInit.setName(mJSONX.getString("name"));
                                mSingObsInit.setDisplay(mJSONX.getString("display"));
                                mSingInner.add(mSingObsInit);
                            }
                        } else {
                            mSing.setHasSubs(false);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSing.setmArray(mSingInner);
                */
                    mArray.add(mSing);

                }

            }

            @Override
            public void onError() {

            }
        });

        onSuccess(mArray);
    }

    private void onSuccess(ArrayList<circlesInit> mArray) {

        mAdap.setData(mArray);

    }

}
