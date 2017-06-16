package com.receivelocation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.receivelocation.R;
import com.receivelocation.model.Constants;
import com.receivelocation.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/4/19${Time}
 * @describe ${TODO}
 */

public class DisturbFragment extends Fragment {
    @BindView(R.id.tv_targetphone)
    TextView mTvTargetphone;
    @BindView(R.id.et_filter)
    EditText mEtFilter;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.tv_invalid)
    TextView mTvInvalid;
    @BindView(R.id.tv_trigger)
    TextView mTvTrigger;
    @BindView(R.id.tv_currentplot)
    TextView mTvCurrentplot;
    @BindView(R.id.cb_disturb)
    CheckBox mCbDisturb;
    @BindView(R.id.lv_disturb)
    ListView mLvDisturb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disturb, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mTvTargetphone.setText(PreferenceUtils.getString(Constants.SAVEDNUMBER));
    }
}
