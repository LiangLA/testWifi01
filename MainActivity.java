package com.example.liangao.testwifi01;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.Calendar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager mWifiManager;
    //private List<ScanResult> mWifiList;
    private TextView textView1,textView2,textView3,textView4;
    private Button bt_scan;
    boolean found = false;
    int start_min,start_sec,start_msec,end_min,end_sec,end_msec;
    StringBuilder sBuilder_find = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        textView1 = findViewById(R.id.textView1);
        textView1.setMovementMethod(ScrollingMovementMethod.getInstance());
        bt_scan = findViewById(R.id.bt_scan_wifi);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StringBuilder sBuilder = new StringBuilder();

                if(!found){
                    if(mWifiManager !=null){
                        mWifiManager.startScan();
                        List<ScanResult> wifiResults = mWifiManager.getScanResults();
                        //Toast.makeText(MainActivity.this, "Scanning!", Toast.LENGTH_SHORT).show();
                        if (wifiResults != null && wifiResults.size() != 0) {
                            for (int i = 0; i < wifiResults.size(); i++) {
                                ScanResult wifi = wifiResults.get(i);
                                sBuilder.append("Wifi name"+wifi.SSID+"\n");
                                sBuilder.append("level:"+wifi.level+"\n");
                                //System.out.println(wifi.BSSID);
                                if(wifi.SSID.equals("Ao's iphone")){
                                    found = true;
                                    Toast.makeText(MainActivity.this, "Found it !", Toast.LENGTH_SHORT).show();
                                    // stop timing
                                    Calendar ca2 = Calendar.getInstance();
                                    end_min = ca2.get(Calendar.MINUTE);
                                    end_sec = ca2.get(Calendar.SECOND);
                                    end_msec = ca2.get(Calendar.MILLISECOND);
                                    String time_end = "End Time:"+Integer.toString(end_min)+":"+Integer.toString(end_sec)+"."+Integer.toString(end_msec);
                                    sBuilder_find.append("Wifi name："+wifi.SSID + "\n");
                                    sBuilder_find.append("level:"+wifi.level+"\n");
                                    textView3.setText(time_end);
                                    int all_msec_start = (start_min*60+start_sec)*1000+start_msec;
                                    int all_msec_end = (end_min*60+end_sec)*1000+end_msec;
                                    int time_cost = all_msec_end-all_msec_start;
                                    String time_interval = "Time Cost:"+Integer.toString(time_cost)+"ms";
                                    textView4.setText(time_interval);
                                    break;
                                }
                                else {
                                    continue;
                                }
                            }
                            textView1.setText(sBuilder);
                        } else {
                            Toast.makeText(MainActivity.this, "please check whether Wifi is on", Toast.LENGTH_LONG).show();
                            sBuilder.append("no accessible Wifi，please check whether Wifi is on");
                        }

                    }
                }
                else {
                    textView1.setText(sBuilder_find);
                }

                handler.postDelayed(this,500);
            }
        };

        bt_scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                found = false;
                Toast.makeText(MainActivity.this, "Start timing now!", Toast.LENGTH_SHORT).show();
            // click the 'start scan wifi' and start timing
                Calendar ca1 = Calendar.getInstance();
                start_min = ca1.get(Calendar.MINUTE);
                start_sec = ca1.get(Calendar.SECOND);
                start_msec = ca1.get(Calendar.MILLISECOND);
                String time_start = "Start Time:"+Integer.toString(start_min)+":"+Integer.toString(start_sec)+"."+Integer.toString(start_msec);
                textView2.setText(time_start);
                textView3.setText("end time");
                textView4.setText("time cost");
                sBuilder_find.delete(0,sBuilder_find.length());
                runnable.run();

            }
        });

    }
}
