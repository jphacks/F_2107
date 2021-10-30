package com.example.sleeptaskapp;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sleeptaskapp.databinding.FragmentUpdateBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatePicker datePicker;

    private FragmentUpdateBinding binding;

    DataBaseHelper myDb;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TimePickerDialog dialog;
    private TimePickerDialog dialogE;
    private String time;
    private String endtime;
    private String DAY;
    private String Btask;
    private String ID;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
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
            time = getArguments().getString("START");
            endtime = getArguments().getString("END");
            Btask = getArguments().getString("TASK");
            ID = getArguments().getString("ID");
        }
        myDb = new DataBaseHelper(getContext());


        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        dialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("test",String.format("%02d:%02d", hourOfDay,minute));
                        SetTime(hourOfDay,minute);
                    }
                },
                hour,minute,true);
        dialogE = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("test",String.format("%02d:%02d", hourOfDay,minute));
                        SetEndTime(hourOfDay,minute);
                    }
                },
                hour,minute,true);


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        binding.buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogE.show();
            }
        });

        binding.buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Added = -1;
                Added = DEL();
                Log.d("ID",ID);
                if(Added != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DAY", DAY);
                    NavHostFragment.findNavController(UpdateFragment.this)
                            .navigate(R.id.action_UpdateFragment_to_TaskListFragment, bundle);
                }
            }
        });

        binding.buttonAddA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean Addded = ClickMe();
                if(Addded) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DAY", DAY);
                    NavHostFragment.findNavController(UpdateFragment.this)
                            .navigate(R.id.action_UpdateFragment_to_TaskListFragment, bundle);
                }
            }
        });



        binding.editQuery.setText(Btask);
        binding.textviewTime.setText(time);
        binding.textviewEnd.setText(endtime);
    }

    private int DEL() {
        return myDb.deleteData(ID);
    }

    private void SetTime(int hourOfDay,int minute) {
        binding.textviewTime.setText(String.format("%02d:%02d", hourOfDay,minute));
        time = String.format("%02d:%02d", hourOfDay,minute);
    }

    private void SetEndTime(int hourOfDay,int minute) {
        binding.textviewEnd.setText(String.format("%02d:%02d", hourOfDay,minute));
        endtime = String.format("%02d:%02d", hourOfDay,minute);
    }

    private boolean ClickMe() {
        String task = "";
        task = binding.editQuery.getText().toString();
        /*Toast toast = Toast.makeText(getContext(), task, Toast.LENGTH_SHORT);
        toast.show();*/
        String[] ms = time.split(":");
        int start = Integer.parseInt(ms[0]) * 60 + Integer.parseInt(ms[1]);
        ms = endtime.split(":");
        int end = Integer.parseInt(ms[0]) * 60 + Integer.parseInt(ms[1]);

        int length = end - start;
        if(length > 0 && !task.equals("")) {
            Boolean result = myDb.updateData(ID,task, time, endtime, DAY);
            if (result) {
                Toast.makeText(getContext(), "Edit Task", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(getContext(), "NG", Toast.LENGTH_SHORT).show();
            }
        } else if(task.equals("")) {
            Toast.makeText(getContext(), "Task Name is None", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Set Time is Wrong", Toast.LENGTH_SHORT).show();
        }
        return  false;
    }



    private void ClickMe2() {
        Cursor res = myDb.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        if(res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("ID: " + res.getString(0) + "\n");
                stringBuffer.append("TASK: " + res.getString(1) + "\n");
                stringBuffer.append("TIME: " + res.getString(2) + "\n");
                stringBuffer.append("ENTIRE: " + res.getString(3) + "\n");
            }
        }
        Toast toast = Toast.makeText(getContext(), stringBuffer, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}