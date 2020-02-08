package com.example.user.moviesseries;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Result extends AppCompatActivity /*implements LoaderCallbacks<Type>*/ {

    ScrollView scrollView;
    Type movieDetails;
    private static String URLink = "http://www.omdbapi.com/?i=tt3896198&apikey=951d3c60&t=";
    private static String MovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        final boolean connectedOrConnecting = info!=null && info.isConnectedOrConnecting();
        info.getType();

        Intent i = getIntent();
        MovieName = i.getStringExtra("name");

        scrollView = findViewById(R.id.scroll);
        //getLoaderManager().initLoader(0, null, this);
        scrollView.setVisibility(View.INVISIBLE);
        DownloadClass downloadClass=new DownloadClass(URLink+MovieName);
        downloadClass.execute();

    }



    /*

    @Override
    public Loader<Type> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, URLink);
    }

    @Override
    public void onLoadFinished(Loader<Type> loader, Type type2) {
        if (type2.Background != null) {
            scrollView.setBackground(new BitmapDrawable(type2.Background));
        }

        UpdateUi(type2);

    }

    @Override
    public void onLoaderReset(Loader<Type> loader) {

    }

    private void UpdateUi(Type movie) {
        TextView textView = findViewById(R.id.title);
        textView.setText(movie.getTitle());

        TextView rate = findViewById(R.id.rate);
        rate.setText(movie.getRate());

        TextView time = findViewById(R.id.time);
        time.setText(movie.getTime());

        TextView year = findViewById(R.id.year);
        year.setText(movie.getYear());

        TextView gener = findViewById(R.id.gener);
        gener.setText(movie.getGener());

        TextView type = findViewById(R.id.type);
        type.setText(movie.getType());

        TextView cuntry = findViewById(R.id.cuntry);
        cuntry.setText(movie.getCuntry());

        TextView lang = findViewById(R.id.language);
        lang.setText(movie.getLanguage());

        TextView award = findViewById(R.id.award);
        award.setText(movie.getAward());

        TextView box = findViewById(R.id.box);
        box.setText(movie.getBox());

    }



*/




    private class DownloadClass extends AsyncTask<URL,Void,Type> {

        private String name;

        private DownloadClass(String name) {
            this.name = name;
        }

        @Override
        protected void onPostExecute(Type movieDetails) {

            if (movieDetails == null) return;

            if (movieDetails.getImage() != "N/A") {
                ImageView imageView = new ImageView(getApplicationContext());
                DownloadImage image = new DownloadImage(imageView);
                image.execute(movieDetails.getImage());
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            View progress2=findViewById(R.id.Progress);
            progress2.setVisibility(View.GONE);


            ((TextView)findViewById(R.id.error)).setText("Can't find Movie with this Nmae");

            UpdateUi(movieDetails);
            scrollView.setVisibility(View.VISIBLE);


        }

        @Override
        protected Type doInBackground(URL... urls) {
            URL url = null;
            String JsonResponse = "";
            try {
                url = new URL(name);

                try {
                    JsonResponse = makeHTTPrequest(url);
                } catch (Exception e) {
                    //Log.v("Do in back1", e.getMessage());
                }
            } catch (MalformedURLException e) {
                System.out.print(e);
                //Log.v("Do in back2", e.getMessage());
            }

            final Type movieDetails = extractFromJson(JsonResponse);


            return movieDetails;
        }

        private void UpdateUi(Type movie) {

            TextView textView = findViewById(R.id.title);
            textView.setText(movie.getTitle());

            TextView rate = findViewById(R.id.rate);
            rate.setText(movie.getRate());

            TextView time = findViewById(R.id.time);
            time.setText(movie.getTime());

            TextView year = findViewById(R.id.year);
            year.setText(movie.getYear());

            TextView gener = findViewById(R.id.gener);
            gener.setText(movie.getGener());

            TextView Type = findViewById(R.id.type);
            Type.setText(movie.getType());

            TextView cuntry = findViewById(R.id.cuntry);
            cuntry.setText(movie.getCuntry());

            TextView lang = findViewById(R.id.language);
            lang.setText(movie.getLanguage());

            TextView award = findViewById(R.id.award);
            award.setText(movie.getAward());

            TextView box = findViewById(R.id.box);
            box.setText(movie.getBox());

        }

        public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

            ImageView bimage;

            public DownloadImage(ImageView bimage) {
                this.bimage = bimage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    bimage.setImageBitmap(bitmap);
                    ScrollView sc = findViewById(R.id.scroll);
                    sc.setBackground(new BitmapDrawable((bitmap)));
                }
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = strings[0];
                Bitmap micon = null;
                InputStream inputStream = null;
                try {
                    //Toast.makeText(getApplicationContext(),"Image download error..",Toast.LENGTH_LONG).show();
                    inputStream = new URL(url).openStream();
                    micon = BitmapFactory.decodeStream(inputStream);
                } catch (Exception n) {

                }
                return micon;
            }

        }


        private String makeHTTPrequest(URL url) throws Exception {
            String JsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(1500);
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);
            } catch (Exception e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    ;
                }
                if (inputStream != null)
                    inputStream.close();
            }

            return JsonResponse;
        }


        public String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

            return output.toString();
        }

        public Type extractFromJson(String Json) {
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

            }
            return null;
        }
    }
}


