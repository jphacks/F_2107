package com.example.sleeptaskapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import com.example.sleeptaskapp.TaskList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.sleeptaskapp.databinding.FragmentCalendarBinding;
import com.example.sleeptaskapp.databinding.FragmentTaskListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentCalendarBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                /*FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // 普通は引数は画面で入力されたメッセージをいれるのかな？
                transaction.replace(R.id.container, TaskList.newInstance(String.valueOf(year),String.valueOf(month),String.valueOf(dayOfMonth)));

                transaction.commit();*/

                Bundle bundle = new Bundle();
                String yearS = String.valueOf(year);
                String monthS = "";
                String dayOfMonthS = "";

                if(month + 1 < 10) {
                    monthS = "0" + String.valueOf(month + 1);
                } else {
                    monthS = String.valueOf(month + 1);
                }

                if(dayOfMonth < 10) {
                    dayOfMonthS = "0" + String.valueOf(dayOfMonth);
                } else {
                    dayOfMonthS = String.valueOf(dayOfMonth);
                }
                bundle.putString("DAY", yearS + monthS + dayOfMonthS);

                Log.d("you" , year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                NavHostFragment.findNavController(CalendarFragment.this)
                        .navigate(R.id.action_CalendarFragment_to_TaskList,bundle);
            }
        });

    }



}