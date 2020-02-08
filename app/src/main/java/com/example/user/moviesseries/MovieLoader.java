package com.example.user.moviesseries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MovieLoader extends AsyncTaskLoader<Type> {
    String url;
    public MovieLoader(@NonNull Context context,String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public Type loadInBackground() {
        String jsonResponse=null;
        //Log.i("Loader1","Load in background Error");

        try {
            jsonResponse=makeHttpConnection(new URL(url));
            }
            catch (MalformedURLException e) {
                //Log.i("Loader2","Load in background Error");

                e.printStackTrace();
            }

        final Type movieDetails=extractFromJson(jsonResponse);

        if(!movieDetails.getImage().equals("N/A")){
            InputStream inputStream = null;
            Bitmap micon=null;
            try {
                inputStream = new URL(movieDetails.getImage()).openStream();
                micon = BitmapFactory.decodeStream(inputStream);
                movieDetails.Background=micon;
            } catch (IOException e) {
                //Log.i("Loader3","Load in background Error");

                e.printStackTrace();
            }
        }

        return movieDetails;
    }



    private String makeHttpConnection(URL url) {
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        String jsonResponse="";
        StringBuilder line=new StringBuilder();
        try {
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.connect();


            inputStream=urlConnection.getInputStream();

            InputStreamReader inReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffer=new BufferedReader(inReader);
            String sline=buffer.readLine();
            while(sline!=null){
                line.append(sline);
                sline=buffer.readLine();
            }

            return line.toString();

        } catch (IOException e) {
           // Log.i("Loader4","Load in background Error");

            e.printStackTrace();
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //Log.i("Loader5","Load in background Error");

                    e.printStackTrace();
                }
            }
        }


        return line.toString();
    }

    private Type extractFromJson(String Json){
        try {
            JSONObject jsonObject = new JSONObject(Json);
            String title = jsonObject.getString("Title");
            String gener = jsonObject.getString("Genre");
            String year = jsonObject.getString("Year");
            String time = jsonObject.getString("Runtime");
            String box = jsonObject.getString("BoxOffice");
            String typeo = jsonObject.getString("Type");
            String cuntry = jsonObject.getString("Country");
            String language = jsonObject.getString("Language");
            String awards = jsonObject.getString("Awards");
            String rate = jsonObject.getString("imdbRating");
            String image = jsonObject.getString("Poster");

            return new Type(title, gener, rate, year, time, cuntry, language, typeo, awards, box, image);

        } catch (JSONException j) {
            //Log.i("Loader6","Load in background Error");

        }
        return null;
    }
}
