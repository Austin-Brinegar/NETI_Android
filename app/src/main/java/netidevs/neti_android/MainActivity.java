package netidevs.neti_android;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView drugData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drugData = (TextView) findViewById(R.id.drugJSON);
        new JSONTask().execute("https://neti.firebaseio.com/.json"); //calling JSON data
    }

    //This JSONTask class can be called by " new JSONTask().execute("url.json");"
    //use this to get the RAW JSON data
    class JSONTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();   //setup connection to JSON server
                connection.connect();
                InputStream stream = connection.getInputStream();        // create input stream to read data
                reader = new BufferedReader(new InputStreamReader(stream)); //buffered read for input stream
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {   //read all the JSON data
                    buffer.append(line);
                }
                String finalJson = buffer.toString();          // begin parsing JSON subjects here
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject middleObject = parentObject.getJSONObject("subjects");
                StringBuffer finalBufferData = new StringBuffer();  //buffer for data that is in for loop
                for(int i = 0; i < middleObject.length()-1; i++) {   //CRITICAL NOTE: WHEN SYNCING WITH REAL DATABASE, REMOVE THE -1
                    JSONObject finalObject = middleObject.getJSONObject(""+i);
                    String subject = finalObject.getString("name");
                    finalBufferData.append(subject+ "\n");
                }
                return finalBufferData.toString();  //return subjects in JSON

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            drugData.setText(result);
        }
    }
}