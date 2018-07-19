package circle.com.circle.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circle.com.circle.Interfaces.VolleyRespImage;
import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.MyApplication;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/13/2016.
 */
public class circlesVolley {

    VolleySingleton VS;
    ImageLoader mImageLoader;
    MyApplication mApp;
    public  Boolean isNots=false;
    private Pattern p;
    private StringRequest sRq;
    public boolean isSearch;
    public boolean isPlatesSerch;

    public circlesVolley() {

        //  mPlatesUser=new platesUser();
    }

    public void makeRequest(String url, final VolleyResponse callback)
    {


        if(!isNots)
        {
            try {
                URL mUrl=new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return;//If Url Not Good , What request are we going to make :P
            }

        }
        VS=VS.getInstance();
        RequestQueue rq=VS.getRequestQueue();
        Log.e("URLS", url);
        sRq=new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                Log.e("D",s);
                callback.onSuccess(s);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {



                if(!isNots) {
                    Toast.makeText(MyApplication.getAppContext(), "No internet , please try again later", Toast.LENGTH_LONG).show();
                    callback.onError();
                }

            }
        });

        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        sRq.setRetryPolicy(retryPolicy);

        rq.add(sRq);
    }



    public void makeImageRequestPic(String url,Context mContext,final VolleyRespImage mCallback)
    {
        VS= VolleySingleton.getInstance();
        mImageLoader=VS.getImageLoader();
        mImageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {

                if (imageContainer.getBitmap() != null) {
                    mCallback.onSuccess(imageContainer.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    public void picassoImageLoad(ImageView mImg,String url)
    {

        Picasso.with(MyApplication.getAppContext()).load(url).placeholder(R.drawable.alial).into(mImg);
    }
    public void makeImageRequest(String url,final VolleyRespImage call)
    {

        Glide.with(MyApplication.getAppContext())
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.alial)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        call.onSuccess(resource);
                    }
                });




    }
    public void makeSyncRequest(String url,VolleyResponse callback) {


        VS = VS.getInstance();
        RequestQueue requestS = VS.getRequestQueue();
        RequestFuture<String> fFreq = RequestFuture.newFuture();
        StringRequest newR = new StringRequest(url, fFreq, fFreq);
        requestS.add(newR);


        String response = null;

        try {
            response = fFreq.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        callback.onSuccess(response);

    }
    public void makePostRequest(final Map<String,String> myMap,String MEDIA_URL,final VolleyResponse callback)
    {
        Log.e("URL",MEDIA_URL);
        VS=VS.getInstance();
        RequestQueue rq=VS.getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,MEDIA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        callback.onSuccess(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MyApplication.getAppContext(),"No internet , please try again later",Toast.LENGTH_LONG).show();

                        callback.onError();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();

                Iterator<Map.Entry<String, String>> iterator = myMap.entrySet().iterator();
                while(iterator.hasNext()) {
                    Map.Entry<String,String> pairs = (Map.Entry<String,String>)iterator.next();
                    String value =  pairs.getValue();
                    String key = pairs.getKey();
                    params.put(key,value);
                    Log.e("Key","Value"+value);
                }
                return params;
            }
        };

        //Creating a Request Queue
        // RequestQueue requestQueue = Volley.newRequestQueue(this);
        DefaultRetryPolicy  retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        //Adding request to the queue
        rq.add(stringRequest);

    }


}
