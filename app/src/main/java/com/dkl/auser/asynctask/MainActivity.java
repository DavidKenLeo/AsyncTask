package com.dkl.auser.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView jpg;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jpg = (ImageView)findViewById(R.id.jpg);


        new asynctask().execute("https://upload.wikimedia.org/wikipedia/commons/7/7c/Hawk_eating_prey.jpg");

    }

    private class asynctask extends AsyncTask<String, Integer, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Tag onPreExecute", String.valueOf(Thread.currentThread().getId()));
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setMessage("Loading...");
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.d("Tag onPostExecute", String.valueOf(Thread.currentThread().getId()));
            jpg.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("Tag onProgressUpdate", String.valueOf(Thread.currentThread().getId()));
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("Tag doInBackground", String.valueOf(Thread.currentThread().getId()));
            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            if(params[0].equals("start"))
                int i;
                for( i=0;i<=100;i++){
                    try {
                        Thread.sleep(100);
                        publishProgress(i);
                        //tyPercent.setText(i+"%");不可以寫在這邊
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }
    }
}