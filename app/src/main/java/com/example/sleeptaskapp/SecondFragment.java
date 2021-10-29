package com.example.sleeptaskapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sleeptaskapp.databinding.FragmentSecondBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_CalendarFragment);
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        String greeting = getgreet();
        String tips = gettips();

        binding.textviewSecond.setText(greeting + tips);


    }

    private String gettips() {
        String[] ms = getToday().split(":");
        int time = Integer.parseInt(ms[0]) * 60 + Integer.parseInt(ms[1]);

        switch (Integer.parseInt(ms[1]) % 13) {
            case 0:
                return "アインシュタインは一日10時間以上寝ていたそうですよ！";
            case 1:
                return "睡眠時間は最低7時間以上とりましょう！";
            case 2:
                return "寝る30分前はスマホの電源を切りましょう！";
            case 3:
                return "朝散歩は体を目覚めさせるのに最適です！";
            case 4:
                return "睡眠時間を削ると脳にダメージがあります！";
            case 5:
                return "昼寝は午後3時までにしておきましょう！";
            case 6:
                return "あなたはナポレオンじゃないので睡眠時間が3時間で足りるわけないでしょう！";
            case 7:
                return "睡眠不足は肥満の原因にもなりますよ！";
            case 8:
                return "あんまり仕事を詰めすぎないようにね！";
            case 9:
                return "睡眠不足はうつ病の原因にもなりますよ！";
            case 10:
                return "寝てない自慢はダサいですよ！";
            case 11:
                return "体は資本！しっかりと寝ましょう！";
            case 12:
                return "時代は変わりました！今はしっかり寝たものが勝ちます！";
        }
        return "よい睡眠ライフを！";
    }

    private String getgreet() {
        String[] ms = getTodayS().split(":");
        Log.d("H" , ms[0]);
        Log.d("m" , ms[1]);
        int time = Integer.parseInt(ms[0]) * 60 + Integer.parseInt(ms[1]);

        if(time < 240 || time >= 1020) {
            return "こんばんは！\n";
        } else if(time >= 240 && time < 720) {
            return "おはようございます！\n";
        } else {
            return "こんにちは！\n";
        }
    }

    private String getTodayS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    private String getToday() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}