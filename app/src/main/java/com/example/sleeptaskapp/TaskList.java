package com.example.sleeptaskapp;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sleeptaskapp.databinding.FragmentSecondBinding;
import com.example.sleeptaskapp.databinding.FragmentTaskListBinding;
import com.example.sleeptaskapp.databinding.FragmentThirdBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String CHANNEL_ID = "sample_notification_channel";
    DataBaseHelper myDb;

    private FragmentTaskListBinding binding;

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


    public TaskList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskList.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskList newInstance(String param1, String param2, String param3) {
        TaskList fragment = new TaskList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.AddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("DAY", DAY);
                NavHostFragment.findNavController(TaskList.this)
                        .navigate(R.id.action_TaskListFragment_to_ThirdFragment,bundle);
            }
        });

        binding.BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("DAY", DAY);
                NavHostFragment.findNavController(TaskList.this)
                        .navigate(R.id.action_TaskListFragment_to_CalendarFragment,bundle);
            }
        });

        binding.ShowCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("f","FFFFFFF");
                Bundle bundle = new Bundle();
                bundle.putString("DAY", DAY);
                NavHostFragment.findNavController(TaskList.this)
                        .navigate(R.id.action_TaskList_to_CircleFragment,bundle);
            }
        });

        ListView listView = binding.taskList;
        adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String)adapterView.getItemAtPosition(position);
                Toast.makeText(getContext(),"Item:" + item,Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("DAY", DAY);
                String[] content = item.split("\n");
                Log.d("FFFF",content[0]);
                bundle.putString("TASK", content[0]);
                bundle.putString("START", content[1].split(":")[1] + ":" + content[1].split(":")[2]);
                bundle.putString("END", content[2].split(":")[1] + ":" + content[2].split(":")[2]);
                bundle.putString("ID", String.valueOf(ID[position]));
                NavHostFragment.findNavController(TaskList.this)
                        .navigate(R.id.action_TaskList_to_UpdateFragment,bundle);

            }
        });
        ClickMe2();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);*/
            DAY = getArguments().getString("DAY");
            Log.d("you" , DAY);
        }

        myDb = new DataBaseHelper(getContext());



    }

    private void ClickMe2() {
        int total_time = 0;
        Cursor res = myDb.getAllData();
        String[] Buffers;
        int[] t_value;
        StringBuffer stringBuffer = new StringBuffer();
        if(res != null && res.getCount() > 0) {
            Buffers = new String[res.getCount()];
            t_value = new int[res.getCount()];
            ID = new int[res.getCount()];
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

            for(int i=0;i< Buffers.length;i++) {
                if(Buffers[i] != null) {
                    Log.i("ClickMe", String.valueOf(Buffers.length));
                    adapter.add(String.valueOf(Buffers[i]));
                }
            }

            Log.d("total",String.valueOf(total_time));

            if(total_time > 1020) {
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
            }

        }

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}