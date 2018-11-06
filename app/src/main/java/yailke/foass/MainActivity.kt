package yailke.foass

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

//Views
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//Connection libraries
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//Exceptions
import java.io.IOException


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Button>(R.id.requestButton)
        myButton.setOnClickListener {
            val post = onbuttonclickHttpPost()
            post.execute()
        }
    }

    inner class onbuttonclickHttpPost : AsyncTask<Void, Int, String>() {
        //View Items to Change
        internal var outputView: TextView = findViewById(R.id.outputView)
        internal var name: String = ""

        override fun onPreExecute() {
            val myInput = findViewById<EditText>(R.id.inputText)
            name = myInput.text.toString()
        }

        override fun doInBackground(vararg params: Void): String {
            try {
                val url = URL("http://foaas.com/off/$name/Yagiz")
                val result = StringBuilder()
                val connection = url.openConnection() as HttpURLConnection
                // Make a get request
                connection.requestMethod = "GET"
                // Set the Accept value
                connection.addRequestProperty("Accept", "text/plain")
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                //Read by line
                var tempStr: String
                tempStr = reader.readLine()
                result.append(tempStr).append('\n')

                return result.toString()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

        }

        override fun onPostExecute(result: String) {
            outputView.text = result
        }
    }


}

