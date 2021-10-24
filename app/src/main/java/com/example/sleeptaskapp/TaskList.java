package com.example.sleeptaskapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    DataBaseHelper myDb;

    private FragmentTaskListBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayAdapter<String> adapter;

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
    public static TaskList newInstance(String param1, String param2) {
        TaskList fragment = new TaskList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.AddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TaskList.this)
                        .navigate(R.id.action_TaskListFragment_to_ThirdFragment);
            }
        });

        ListView listView = binding.taskList;
        adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        ClickMe2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        myDb = new DataBaseHelper(getContext());



    }

    private void ClickMe2() {
        Cursor res = myDb.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        if(res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("TASK: " + res.getString(1) + "\n");
                stringBuffer.append("TIME: " + res.getString(2) + "\n");
                stringBuffer.append("ENTIRE: " + res.getString(3));
                adapter.add(stringBuffer.toString());
                stringBuffer = new StringBuffer();
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