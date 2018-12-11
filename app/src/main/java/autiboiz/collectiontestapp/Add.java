package autiboiz.collectiontestapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Add extends AppCompatActivity {

    private EditText mBarcodeView;
    private UserAddTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);


        mBarcodeView = (EditText) findViewById(R.id.Barcode);
    }

    public void attemptAddGame(View view) {


        // Store values at the time of the login attempt.

        String barcode;
        barcode = mBarcodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(barcode)) {
            mBarcodeView.setError(getString(R.string.error_field_required));
            focusView = mBarcodeView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // !important ADD CODE TO FOR VERIFICATION HERE!!
//            Log.d(LOG_TAG, "Button clicked!");
            Log.d("User is being logged in", "Sending data to Database!");
            mAuthTask = new Add.UserAddTask(barcode);
            mAuthTask.execute((Void) null);
        }
    }

    public class UserAddTask extends AsyncTask<Void, Void, Boolean> {

        private final String mBarcode;

        UserAddTask(String barcode ) {
            mBarcode = barcode;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service
            sendPost(mBarcode);
            return true;
        }

    }


    public void sendPost(final String barcode) {
        Thread thread = new Thread(new Runnable() {
            String response;

            @Override

            public void run() {
                try {
                    Log.d("database", "Sending data to Database!");
                    String Link = "https://game-collections.herokuapp.com/games/add";
                    URL url = new URL(Link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userToken", LoginActivity.userToken);
                    jsonParam.put("userEmail", barcode);
                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("LOGINSTS", String.valueOf(conn.getResponseCode()));
                    Log.i("LOGINMSG" , conn.getResponseMessage());

                    int responseCode=conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line=br.readLine())  != null) {
                            Log.i("LOGINBR" , line);

                            response = line;
                        }
                    }



                    if(conn.getResponseCode() != 401) {
                        JSONObject jObject = new JSONObject(response);

                        String jToken = jObject.getString("token");

                        Object loginDialog = new LogInSuccesFull();
                        ((LogInSuccesFull) loginDialog).show(getSupportFragmentManager(), "Test");
                    }
                    else{
                        Object loginDialog = new LogInFailed();
                        ((LogInFailed) loginDialog).show(getSupportFragmentManager(), "Test");
                    }







                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
