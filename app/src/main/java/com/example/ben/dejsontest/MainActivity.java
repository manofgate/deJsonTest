package com.example.ben.dejsontest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<Book> books;
    private  ListView lv;

    public void run(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
        @Override public void onFailure(Request request,  IOException e) {
            e.printStackTrace();
        }

            @Override public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                 InputStream responseS = response.body().byteStream();
                String result = convert(responseS);
               // System.out.println(response.body().string());
                try {
                    JSONArray  jsonarray = new JSONArray(result);
                    Log.v("mine", "size " + jsonarray.length());
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject obj = jsonarray.getJSONObject(i);
                       // Log.v("title", obj.toString());
                        String title = "";
                        if(!obj.isNull("title")){
                            title = obj.getString("title");
                        }
                        String imageURL = "";
                        if(!obj.isNull("imageURL")) {
                            imageURL = obj.getString("imageURL");
                        }
                        String author = "";
                        if(!obj.isNull("author")) {

                            author = obj.getString("author");
                        }
                        Book b = new Book(imageURL, title, author);
                        books.add(b);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateList();
                    }
                });
                }
            });
    }
    public String convert(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void updateList(){

        Log.v("MAINACTSize", "hello " + books.size());
        bookCustomAdapter customAdapter = new bookCustomAdapter(this,books);
        lv.setAdapter(customAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        books = new ArrayList<Book>();
        try {
             run("http://de-coding-test.s3.amazonaws.com/books.json");

        } catch (Exception e){
            System.out.println("io error");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
