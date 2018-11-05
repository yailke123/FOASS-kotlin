package yailke.foass;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//Views
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Connection libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Exceptions
import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.requestButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onbuttonclickHttpPost post = new onbuttonclickHttpPost();
                post.execute();
            }
        });
    }

    public class onbuttonclickHttpPost extends AsyncTask<Void, Integer, String> {
        //View Items to Change
        TextView outputView;
        String name;

        @Override
        protected void onPreExecute() {
            outputView = findViewById(R.id.outputView);
            EditText myInput = findViewById(R.id.inputText);
            name = myInput.getText().toString();
        }

        @Override
        protected String doInBackground (Void...params)  {
            try {
                URL url = new URL("http://foaas.com/off/" + name +"/Yagiz");
                StringBuilder result = new StringBuilder();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Make a get request
                connection.setRequestMethod("GET");
                // Set the Accept value
                connection.addRequestProperty("Accept", "text/plain");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //Read by line
                String l;
                while ((l = reader.readLine()) != null) {
                    result.append(l).append('\n');
                }
                return result.toString();
            }catch ( IOException e){
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            outputView.setText(result);
//        Log.w("MESSAGE", result);
        }
    }


}

