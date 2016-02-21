package netidevs.neti_android;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

import netidevs.neti_android.models.DrugModel;

public class MainActivity extends AppCompatActivity {

    private ListView subjectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectView = (ListView)findViewById(R.id.drugSubjects); //connecting the list view
        new JSONTask().execute("https://neti-android.firebaseio.com/.json"); //calling JSON data
    }

    //This JSONTask class can be called by " new JSONTask().execute("url.json");"
    //use this to get the RAW JSON data
    class JSONTask extends AsyncTask<String, String, List<DrugModel.subjects.Drugs>>{
        @Override
        protected List<DrugModel.subjects.Drugs> doInBackground(String... params) {
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
                JSONArray middleObject = parentObject.getJSONArray("subjects");

                List<DrugModel.subjects.Drugs> DrugList = new ArrayList<>();

                for(int i = 0; i < middleObject.length(); i++) { //setting data on the subject layer
                    DrugModel.subjects.Drugs drugModel = new DrugModel.subjects.Drugs();
                    JSONObject finalObject = middleObject.getJSONObject(i);  //set the index
                    drugModel.setName(finalObject.getString("name"));             //set drug name
                    drugModel.setToxicity(finalObject.getString("toxicity"));     //set toxicity level

                    JSONArray tagsObject = finalObject.getJSONArray("tags");
                    List<DrugModel.subjects.Drugs.tags> TagList = new ArrayList<>();
                    for(int j = 0; j < tagsObject.length(); j++){                 //setting data on the tag layer
                        DrugModel.subjects.Drugs.tags Tags = new DrugModel.subjects.Drugs.tags();
                        Tags.setTagName(tagsObject.getString(j));  //set tag name
                        TagList.add(Tags);       // add tag to the Taglist
                    }
                    drugModel.setTagList(TagList);   //set the object list = the instance list

                    JSONArray topicsObject = finalObject.getJSONArray("topics");
                    List<DrugModel.subjects.Drugs.topics> TopicList = new ArrayList<>();
                    for(int k = 0; k < topicsObject.length(); k++){                       //setting data on the topic layer
                        DrugModel.subjects.Drugs.topics Topics = new DrugModel.subjects.Drugs.topics();
                        JSONObject topicsInterior = topicsObject.getJSONObject(k);
                        Topics.setDescription(topicsInterior.getString("description"));  //add topic description
                        Topics.setTitle(topicsInterior.getString("title"));              //add topic title
                        TopicList.add(Topics);                                          //add topics to list
                    }
                    drugModel.setTopicList(TopicList);  //set the object list = the instance list
                    DrugList.add(drugModel);           //add the whole drug to a list
                   drugModel.setDrugList(DrugList);   //set the object list = the instance list
                }

                return DrugList;  //return subjects in JSON

            } catch (IOException | JSONException e) {
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
        protected void onPostExecute(List<DrugModel.subjects.Drugs> result){
            super.onPostExecute(result);
            drugAdapter adapter = new drugAdapter(getApplicationContext(), R.layout.row, result);
            subjectView.setAdapter(adapter);
        }
    }

    public class drugAdapter extends ArrayAdapter{

        private List<DrugModel.subjects.Drugs> drugModelList;
        private int resource;
        private LayoutInflater inflater;
        public drugAdapter(Context context, int resource, List<DrugModel.subjects.Drugs> objects) {
            super(context, resource, objects);
            drugModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(resource, null);
            }
            TextView Subject = (TextView)convertView.findViewById(R.id.Subject);
            TextView Tags = (TextView)convertView.findViewById(R.id.Tags);
            TextView Titles = (TextView)convertView.findViewById(R.id.Titles);
            TextView Toxicity = (TextView)convertView.findViewById(R.id.Toxicity);
            TextView Description = (TextView)convertView.findViewById(R.id.Descriptions);

            Subject.setText(drugModelList.get(position).getName());  //set the subject

            StringBuffer tagBuffer = new StringBuffer();
            for(DrugModel.subjects.Drugs.tags tag : drugModelList.get(position).getTagList()){   //for each loop sets Tags
                tagBuffer.append(tag.getTagName() + ", ");
            }
            Tags.setText(tagBuffer.toString());

            StringBuffer titleBuffer = new StringBuffer();
            for(DrugModel.subjects.Drugs.topics title : drugModelList.get(position).getTopicList()) {  //for each loop sets titles
                titleBuffer.append(title.getTitle());
            }
            Titles.setText(titleBuffer.toString());

            Toxicity.setText(drugModelList.get(position).getToxicity());   //set the toxicity

            StringBuffer descriptionBuffer = new StringBuffer();
            for(DrugModel.subjects.Drugs.topics description : drugModelList.get(position).getTopicList()) {  //for each loop sets titles
                titleBuffer.append(description.getDescription() + "\n");
            }
            Description.setText(descriptionBuffer.toString());

            return convertView;
        }
    }
}