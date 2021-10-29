package com.example.sleeptaskapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.example.sleeptaskapp.databinding.FragmentCircleBinding;
import com.example.sleeptaskapp.databinding.FragmentFirstBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CircleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String DAY;
    private FragmentCircleBinding binding;
    DataBaseHelper myDb;
    int ID[];

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157),Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80),Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209),Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130)
    };

    public CircleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CircleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CircleFragment newInstance(String param1, String param2) {
        CircleFragment fragment = new CircleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            DAY = getArguments().getString("DAY");
            Log.d("you" , DAY);
        }
        myDb = new DataBaseHelper(getContext());


        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCircleBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ClickMe2();




        //表示用サンプルデータの作成//
        /*String[] dimensions = {"A", "B", "C", "D"};//分割円の名称(String型)
        float[] values = {1f, 2f, 3f, 4f};//分割円の大きさ(Float型)

        //①Entryにデータ格納
        List<PieEntry> entryList = new ArrayList<>();
        for(int i=0;i < values.length;i++){
            entryList.add(new PieEntry(values[i], dimensions[i]));
        }

        //②PieDataSetにデータ格納
        PieDataSet pieDataSet = new PieDataSet(entryList, "candle");
        //③DataSetのフォーマット指定
        pieDataSet.setColor(ColorTemplate.COLORFUL_COLORS.toList());

        //④PieDataにPieDataSet格納
        PieData pieData = new PieData(pieDataSet);
        //⑤PieChartにPieData格納
        PieChart pieChart = binding.pieChartExample;
        pieChart.setData(pieData);
        //⑥Chartのフォーマット指定
        pieChart.legend.isEnabled = false;
        //⑦PieChart更新
        pieChart.invalidate();*/


    }

    private void createPieChart(String[] TaskName,int[] TaskTime) {
        PieChart pieChart = binding.pieChartExample;

        pieChart.setDrawHoleEnabled(true); // 真ん中に穴を空けるかどうか
        pieChart.setHoleRadius(50f);       // 真ん中の穴の大きさ(%指定)
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setRotationAngle(270);          // 開始位置の調整
        pieChart.setRotationEnabled(true);       // 回転可能かどうか
        pieChart.getLegend().setEnabled(true);   //
        pieChart.setData(createPieChartData(TaskName,TaskTime));

        // 更新
        pieChart.invalidate();
        // アニメーション
        pieChart.animateXY(2000, 2000); // 表示アニメーション
    }

    // pieChartのデータ設定
    private PieData createPieChartData(String[] TaskName,int[] TaskTime) {
        ArrayList<PieEntry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();


        int total_time = 0;
        for(int i=0;i<TaskName.length;i++) {
            xVals.add(String.valueOf(i));
            yVals.add(new PieEntry(TaskTime[i], TaskName[i] + "\n" + String.valueOf(TaskTime[i] / 60) +"時間" + String.valueOf(TaskTime[i] % 60) + "分"));
            Log.d(TaskName[i],String.valueOf(TaskTime[i]));
            total_time += TaskTime[i];
            colors.add(COLORFUL_COLORS[i]);
        }

        int sleeptime = 1440 - total_time;
        yVals.add(new PieEntry(sleeptime,"Sleep" + "\n" + String.valueOf(sleeptime / 60) +"時間" + String.valueOf(sleeptime % 60) + "分"));
        colors.add(Color.rgb(0, 255, 0));

        PieDataSet dataSet = new PieDataSet(yVals, "Data");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(1f);

        // 色の設定
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        // テキストの設定
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    private void ClickMe2() {
        int total_time = 0;
        Cursor res = myDb.getAllData();
        String[] Buffers;
        String[] TaskName;
        int[] t_value;
        int[] tasktime;
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;

        if(res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
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
            tasktime = new int[count];
            TaskName = new String[count];
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
                    TaskName[T] = res.getString(1);

                    Buffers[T] = stringBuffer.toString();
                    t_value[T] = (Integer.parseInt(ms[0]))* 60 + (Integer.parseInt(ms[1]));
                    tasktime[T] = (Integer.parseInt(ems[0]) - Integer.parseInt(ms[0]) )* 60 + (Integer.parseInt(ems[1]) - Integer.parseInt(ms[1]) );
                    total_time += tasktime[T];

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

                        b = TaskName[i];
                        TaskName[i] = TaskName[j];
                        TaskName[j] = b;

                        a = ID[i];
                        ID[i] = ID[j];
                        ID[j] = a;

                        a = tasktime[i];
                        tasktime[i] = tasktime[j];
                        tasktime[j] = a;
                    }
                }
            }

            for(int i=0;i< Buffers.length;i++) {
                if(Buffers[i] != null) {
                    Log.i("ClickMe", String.valueOf(Buffers.length));
                }
            }

            Log.d("total",String.valueOf(total_time));
            createPieChart(TaskName,tasktime);

            /*if(total_time > 1020) {
                // notificationId is a unique int for each notification that you must define
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage( "睡眠は7時間以上とれるようにタスクを調整してください！")
                        .setTitle("警告！タスクを詰めすぎています！" )
                        .setIcon(R.drawable.dialog_icon)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
// ボタンをクリックしたときの動作
                            }
                        });
                builder.show();
            }*/

        }

    }


}