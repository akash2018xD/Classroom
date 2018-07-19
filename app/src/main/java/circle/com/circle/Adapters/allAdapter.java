package circle.com.circle.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import circle.com.circle.Data.dataAll;
import circle.com.circle.Data.getUrl;
import circle.com.circle.R;

/**
 * Created by skmishra on 3/31/2018.
 */
public class allAdapter extends RecyclerView.Adapter<allAdapter.ViewHolderAll> {


    ArrayList<dataAll> mArray=new ArrayList<>();
    private final LayoutInflater mLay;
    Context context;

    public allAdapter(Context c) {
        mLay= LayoutInflater.from(c);
        context=c;

    }

    @Override
    public ViewHolderAll onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
            v = mLay.inflate(R.layout.small_view_all, parent, false);
            ViewHolderAll vg=new ViewHolderAll(v);
           return vg;
    }

    @Override
    public void onBindViewHolder(ViewHolderAll holder, int position) {

        dataAll mds=mArray.get(position);
        holder.about.setText(mds.getDesc());
        holder.textBig.setText(mds.getName());
        getUrl mGte=new getUrl();
        String url=mGte.returnBase()+"userdata/media/"+mds.getImag()+".jpg";
        Log.e("Url",url);
       Glide.with(context).load(url).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public void setData(ArrayList<dataAll> mAX) {

        Log.e("DCame","DD");
        mArray=mAX;
        notifyItemRangeChanged(0,mArray.size());
    }

    class ViewHolderAll extends RecyclerView.ViewHolder{

        ImageView img;
        TextView textBig;
        TextView about;

        public ViewHolderAll(View itemView) {
                super(itemView);
                img=(ImageView)itemView.findViewById(R.id.imageX);
                textBig=(TextView)itemView.findViewById(R.id.name);
                about=(TextView)itemView.findViewById(R.id.about);


        }
    }
}
