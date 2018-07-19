package circle.com.circle.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import circle.com.circle.Adapters.mainFeedAdapter;
import circle.com.circle.Data.feed_data;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class feed extends AppCompatActivity implements NavigationDrawerFragment.OnFragmentInteractionListener  {

    private NavigationDrawerFragment drawer;
    private Toolbar toolbar;
     private DrawerLayout Dlay;
    RecyclerView rexk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        setTitle("Sarthak Mishra");
        Dlay=(DrawerLayout)findViewById(R.id.DrawerLayout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        buildDrawer();

        rexk=(RecyclerView)findViewById(R.id.recycler);
        rexk.setLayoutManager(new LinearLayoutManager(this));

        final mainFeedAdapter mGC;
        mGC = new mainFeedAdapter(this);
        rexk.setAdapter(mGC);
        getUrl get=new getUrl();
        circlesVolley mCv=new circlesVolley();
        final ArrayList<feed_data> mAX=new ArrayList<feed_data>();
        mCv.makeRequest(get.returnBase() + "circle.php?code=acts", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {

                Log.e("Resp",resp);
                try {
                    JSONArray mJSON=new JSONArray(resp);
                    for(int i=0;i<mJSON.length();i++)
                    {
                        JSONObject mSIbd=mJSON.getJSONObject(i);
                        feed_data fx=new feed_data();

                        fx.setName(mSIbd.getString("Organizer"));
                        fx.setDisplay(mSIbd.getString("display_pic"));
                        fx.setAbout(mSIbd.getString("description"));
                        fx.setVenue(mSIbd.getString("venue"));

                        mAX.add(fx);

                    }
                    mGC.setData(mAX);
                    Log.e("ffffff",mAX.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
                Log.e("Failed","Request");
            }
        });


      }
    public void buildDrawer()
    {
        drawer = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        drawer.setUp(R.id.fragment, Dlay, toolbar, -1);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
