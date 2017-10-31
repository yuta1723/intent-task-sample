package com.example.yutan.intentandactivitysample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private String ACTION_1 = "action1";
    private String ACTION_2 = "action2";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive : " + intent.getAction());
            Toast.makeText(getApplicationContext(),"hoge",Toast.LENGTH_LONG);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, NotificationSample.class));

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSyncBroadcast();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_2);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("", "End Activity");
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }

    private void sendSyncBroadcast() {

        Intent i = new Intent();
        i.setAction(ACTION_1);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcastSync(i);
    }

}

