package circle.com.circle.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import circle.com.circle.Interfaces.VolleyResponse;
import circle.com.circle.MyApplication;
import circle.com.circle.Network.circleUser;
import circle.com.circle.Network.circlesVolley;
import circle.com.circle.R;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private Button google;
    private int RC_SIGN_IN=2200;
    private CallbackManager mCallbackmanager;
    private LoginButton loginButton;
    Button b4;
    private circleUser mCirclesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(MyApplication.getAppContext());
        setContentView(R.layout.activity_main);
        google=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);


        mCirclesUser=new circleUser();

        setUpGoogle();

        setUpFacebook();

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });


    }
    /*facebook*/
    public void setUpFacebook()
    {
        //Use this to logout LoginManager.getInstance().logOut();
        //Facebook
        mCallbackmanager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.login_button);
        //loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(mCallbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        final String accessToken = loginResult.getAccessToken()
                                .getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

                                        try {

                                            object.put("oauth_type", "facebook_auth");
                                            object.put("token", accessToken);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        mCirclesUser.initiate_login_oauth(object,MainActivity.this);
                                        //Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_LONG).show();


                                    }


                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {

                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

    }
    
    /*Google*/
    public void setUpGoogle()
    {
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }
    private void signIn() {
        google.setText("Please wait");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else
        {
            mCallbackmanager.onActivityResult(requestCode, resultCode, data);//facebook

        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Check", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


            JSONObject object=new JSONObject();

            String google_email=acct.getEmail();
           String google_name=acct.getDisplayName();
            String google_id = acct.getId();

            try {
                object.put("email",google_email);
                  object.put("name",google_name);
                object.put("oauth_type","google_auth");
                object.put("id", google_id);



                mCirclesUser.initiate_login_oauth(object,MainActivity.this);
                Toast.makeText(MyApplication.getAppContext(), "Login Success", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            // Signed out, show unauthenticated UI.

        }
    }
    public void handleOauthLogin(JSONObject object, Boolean truth, int rets_id, Boolean error)
    {

         b4.setText("Continue with Facebook");
        google.setText("Continue with Google");
        if(!error) {
            if (truth) {

                    mCirclesUser.initiateUser(rets_id,MyApplication.getAppContext());



               /* Intent i = new Intent(MyApplication.getAppContext(), profile.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                */

            } else {

                String o_email = "";
                String o_name = "";
                String o_id = "";
                String oauth_type = "";

                try {

                   oauth_type = object.getString("oauth_type");
                    o_email = object.getString("email");
                    o_name = object.getString("name");
                    o_id = object.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(MyApplication.getAppContext(), username.class);
                i.putExtra("email", o_email);
                i.putExtra("full", o_name);
                i.putExtra("type", oauth_type);
                i.putExtra("id", o_id);

                startActivity(i);


            }
        }
        else {
            Toast.makeText(MyApplication.getAppContext(),"Authentication Error ",Toast.LENGTH_LONG).show();
        }
    }

}
