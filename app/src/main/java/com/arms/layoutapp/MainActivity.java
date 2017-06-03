package com.arms.layoutapp;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private Button stpButton;
    private Button strButton;
    private Button retButton;

    private long startTime;
    private long continuationTime = 0l;
    private TextView currentTimerLabel;

    private Handler handler = new Handler();
    private  Runnable targetTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stpButton = (Button) findViewById(R.id.stpButton);
        strButton = (Button) findViewById(R.id.strButton);
        retButton = (Button) findViewById(R.id.retButton);

        changeButtonStatus(true, false, false);
        currentTimerLabel = (TextView)findViewById(R.id.currentTimerLabel);



    }

    public void timeStart(View view){
        startTime = SystemClock.elapsedRealtime();
        targetTime = new Runnable() {
            @Override
            public void run() {
                long ct = SystemClock.elapsedRealtime() - startTime + continuationTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
                currentTimerLabel.setText(sdf.format(ct));
                handler.removeCallbacks(targetTime);
                handler.postDelayed(targetTime,100);
            }
        };
        handler.postDelayed(targetTime,100);
        changeButtonStatus(false, true, false);


    }
    public void timeStop(View view){
        continuationTime = continuationTime + SystemClock.elapsedRealtime() - startTime;
        handler.removeCallbacks(targetTime);
        changeButtonStatus(true, false, true);
    }

    public void timeReset(View view){
        continuationTime = 0l;
        currentTimerLabel.setText(R.string.time_label);
        changeButtonStatus(true, false, false);
    }


    public  void changeButtonStatus(boolean start, boolean stop, boolean reset){
        strButton.setEnabled(start);
        stpButton.setEnabled(stop);
        retButton.setEnabled(reset);

    }
}
