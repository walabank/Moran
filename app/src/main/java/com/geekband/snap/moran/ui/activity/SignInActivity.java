package com.geekband.snap.moran.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.geekband.snap.moran.ApplicationContext;
import com.geekband.snap.moran.R;
import com.geekband.snap.moran.util.AESEncryptor;
import com.geekband.snap.moran.util.NetworkStatus;
import com.geekband.snap.moran.util.StreamUtil;
import com.geekband.snap.moran.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class SignInActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmail;
    private TextView mPassword;
    private Button mSignIn;
    private Button mSignUp;
    private ApplicationContext mAppContext;
    private static final String mPATH ="/user/login";
    private static final int SUCCESS = 1;
    private static final int ERROR = 0;
    private static final int UNCONNECTED = -1;
    private Handler mHandler;
    {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UNCONNECTED:
                        Toast.makeText(getApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        Intent intent = new Intent(SignInActivity.this,SquareActivity.class);
                        startActivity(intent);
                        break;
                    case ERROR:
                        Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }
    private View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sign_in_button:
                    signIn();
                    break;
                case R.id.sign_up_button:
                    signUp();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = (AutoCompleteTextView) findViewById(R.id.email);
        mPassword = (TextView) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.sign_in_button);
        mSignUp = (Button) findViewById(R.id.sign_up_button);
        mSignIn.setOnClickListener(listener);
        mSignUp.setOnClickListener(listener);
        mAppContext = (ApplicationContext) getApplication();
    }
    private void signUp(){
        Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
    private void signIn() {
        mEmail.setError(null);
        mPassword.setError(null);

        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        boolean isValid = true;
        View focusView = null;

        if(TextUtils.isEmpty(email)){
            mEmail.setError(getString(R.string.error_empty_email));
            focusView=mEmail;
            isValid = false;
        }else if(StringUtil.isEmail(email)==false){
            mEmail.setError(getString(R.string.error_pattern_email));
            focusView = mEmail;
            isValid = false;
        }

        if(TextUtils.isEmpty(password)){
            mPassword.setError(getString(R.string.error_empty_password));
            focusView=mPassword;
            isValid=false;
        }else if(StringUtil.isPasswordValid(password)==false){
            mPassword.setError(getString(R.string.error_length_password));
            focusView=mPassword;
            isValid=false;
        }
        if(isValid == false){
            focusView.requestFocus();
        }else if(NetworkStatus.isNetworkConnected(getApplicationContext())){
               new Thread() {
                   @Override
                   public void run() {
                       try {
                           String pwd = StringUtil.getMD5(password).substring(0, 20);
                           String gbid = "G2015020163";
                           JSONObject user = new JSONObject();
                           user.put("email", email);
                           user.put("password", pwd);
                           user.put("gbid", gbid);

                           String url = mAppContext.getUrl(mPATH);
                           doPostRequest(url, user);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               }.start();
           }else {
               Message msg = Message.obtain();
               msg.what = UNCONNECTED;
               msg.obj = getString(R.string.unavailable_network_connection);
               mHandler.sendMessage(msg);
           }


        }

    private void doPostRequest(String path, JSONObject data) {
        try {
            byte[] entity = data.toString().getBytes("UTF-8");
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(entity.length));
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(entity);
            outputStream.close();


            int responseCode = connection.getResponseCode();
            if(responseCode == 200)
            {
                InputStream inputStream = connection.getInputStream();
                byte[] is = StreamUtil.readInputStream(inputStream);
                String json = new String(is);

                JSONObject jsonObject = new JSONObject(json);
                JSONObject myData = jsonObject.getJSONObject("data");
                int myId = myData.getInt("user_id");
                String myToken = myData.getString("token");

                SharedPreferences sp = getSharedPreferences("moran", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user_id", AESEncryptor.encrypt(getString(R.string.random_seed), String.valueOf(myId)));
                editor.putString("token",AESEncryptor.encrypt(getString(R.string.random_seed),myToken));
                editor.commit();

                Message msg = Message.obtain();
                msg.what = SUCCESS;
                msg.obj = json;
                mHandler.sendMessage(msg);
                }else {
                   Message msg = Message.obtain();
                   msg.what=ERROR;
                   mHandler.sendMessage(msg);
                }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
