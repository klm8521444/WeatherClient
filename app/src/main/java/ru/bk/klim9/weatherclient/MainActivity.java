package ru.bk.klim9.weatherclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String QUERY_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    TextView nameCity;
    Button sendRequest;
    EditText editText;
    String mAnswer = "";
    JSONObject mJsonObject;
    String[] mDataArray;
    String lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameCity = (TextView) findViewById(R.id.namecity);
        sendRequest = (Button) findViewById(R.id.button);
        editText =(EditText) findViewById(R.id.editText);

        sendRequest.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        queryWeather(editText.getText().toString());

    }

    private void queryWeather(String searchString) {

        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        setProgressBarIndeterminateVisibility(true);

        client.get(QUERY_URL + urlString + "&units=metric",
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        setProgressBarIndeterminateVisibility(false);

                        Toast.makeText(getApplicationContext(), "Success!",
                                Toast.LENGTH_LONG).show();

                        mJsonObject = jsonObject;
                        mDataArray = new String[26];

                        try {
                            JSONObject wind = jsonObject.getJSONObject("wind");
                            mDataArray[13] = wind.getString("speed");
                            mDataArray[14] = wind.getString("deg");
                            JSONObject clouds = jsonObject.getJSONObject("clouds");
                            mDataArray[15] = clouds.getString("all");
                            mDataArray[16] = jsonObject.getString("dt");
                            JSONObject sys = jsonObject.getJSONObject("sys");
                            mDataArray[17] = sys.getString("type");
                            mDataArray[18] = sys.getString("id");
                            mDataArray[19] = sys.getString("message");
                            mDataArray[20] = sys.getString("country");
                            mDataArray[21] = sys.getString("sunrise");
                            mDataArray[22] = sys.getString("sunset");
                            mDataArray[23] = jsonObject.getString("id");
                            mDataArray[24] = jsonObject.getString("name");
                            mDataArray[25] = jsonObject.getString("cod");

                            JSONObject coord = jsonObject.getJSONObject("coord");
                            mDataArray[0] = coord.getString("lon");
                            mDataArray[1] = coord.getString("lat");
                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject id = (JSONObject) jsonArray.get(0);
                            JSONObject main = (JSONObject) jsonArray.get(0);
                            JSONObject description = (JSONObject) jsonArray.get(0);
                            JSONObject icon = (JSONObject) jsonArray.get(0);
                            mDataArray[2] = id.getString("id");
                            mDataArray[3] = main.getString("main");
                            mDataArray[4] = description.getString("description");
                            mDataArray[5] = icon.getString("icon");
                            mDataArray[6] = jsonObject.getString("base");
                            JSONObject mainObject = jsonObject.getJSONObject("main");
                            mDataArray[7] = mainObject.getString("temp");
                            mDataArray[8] = mainObject.getString("pressure");
                            mDataArray[9] = mainObject.getString("humidity");
                            mDataArray[10] = mainObject.getString("temp_min");
                            mDataArray[11] = mainObject.getString("temp_max");
                            mDataArray[12] = jsonObject.getString("visibility");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("array", mDataArray);
                        startActivity(intent);



                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        setProgressBarIndeterminateVisibility(false);

                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " +
                                throwable.getMessage(), Toast.LENGTH_LONG).show();

                        Log.e("omg android", statusCode + " " + throwable.getMessage());
                    }
                }
        );

    }


}
