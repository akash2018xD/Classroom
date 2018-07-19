package circle.com.circle.Library;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Base64;


import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import circle.com.circle.MyApplication;

/**
 * Created by skmishra on 1/6/2016.
 */
public class SharedPrefs  {

    private LruCache<String, Bitmap> mMemoryCache=null;

    public SharedPrefs()
    {

    }
    public SharedPrefs(LruCache<String,Bitmap> mC) {

        mMemoryCache = mC;
    }

    public void putData(Context context,String key,String value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }
    public void putData(Context context,String key,Boolean value)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
    public void putData(Context context,String key,int value)
    {

        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putInt(key, value);
        edit.apply();
    }


    public void putData(Context context,Bitmap bit,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(key);
        String base64=encodeTobase64(bit);
        edit.putString(key, base64);
        edit.apply();

    }
    public void removeData(Context context,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(key);
        edit.apply();

    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }
    public String getData(Context mConts,String key)
    {
        Context context;
        context= MyApplication.getAppContext();//  i
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        String contents =sharedPref.getString(key, "alias");
        return  contents;


    }

    public Boolean getDataBoolean(Context context,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        Boolean contents =sharedPref.getBoolean(key, false);
        return  contents;
    }
    public int getDataInt(Context context,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        int contents =sharedPref.getInt(key, 0);
        return  contents;
    }
    public Bitmap getDataBitmap(Context context,String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        String photo = sharedPref.getString(key, null);
        Bitmap bitmap = null;
        if (photo != null) {
            if (!photo.equals("photo")) {
                byte[] b = Base64.decode(photo, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                bitmap = BitmapFactory.decodeStream(is);
            }
            return bitmap;
        }
      return  null;
    }
    public void addBitmapToCache(String key,Bitmap bitmap)
    {
        if (getBitmapFromMemCache(key) == null) {
            if(bitmap!=null) {
                mMemoryCache.put(key, bitmap);
            }
            }
    }
    public Bitmap getBitmapFromMemCache(String key)
    {
        return mMemoryCache.get(key);
    }
    public void logout(Context context)
    {
        NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
        FacebookSdk.sdkInitialize(context);
        LoginManager.getInstance().logOut();
        SharedPreferences sharedPref = context.getSharedPreferences("plates", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        putData(context,"security_token","alias");
        edit.clear();
        edit.apply();
    }


}
