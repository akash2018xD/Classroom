package circle.com.circle;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.v4.util.LruCache;

/**
 * Created by skmishra on 12/13/2016.
 */
public class MyApplication extends Application {


    private static LruCache<String, Bitmap> mMemoryCache;
    private static MyApplication ins;


    @Override
    public void onCreate() {
        super.onCreate();
        ins=this;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };



    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    public static MyApplication getInstance()
    {
        return ins;
    }

    public static Context getAppContext()
    {
        return ins.getApplicationContext();
    }
}
