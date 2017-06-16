package com.receivelocation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.receivelocation.R;
import com.receivelocation.model.Constants;
import com.receivelocation.ui.fragment.DisturbFragment;
import com.receivelocation.ui.fragment.MyphoneFragment;
import com.receivelocation.ui.fragment.NullCodeFragment;
import com.receivelocation.ui.fragment.ProtectFragment;
import com.receivelocation.ui.fragment.SettingFragment;
import com.receivelocation.ui.fragment.TargertFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_save)
    TextView mTvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        String type = getIntent().getStringExtra("type");
        switch (type) {
            case Constants.PROTECT:
                ft.replace(R.id.fr_content, new ProtectFragment(), Constants.PROTECT);
                mTvTitle.setText("守控小区");
                break;

            case Constants.NULL_CODE:
                ft.replace(R.id.fr_content, new NullCodeFragment(), Constants.NULL_CODE);
                mTvTitle.setText("空码搜索");
                break;

            case Constants.TARGET:
                ft.replace(R.id.fr_content, new TargertFragment(), Constants.TARGET);
                mTvTitle.setText("目标测向");
                break;

            case Constants.DISTURB:
                ft.replace(R.id.fr_content, new DisturbFragment(), Constants.DISTURB);
                mTvTitle.setText("干扰探测");
                break;

            case Constants.PARAM_SETTING:
                ft.replace(R.id.fr_content, new SettingFragment(), Constants.PARAM_SETTING);
                mTvTitle.setText("参数配置");
                break;

            case Constants.INFO_PHONE:
                ft.replace(R.id.fr_content, new MyphoneFragment(), Constants.INFO_PHONE);
                mTvTitle.setText("本机信息");
                break;

        }
        ft.commit();
    }

}
