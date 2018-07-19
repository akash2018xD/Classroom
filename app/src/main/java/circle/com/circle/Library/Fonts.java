package circle.com.circle.Library;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by skmishra on 12/23/2015.
 */
public class Fonts  {



        Typeface gets;
        public void setGrandHotel(Context context,TextView view)
        {
            gets=Typeface.createFromAsset(context.getAssets(),"GrandHotel-Regular.otf");
            view.setTypeface(gets);

        }
        public void setRalewayBold(Context context,TextView view)
        {
            gets=Typeface.createFromAsset(context.getAssets(),"Raleway-Bold.ttf");
            view.setTypeface(gets);
        }
            public void setRalewayBold(Context context,Button view)
            {
                gets=Typeface.createFromAsset(context.getAssets(),"Raleway-Bold.ttf");
                view.setTypeface(gets);
            }

            public void setRalewayRegular(Context context,TextView view)
            {
                gets=Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf");
                view.setTypeface(gets);
            }
    public void setRalewaySemiBold(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(), "Raleway-SemiBold.ttf");
        view.setTypeface(gets);


    }
    public void setRobotoLight(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(),"Roboto-Light.ttf");
        view.setTypeface(gets);
    }
    public void setRobotoRegular(Context context,TextView view)
    {
        gets=Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        view.setTypeface(gets);
    }
    public void setFontAwesome(Context context,TextView view)
        {
            gets=Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
            view.setTypeface(gets);

        }


}
