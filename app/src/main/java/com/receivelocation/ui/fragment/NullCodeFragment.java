package com.receivelocation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.receivelocation.R;
import com.receivelocation.eventbusmessage.NullCodeMessage;
import com.receivelocation.model.Constants;
import com.receivelocation.model.NullCodeBean;
import com.receivelocation.ui.adpter.NullCodeAdapter;
import com.receivelocation.utils.DataUtils;
import com.receivelocation.utils.PreferenceUtils;
import com.receivelocation.utils.ThreadUtils;
import com.receivelocation.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/4/18${Time}
 * @describe ${TODO}
 */

public class NullCodeFragment extends Fragment {
    @BindView(R.id.tv_targetphone)
    TextView mTvTargetphone;
    @BindView(R.id.et_targettmsi)
    EditText mEtTargettmsi;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.tv_invalid)
    TextView mTvInvalid;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.currentplot)
    TextView mCurrentplot;
    @BindView(R.id.cb_filter)
    CheckBox mCbFilter;
    @BindView(R.id.btn_begin)
    Button   mBtnBegin;
    @BindView(R.id.btn_stop)
    Button   mBtnStop;
    @BindView(R.id.item_lv_nullcode)
    ListView mItemLvNullcode;

    ArrayList<NullCodeBean>datas;
    ArrayList<NullCodeBean>tempDatas;
    public static NullCodeAdapter adapter;

    int sumSTMSI;
    int count;
    //开始/停止滚动
    boolean scroll;
    //是否过滤
    boolean filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nullcode, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initDatas();
        initEvents();
        return view;
    }



    private void initDatas() {
        datas=new ArrayList<>();
        adapter=new NullCodeAdapter();
        adapter.setDatas(datas);
        mTvTargetphone.setText(PreferenceUtils.getString(Constants.SAVEDNUMBER));
        mEtTargettmsi.setText(PreferenceUtils.getString(Constants.SAVEDSTMSI));
        mEtTargettmsi.setSelection(mEtTargettmsi.getText().length());
    }

    private void initEvents() {
        mBtnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll=true;
                mItemLvNullcode.smoothScrollToPosition(datas.size()-1);
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll=false;
            }
        });

        mCbFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filter=true;
                if(isChecked&&mEtTargettmsi.getText().toString().trim().length()>0){
                    tempDatas.clear();
                    for(int i=0;i<datas.size();i++){
                        if(datas.get(i).getStmsi().contains(mEtTargettmsi.getText().toString().trim())){
                            tempDatas.add(datas.get(i));
                            adapter.setDatas(tempDatas);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    filter=false;
                    adapter.setDatas(datas);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        getActivity().findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.putString(Constants.SAVEDSTMSI,mEtTargettmsi.getText().toString().trim());
                ToastUtil.show("保存成功!");
            }
        });
    }

    @Subscribe
    public void onNullCodeMessage(final NullCodeMessage event){
        ThreadUtils.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                boolean tag = false;

                final ArrayList<NullCodeBean>data= DataUtils.getNullCodeList();
                if(data.size()>0){
                    count++;
                    for(int i=0;i<data.size();i++){
                        for(int j=0;j<datas.size();j++){
                            tag=false;
                            if(data.get(i).getStmsi().equals(datas.get(j).getStmsi())){
                                int count=datas.get(j).getCount();
                                datas.set(j,data.get(i));
                                datas.get(j).setCount(count+1);
                                tag=true;
                                break;
                            }
                        }
                        if(tag==false){
                            data.get(i).setCount(1);
                            datas.add(data.get(i));

                            sumSTMSI++;

                        }
                        if(filter){
                            if(data.get(i).getStmsi().contains(mEtTargettmsi.getText().toString().trim())){
                            tempDatas.add(data.get(i))        ;
                            }
                        }
                    }
                }
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvSum.setText(sumSTMSI);
                        mCount.setText(count+"");

                        if(filter){
                            adapter.setDatas(tempDatas);
                        }else {
                            adapter.setDatas(datas);
                        }
                        adapter.notifyDataSetChanged();
                        if(scroll){
                            mItemLvNullcode.smoothScrollToPosition(adapter.getCount()-1);
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
