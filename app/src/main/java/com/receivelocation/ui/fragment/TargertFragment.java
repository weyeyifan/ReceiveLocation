package com.receivelocation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.receivelocation.R;
import com.receivelocation.eventbusmessage.TargettMessage;
import com.receivelocation.model.Constants;
import com.receivelocation.model.TargetBean;
import com.receivelocation.ui.adpter.TargetAdapter;
import com.receivelocation.utils.DataUtils;
import com.receivelocation.utils.PreferenceUtils;
import com.receivelocation.utils.ThreadUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/4/19${Time}
 * @describe ${TODO}
 */

public class TargertFragment extends Fragment {
    @BindView(R.id.tv_targetphone)
    TextView    mTvTargetphone;
    @BindView(R.id.tv_targetSTMSI)
    TextView    mTvTargetSTMSI;
    @BindView(R.id.count_trigger)
    TextView    mCountTrigger;
    @BindView(R.id.tv_currentplot)
    TextView    mTvCurrentplot;
    @BindView(R.id.rb_far)
    RadioButton mRbFar;
    @BindView(R.id.rb_near)
    RadioButton mRbNear;
    @BindView(R.id.rg_target)
    RadioGroup  mRgTarget;
    @BindView(R.id.btn_start)
    Button      mBtnStart;
    @BindView(R.id.btn_stop)
    Button      mBtnStop;
    @BindView(R.id.lv_target)
    ListView    mLvTarget;
    @BindView(R.id.barchat)
    BarChart    mBarchat;

    TargetAdapter adapter;

    ArrayList<TargetBean>datas;

    boolean scroll;

    int trigger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_target, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        initEvent();
        return view;
    }

    private void initData() {
        mTvTargetphone.setText(PreferenceUtils.getString(Constants.SAVEDNUMBER));
        mTvTargetSTMSI.setText(PreferenceUtils.getString(Constants.SAVEDSTMSI));
        adapter=new TargetAdapter();
        datas=new ArrayList<>();
        adapter.setDatas(datas);
        getActivity().findViewById(R.id.tv_save).setVisibility(View.GONE);

    }

    private void initEvent() {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll=true;
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll=false;
            }
        });
    }

    @Subscribe
    public void onTargetMessage(final TargettMessage event){
        ThreadUtils.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<TargetBean>list= DataUtils.getTargetList();
                datas.addAll(list);
                trigger+=list.size();
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mCountTrigger.setText(trigger+"");
                        if(scroll){
                            mLvTarget.smoothScrollToPosition(adapter.getCount()-1);
                        }
                    }
                });
            }
        });
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
