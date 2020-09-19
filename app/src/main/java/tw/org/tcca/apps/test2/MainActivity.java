package tw.org.tcca.apps.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.v("bradlog", "1. " + isConnectNetwork());
        Log.v("bradlog", "2. " + isWifiConnected());
    }

    private boolean isConnectNetwork(){
        NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isWifiConnected(){
        NetworkInfo networkInfo = cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }
}