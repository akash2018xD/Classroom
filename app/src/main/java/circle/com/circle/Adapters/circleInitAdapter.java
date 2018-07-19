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
import circle.com.circle.Data.getUrl;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.Library.SharedPrefs;
import circle.com.circle.Network.circleUser;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class circleInitAdapter extends RecyclerView.Adapter<circleInitAdapter.ViewHolderC> {

LayoutInflater mLay;
   Context mCp;
    Boolean verts=true;

    public Boolean getVerts() {
        return verts;
    }

    public void setVerts(Boolean verts) {
        this.verts = verts;
    }

    ArrayList<circlesInit> mArray=new ArrayList<>();

    public void setData(ArrayList<circlesInit> mInt)
    {
        mArray=mInt;
        notifyItemRangeChanged(0,mArray.size());
    }
    public circleInitAdapter(Context mC) {


        mLay=LayoutInflater.from(mC);
        mCp=mC;


    }

    @Override
    public ViewHolderC onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
            v = mLay.inflate(R.layout.custom_circle, parent, false);


        ViewHolderC mVC=new ViewHolderC(v);
        return mVC;
    }

    @Override
    public void onBindViewHolder(ViewHolderC holder, int position) {


        circlesInit mS=mArray.get(position);

            getUrl gets=new getUrl();
        Resources res =mCp.getResources();
        int resID = res.getIdentifier(mS.getDisplay(), "drawable", mCp.getPackageName());
        Log.e("res",resID+"");
        String url=gets.returnBase()+"userdata/circles/"+mS.getDisplay()+".png";

        Glide.with(mCp).load(url).into(holder.mImg);
            holder.name.setText(mS.getName());

    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }



    public class ViewHolderC extends RecyclerView.ViewHolder
    {

        private final ImageView tick;
        ImageView mImg;
        TextView name;
        RelativeLayout mRel;
            public ViewHolderC(View itemView) {
            super(itemView);


                mRel=(RelativeLayout)itemView.findViewById(R.id.rel);
            mImg=(ImageView)itemView.findViewById(R.id.image);
            name=(TextView)itemView.findViewById(R.id.text);
            tick=(ImageView)itemView.findViewById(R.id.tick);

        mRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick.setVisibility(View.VISIBLE);
                circlesVolley mC=new circlesVolley();
                circleUser mCu=new circleUser();
                getUrl get=new getUrl();
                circlesInit mGets = mArray.get(getAdapterPosition());
                mC.makeRequest(get.returnBase() + "circle.php?code=cjoin&user_id=" + mCu.getUserId(mCp) + "&c_id=" + mGets.getId(), new VolleyResponse() {
                    @Override
                    public void onSuccess(String resp) {

                    }

                    @Override
                    public void onError() {

                    }
                });

                        //Join Here
            }
        });

        }
    }
}
