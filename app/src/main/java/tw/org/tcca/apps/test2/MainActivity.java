package tw.org.tcca.apps.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;
    private MyReceiver myReceiver;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);
        cmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, filter);

    }

    private boolean isConnectNetwork(){
        NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isWifiConnected(){
        NetworkInfo networkInfo = cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.tcca.org.tw");
                    HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                    conn.connect();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    String line;
                    while ( (line = reader.readLine()) != null){
                        Log.v("bradlog", line);
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.v("bradlog", e.toString());
                }
            }
        }.start();
    }

    public void test2(View view) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.tcca.org.tw/img/q_01_01.jpg");
                    HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                    conn.connect();
                    BufferedInputStream bin =
                            new BufferedInputStream(conn.getInputStream());
                    bitmap = BitmapFactory.decodeStream(bin);
                    bin.close();
                    hander.sendEmptyMessage(0);


                } catch (Exception e) {
                    Log.v("bradlog", e.toString());
                }
            }
        }.start();
    }

    private Bitmap bitmap;
    private UIHander hander = new UIHander();
    private class UIHander extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            img.setImageBitmap(bitmap);
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.v("bradlog", "OK");
            Log.v("bradlog", "1. " + isConnectNetwork());
            Log.v("bradlog", "2. " + isWifiConnected());
        }
    }

}