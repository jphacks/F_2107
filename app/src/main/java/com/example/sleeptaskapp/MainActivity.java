package com.example.sleeptaskapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.sleeptaskapp.databinding.FragmentTaskListBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sleeptaskapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private EditText taskname;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String CHANNEL_ID = "sample_notification_channel";
    DataBaseHelper myDb;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    int year;
    int month;
    int dayOfMonth;
    int ID[];
    private ArrayAdapter<String> adapter;
    private String DAY;
    //通知オブジェクトの用意と初期化
    Notification notification = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        myDb = new DataBaseHelper(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                ClickMe2();

            }
        });


        // Set the intent that will fire when the user taps the notification




    }

    private void ClickMe2() {
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("WEEK",String.valueOf(week));


        if(week == 1) {
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            DAY = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN).format(calendar.getTime());
        } else if(week == 7) {
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            DAY = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN).format(calendar.getTime());
        } else {
            calendar.setTime(nowDate);
            DAY = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN).format(calendar.getTime());
        }

        // 翌日

        //System.out.println(calendar.getTime().toString());

        // yyyy-MM-dd形式へ
        //String strNextDate = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN).format(calendar.getTime());
        //System.out.println(strNextDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);







        //DAY = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        Log.d("DAY",DAY);




        int total_time = 0;
        Cursor res = myDb.getAllData();
        String[] Buffers;
        int[] t_value;
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        if(res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                Log.d("DAY",res.getString(4));
                if (res.getString(4).equals(DAY)) {
                    count++;
                }
            }
        }


        res = myDb.getAllData();
        if(res != null && res.getCount() > 0) {
            Buffers = new String[count];
            t_value = new int[count];
            ID = new int[count];
            int T = 0;

            while (res.moveToNext()) {
                if(res.getString(4).equals(DAY)) {
                    ID[T] = Integer.parseInt(res.getString(0));
                    stringBuffer.append(res.getString(1) + "\n");
                    stringBuffer.append("TIME:" + res.getString(2) + "\n");
                    stringBuffer.append("END:" + res.getString(3));
                    //adapter.add(stringBuffer.toString());
                    String time = res.getString(2);
                    String etime = res.getString(3);
                    String[] ms = time.split(":");
                    String[] ems = etime.split(":");

                    Buffers[T] = stringBuffer.toString();
                    t_value[T] = (Integer.parseInt(ms[0]))* 60 + (Integer.parseInt(ms[1]));
                    int FFFF = (Integer.parseInt(ems[0]) - Integer.parseInt(ms[0]) )* 60 + (Integer.parseInt(ems[1]) - Integer.parseInt(ms[1]) );
                    total_time += FFFF;

                    Log.i("ClickMe",Buffers[T]);
                    stringBuffer = new StringBuffer();
                    T++;
                }
            }

            for (int i = 0; i < t_value.length - 1; i++) {
                for (int j = i + 1; j < t_value.length ; j++) {
                    if (t_value[i] > t_value[j]) {
                        int a = t_value[i];
                        t_value[i] = t_value[j];
                        t_value[j] = a;

                        String b = Buffers[i];
                        Buffers[i] = Buffers[j];
                        Buffers[j] = b;

                        a = ID[i];
                        ID[i] = ID[j];
                        ID[j] = a;

                    }
                }
            }

            StringBuffer buf = new StringBuffer();



            for(int i=0;i< Buffers.length;i++) {
                if(Buffers[i] != null) {
                    Log.i("ClickMe", String.valueOf(Buffers.length));
                    buf.append(String.valueOf(Buffers[i]));
                    //adapter.add(String.valueOf(Buffers[i]));
                }
            }

            Log.d("total",String.valueOf(total_time));

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("email"));
            String[] s = {"JPHACKSPROJECTMANAGER@gmail.com"};
            i.putExtra(Intent.EXTRA_EMAIL,s);
            i.putExtra(Intent.EXTRA_SUBJECT,buf.toString());
            i.putExtra(Intent.EXTRA_TEXT,"");
            i.setType("message/rfc822");
            if(i.resolveActivity(getPackageManager())!=null){
                startActivity(i);
            }


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

    @Override
    public boolean onSupportNavigateUp() {



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}