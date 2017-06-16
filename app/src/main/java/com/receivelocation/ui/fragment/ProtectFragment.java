package com.receivelocation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.receivelocation.R;
import com.receivelocation.eventbusmessage.ProtectListMessage;
import com.receivelocation.model.Constants;
import com.receivelocation.model.ProtectBean;
import com.receivelocation.ui.adpter.AutoAdapter;
import com.receivelocation.ui.adpter.ProtectAdapter;
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

public class ProtectFragment extends Fragment {

    @BindView(R.id.et_earfcn)
    EditText mEtEarfcn;
    @BindView(R.id.et_pci)
    EditText mEtPci;
    @BindView(R.id.btn_add)
    Button   mBtnAdd;
    @BindView(R.id.lv_autosearch)
    ListView mLvAutosearch;
    @BindView(R.id.lv_protect)
    ListView mLvProtect;
    @BindView(R.id.tv_state)
    TextView mTvState;
    //自动搜索小区数据集合
    public static ArrayList<ProtectBean> autoDatas;
    //受控小区列表数据集合
    public static ArrayList<ProtectBean> protectDatas;

    public static AutoAdapter    autoAdapter;
    public static ProtectAdapter protectAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_protect, container, false);
        ButterKnife.bind(this, view);
        initDatas();
        initEvent();
        EventBus.getDefault().register(this);
        return view;
    }

    private void initDatas() {
        autoDatas = new ArrayList<>();
        protectDatas = new ArrayList<>();
        autoAdapter = new AutoAdapter();
        protectAdapter = new ProtectAdapter();
        autoAdapter.setDatas(autoDatas);
        protectAdapter.setDatas(protectDatas);
        mEtEarfcn.setText(PreferenceUtils.getString(Constants.PROTECT_EARFCN));
        mEtPci.setText(PreferenceUtils.getString(Constants.PROTECT_PCI));
    }

    private void initEvent() {
        getActivity().findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.putString(Constants.PROTECT_EARFCN,mEtEarfcn.getText().toString().trim());
                PreferenceUtils.putString(Constants.PROTECT_PCI,mEtPci.getText().toString().trim());
                ToastUtil.show("保存成功!");
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tag = false;
                if (mEtEarfcn.getText().toString().trim().length() == 0 || mEtPci.getText().toString().trim().length() == 0) {
                    ToastUtil.show("频点数与PCI必须填写!");
                } else {
                    for (int i = 0; i < autoDatas.size(); i++) {
                        if (autoDatas.get(i).getEarfcn().equals(mEtEarfcn.getText().toString().trim()) && autoDatas.get(i).getPci().equals(mEtPci.getText().toString().trim())) {
                            if (autoDatas.get(i).isCheck()) {
                                ToastUtil.show("小区已经守控!");
                            } else {
                                protectDatas.add(0, autoDatas.get(i));
                                ToastUtil.show("添加守控小区成功!");
                            }
                            tag = true;
                            break;
                        }
                    }
                    if (tag == false) {
                        ToastUtil.show("没有指定小区的数据!");
                    }
                }
            }
        });
    }

    @Subscribe
    public void onProtectListMessage(final ProtectListMessage event) {
        ThreadUtils.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                boolean tag = false;

                final ArrayList<ProtectBean> data = DataUtils.getProtectList();
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < autoDatas.size(); j++) {
                            tag=false;
                            if (autoDatas.get(j).getEarfcn().equals(data.get(i).getEarfcn()) && autoDatas.get(j).getPci().equals(data.get(i).getPci())) {
                                autoDatas.set(j, data.get(i));
                                tag = true;
                                if (autoDatas.get(j).isCheck()) {
                                    for (int k = 0; k < protectDatas.size(); k++) {
                                        if (protectDatas.get(k).getEarfcn().equals(data.get(i).getEarfcn()) && protectDatas.get(k).getPci().equals(data.get(i).getPci())) {
                                            protectDatas.set(k, data.get(i));
                                            break;
                                        }
                                    }
                                }

                                break;
                            }
                        }
                        if (tag == false) {
                            autoDatas.add(0, data.get(i));
                        }
                    }

                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {

                            autoAdapter.notifyDataSetChanged();
                            protectAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
