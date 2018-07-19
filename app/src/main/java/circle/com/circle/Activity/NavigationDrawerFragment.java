package circle.com.circle.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import circle.com.circle.Adapters.circleInitAdapter;
import circle.com.circle.Data.circlesInit;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.MyApplication;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class NavigationDrawerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RecyclerView recycler;
    private String mParam1;
    private String mParam2;
    private ActionBarDrawerToggle mtog;
    private DrawerLayout mDlay;
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View mContainer;
    private boolean mDrawerOpened = false;
    private int outOfBounds=-1;//False

    private static  final String FILENAME="file";

    private OnFragmentInteractionListener mListener;

    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer =Boolean.valueOf( readFromPrefrences(getActivity(), KEY_USER_LEARNED_DRAWER,"false"));
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        circleInitAdapter mAdap=new circleInitAdapter(MyApplication.getAppContext());
        mAdap.setVerts(false);

            getUrl get=new getUrl();

        final ArrayList<circlesInit> mArray=new ArrayList<>();
        circlesVolley mC=new circlesVolley();
        mC.makeRequest(get.returnBase() + "circle.php?code=cget", new VolleyResponse() {
            @Override
            public void onSuccess(String resp) {
                JSONArray mJSON = null;
                try {
                    mJSON = new JSONArray(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                    mArray.add(mSing);

                }

            }

            @Override
            public void onError() {

            }
        });


        mAdap.setData(mArray);
        super.onViewCreated(view, savedInstanceState);

    }

    public void setUp(int Fragment,DrawerLayout Dlay,Toolbar toolbar,int outOF)
    {
        outOfBounds=outOF;
        mDlay=Dlay;
        mContainer=getActivity().findViewById(Fragment);
        mtog=new ActionBarDrawerToggle(getActivity(),mDlay,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;

                    saveToPrefrences(getActivity(), KEY_USER_LEARNED_DRAWER,"true");//Check Above Line
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState)
        {
            mDlay.openDrawer(mContainer);
        }
        mDlay.setDrawerListener(mtog);
        mDlay.post(new Runnable() {
            @Override
            public void run() {
                mtog.syncState();

            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static void saveToPrefrences(Context context,String key,String boolV)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,boolV);
        editor.apply();
    }
    public static String readFromPrefrences(Context context, String key, String def)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,def);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);


        public void onLongClick(View view, int position);
    }



}
