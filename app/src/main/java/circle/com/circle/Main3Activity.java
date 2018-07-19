package circle.com.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import circle.com.circle.Adapters.allAdapter;
import circle.com.circle.Adapters.mainFeedAdapter;
import circle.com.circle.Data.dataAll;
import circle.com.circle.Data.feed_data;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyRespImage;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Network.circlesVolley;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewStub vst;

    RelativeLayout act;
    RelativeLayout grade;
    RelativeLayout know;
    RelativeLayout classm;
    RelativeLayout defaultact;
    RelativeLayout chat;
    private String current_teacher_id;



    void hideAll()
    {
        grade.setVisibility(View.GONE);
        act.setVisibility(View.GONE);
        defaultact.setVisibility(View.GONE);
        classm.setVisibility(View.GONE);
        know.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        know=(RelativeLayout)findViewById(R.id.know);
        classm=(RelativeLayout)findViewById(R.id.classmates);
         act=(RelativeLayout)findViewById(R.id.activities);
         grade=(RelativeLayout)findViewById(R.id.tear);
         defaultact=(RelativeLayout)findViewById(R.id.dash);
         openActivities();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setTitle("Sarthak Mishra");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    void openActivities()
    {
        hideAll();
        act.setVisibility(View.VISIBLE);
        RecyclerView rexk = (RecyclerView)findViewById(R.id.recycler);
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
    public  void newTeacher(final ImageView rl, final TextView nameT, final TextView aboutT)
    {
        final getUrl mget=new getUrl();
        final circlesVolley mCV=new circlesVolley();
        mCV.makeRequest(mget.returnBase() + "printteacher.php?section=CS7", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {

                try {
                    JSONObject mJSON=new JSONObject(resp);
                    String url=mJSON.getString("pic");
                    String name=mJSON.getString("name");
                    String about=mJSON.getString("about");
                    current_teacher_id=mJSON.getString("id");

                    String urlNmae=mget.returnBase()+"userdata/media/"+url+".jpg";
                    Glide.with(Main3Activity.this).load(urlNmae).into(rl);
                    nameT.setText(name);
                    aboutT.setText(about);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });

    }
    public void updateScore(String score,String t_id)
    {
        getUrl mget=new getUrl();
        circlesVolley mCV=new circlesVolley();
        mCV.makeRequest(mget.returnBase()+"grade.php?newScore=" + score + "&t_id=" + t_id, new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {

            }

            @Override
            public void onError() {

            }
        });
    }
    public void openGrade()
    {

        hideAll();

        final ImageView relBackMain=(ImageView) findViewById(R.id.relBackMain);
        final TextView textname=(TextView)findViewById(R.id.nameTea);
        final TextView descName=(TextView)findViewById(R.id.descTea);

        newTeacher(relBackMain,textname,descName);
        grade.setVisibility(View.VISIBLE);


        grade.setOnTouchListener(new OnSwipeTouchListener(Main3Activity.this){


           public void onSwipeTop() {
               Log.e("Top","d");
           }
           public void onSwipeRight() {
               Log.e("Right","d");
               newTeacher(relBackMain,textname,descName);
               //updateScore("1",current_teacher_id);
               Toast.makeText(Main3Activity.this,"You seem to like this teacher",Toast.LENGTH_LONG).show();
              }
           public void onSwipeLeft() {

               Log.e("Left","d");
               newTeacher(relBackMain,textname,descName);
              // updateScore("-1",current_teacher_id);
               Toast.makeText(Main3Activity.this,":( Dont seem to like this teacher",Toast.LENGTH_LONG).show();
           }
           public void onSwipeBottom() {
              Log.e("Bottom","d");
               newTeacher(relBackMain,textname,descName);
              // updateScore("2",current_teacher_id);
               Toast.makeText(Main3Activity.this,":) :) Super Like ",Toast.LENGTH_LONG).show();

           }



       });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_act) {
            // Handle the camera action
            openActivities();
        }
         else if (id == R.id.nav_class) {
                openClass();

        }else if(id == R.id.nav_teachers)
        {
            openTeachers();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openTeachers() {

        hideAll();
        know.setVisibility(View.VISIBLE);
        RecyclerView rexk = (RecyclerView)findViewById(R.id.recyclerTeachers);
        rexk.setLayoutManager(new LinearLayoutManager(this));

        final allAdapter mGC;
        mGC = new allAdapter(this);
        rexk.setAdapter(mGC);
        getUrl get=new getUrl();
        circlesVolley mCv=new circlesVolley();
        final ArrayList<dataAll> mAX=new ArrayList<dataAll>();
        mCv.makeRequest(get.returnBase() + "myteacher.php?section=CS7", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {

                Log.e("Resp",resp);
                try {
                    JSONArray mJSON=new JSONArray(resp);
                    for(int i=0;i<mJSON.length();i++)
                    {
                        JSONObject mSIbd=mJSON.getJSONObject(i);
                        dataAll mD=new dataAll();

                        mD.setDesc(mSIbd.getString("about"));
                        mD.setName(mSIbd.getString("name"));
                        mD.setImag(mSIbd.getString("pic"));
                        mAX.add(mD);

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

    public void openClass() {

        hideAll();
        classm.setVisibility(View.VISIBLE);
        RecyclerView rexk = (RecyclerView)findViewById(R.id.recyclerStuds);
        rexk.setLayoutManager(new LinearLayoutManager(this));

        final allAdapter mGC;
        mGC = new allAdapter(this);
        rexk.setAdapter(mGC);
        getUrl get=new getUrl();
        circlesVolley mCv=new circlesVolley();
        final ArrayList<dataAll> mAX=new ArrayList<dataAll>();
        mCv.makeRequest(get.returnBase() + "myclassmate.php?section=CS7", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {

                Log.e("Resp",resp);
                try {
                    JSONArray mJSON=new JSONArray(resp);
                    for(int i=0;i<mJSON.length();i++)
                    {
                        JSONObject mSIbd=mJSON.getJSONObject(i);
                        dataAll mD=new dataAll();

                        mD.setDesc(mSIbd.getString("about"));
                        mD.setName(mSIbd.getString("full_name"));
                        mD.setImag(mSIbd.getString("pro_pic"));
                        mAX.add(mD);

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
}
