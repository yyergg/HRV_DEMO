package com.chasewind.AiQ_demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yyerg on 2016/5/13.
 */
public class FragmentData extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private BLEManager mBLEManager;
    private ChartHelper ch;
    private static FragmentData instance;
    private TextView tvHeartRate;
    private LinearLayout chartContainer;

    public static FragmentData getInstance(int sectionNumber) {
        if (instance == null) {
            instance = new FragmentData();
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        instance.setArguments(args);
        return instance;
    }

    public FragmentData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        this.mBLEManager = ((MainActivity) this.getActivity()).mBLEManager;
        this.ch = new ChartHelper(this.getActivity());
        this.chartContainer = (LinearLayout) rootView.findViewById(R.id.llChart);
        this.tvHeartRate = (TextView) rootView.findViewById(R.id.tvHeartRate);
        final Handler handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(1000);
                        handler.post(new Runnable() {
                            public void run() {
                                Log.d("data", mBLEManager.HR_amount.toString());
                                printHR(mBLEManager.HR_amount);
                                ArrayList<Integer> latest_10_HR = new ArrayList<Integer>(mBLEManager.HR_array.subList(
                                        mBLEManager.HR_array.size() - 10,
                                        mBLEManager.HR_array.size() - 1));
                                View mChart = ch.drawArray(latest_10_HR);
                                chartContainer.removeAllViews();
                                chartContainer.addView(mChart);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return rootView;
    }

    private void printHR(Integer HR_amount) {
        if (HR_amount == null || HR_amount == 0) {
            tvHeartRate.setText("---");
        } else {
            tvHeartRate.setText(HR_amount.toString());
        }
    }
}
