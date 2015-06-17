/*
 * @author: Dennis Gehrig
 * @date:   16. Juni 2015
 */
package ch.zkb.m335.parkalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ch.zkb.m335.parkalarm.ch.zkb.m335.parkalarm.services.MyService;


public class RunningMainActivity extends Activity {
    private ParkCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private long timeElapsed;
    private TextView text;
    private TextView timeElapsedView;
    private Button stopTimer;
    private long duration;

    private final long startTime = 60000;
    private final long interval  = 100;

    SerializeHelper sh = new SerializeHelper();
    private ParkInfo pi = sh.deserializeParkInfo(getApplicationContext());

    @Override
    public void onBackPressed(){
        Toast.makeText(this, "Diesen Button können Sie momentan nicht verwenden.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_main);
        if(pi != null){
            duration = pi.getDuration();
        }
//        startTime mit duration austauschen
        countDownTimer = new ParkCountDownTimer(startTime, interval);
        stopTimer = (Button) this.findViewById(R.id.startTimer);

        text = (TextView) this.findViewById(R.id.timer);
        timeElapsedView = (TextView) this.findViewById(R.id.timeElapsed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_running_main, menu);
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

    public void goToMap(View v){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void startTimer(View v){
        startService(new Intent(getBaseContext(), MyService.class));
        if (!timerHasStarted)
        {
            Log.d("startTimer-Method", "startTimer-Method");
            countDownTimer.start();
            timerHasStarted = true;
            //stopTimer.setText("Start Timer");
        }
        else
        {
            countDownTimer.cancel();
            timerHasStarted = false;
            //stopTimer.setText("RESET");
        }
    }

    // CountDownTimer class
    public class ParkCountDownTimer extends CountDownTimer
    {

        public ParkCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            //Alarm einbauen
            stopTimer.setText("Time remain:" + 0);
            text.setText("Time's up!");
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
            stopService(new Intent(getBaseContext(), MyService.class));
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            double d;
            long minutesUntilFinished;
            d = (double)millisUntilFinished / 1000;
            minutesUntilFinished = Math.round(d) / 60;
            if(minutesUntilFinished != (startTime/60000)){
                minutesUntilFinished++;
            }
            stopTimer.setText("Time remain:" + minutesUntilFinished);
            timeElapsed = startTime - millisUntilFinished;
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        }
    }

//    public void startService(View v){
//        startService(new Intent(getBaseContext(), MyService.class));
//    }
//
//    public void stopService(View v){
//        stopService(new Intent(getBaseContext(), MyService.class));
//    }

}
