package circle.com.circle.Library;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import circle.com.circle.Activity.MainActivity;
import circle.com.circle.MyApplication;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/28/2015.
 */
public class UiTweaks {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
   public void setColorInput(String color, EditText ed)
    {
        ed.getBackground().setColorFilter(Color.parseColor("#"+color), PorterDuff.Mode.SRC_ATOP);
    }

    public void hideKeyboard(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setStatusBarcolor(Activity mContext)
    {
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.LOLLIPOP) {

            Window window = mContext.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.setStatusBarColor(mContext.getResources().getColor(R.color.primary_dark));
        };
    }
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = dp * (metrics.densityDpi / 160);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(int px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160));
        return dp;
    }


    private void startOldService(Context mContext,Boolean redirect) {


    }

    public void hideTransit(View view)
    {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);

// Start the animation
        view.animate()
                .translationY(view.getHeight())
                .alpha(1.0f);
    }
    public void displayTransit(View view)
    {
        view.animate().translationY(0);
        view.setAlpha(1.0f);
    }


}
