package circle.com.circle.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import circle.com.circle.Library.SharedPrefs;
import circle.com.circle.Network.circleUser;
import circle.com.circle.R;

/**
 * Created by skmishra on 12/14/2016.
 */
public class username extends Activity {

    private SharedPrefs mSharedPrefs;
    private String oauth_type,oauth_emai,oauth_name,oauth_id;
    private TextView header;
    private EditText roll_ed;
    private circleUser mCirclesUser;
    private ProgressDialog mProgressDialog;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);
        Intent i=getIntent();
        oauth_type=i.getExtras().getString("type");
        oauth_emai=i.getExtras().getString("email");
        oauth_name=i.getExtras().getString("full");
        oauth_id=i.getExtras().getString("id");
       mSharedPrefs=new SharedPrefs();
        header=(TextView)findViewById(R.id.headerUsername);
        String fName=oauth_name.split(" ")[0];
        header.setText("Last thing , "+fName);
        roll_ed=(EditText)findViewById(R.id.roll_no);
        mCirclesUser=new circleUser();
    }
    public void register(View view)
    {
        mProgressDialog= ProgressDialog.show(this, "", "Please wait, Registering you");
        username=roll_ed.getText().toString();
        mCirclesUser.register(oauth_emai,username,oauth_type,oauth_id,oauth_name,this);

    }
    public void BackPressed(View view)
    {
        super.onBackPressed();
    }

    public void onSuccess(String mFunc) throws JSONException {

        mProgressDialog.dismiss();
        JSONObject mJSON=new JSONObject(mFunc);
        Boolean canPass=mJSON.getBoolean("state");
        String message=mJSON.getString("message");
        if(canPass)
        {
            Toast.makeText(this,"Registered Successfully!",Toast.LENGTH_LONG).show();
            Intent i =new Intent(this,circles_init.class);
            startActivity(i);
            //ShowPremierPlatesPage
            //Proceed to Profile or Main Page

        }
        else
        {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }


    }
}
