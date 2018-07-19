package circle.com.circle.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import circle.com.circle.Data.circlesInit;
import circle.com.circle.Data.feed_data;
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Library.SharedPrefs;
import circle.com.circle.Network.circleUser;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class mainFeedAdapter extends RecyclerView.Adapter<mainFeedAdapter.ViewHolderC> {

    LayoutInflater mLay;
    Context mCp;
    Boolean verts=true;

    public Boolean getVerts() {
        return verts;
    }

    public void setVerts(Boolean verts) {
        this.verts = verts;
    }

    ArrayList<feed_data> mArray=new ArrayList<>();

    public void setData(ArrayList<feed_data> mInt)
    {
        Log.e("DCame","DD");
        mArray=mInt;
        notifyItemRangeChanged(0,mArray.size());
    }
    public mainFeedAdapter(Context mC) {


        mLay=LayoutInflater.from(mC);
        mCp=mC;


    }

    @Override
    public mainFeedAdapter.ViewHolderC onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        if(getVerts()) {
            v = mLay.inflate(R.layout.custom_feed, parent, false);
        }
        else {
            v = mLay.inflate(R.layout.custom_circle2, parent, false);

        }
        ViewHolderC mVC=new ViewHolderC(v);
        return mVC;
    }



    @Override
    public void onBindViewHolder(ViewHolderC holder, int position) {


        feed_data mS=mArray.get(position);
        Log.e("HD",mS.toString());
        Resources res =mCp.getResources();
        int resID = res.getIdentifier(mS.getDisplay(), "drawable", mCp.getPackageName());
        getUrl getU=new getUrl();

            String rl=getU.returnBase()+"userdata/media/"+mS.getDisplay()+".jpg";
            Log.e("Rdl",rl);
            Log.e("ResiDX", "-" + resID);
            try {

                Glide.with(mCp).load(rl).into(holder.mImg);


            } catch (Exception e) {
                Log.e("E.", e.getMessage());
            }

         holder.name.setText(mS.getName());
            holder.about.setText(mS.getAbout());
            holder.venue.setText(mS.getVenue());

    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }



    public class ViewHolderC extends RecyclerView.ViewHolder
    {

       ImageView mImg;
        TextView name;
        TextView about;
        TextView venue;
        RelativeLayout mRel;
        public ViewHolderC(View itemView) {
            super(itemView);


            mRel=(RelativeLayout)itemView.findViewById(R.id.rel);
            mImg=(ImageView)itemView.findViewById(R.id.imageX);
            name=(TextView)itemView.findViewById(R.id.name);
            about=(TextView)itemView.findViewById(R.id.about);
            venue=(TextView)itemView.findViewById(R.id.venue);


        }
    }
}
