package com.receivelocation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.receivelocation.R;
import com.receivelocation.model.Constants;
import com.receivelocation.utils.PreferenceUtils;
import com.receivelocation.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.protect)
    LinearLayout mProtect;
    @BindView(R.id.nullcode)
    LinearLayout mNullcode;
    @BindView(R.id.target)
    LinearLayout mTarget;
    @BindView(R.id.disturb)
    LinearLayout mDisturb;
    @BindView(R.id.setting)
    LinearLayout mSetting;
    @BindView(R.id.phone)
    LinearLayout mPhone;
    @BindView(R.id.btn_reset)
    Button       mBtnReset;
    @BindView(R.id.btn_connect)
    Button       mBtnConnect;
    @BindView(R.id.btn_disconnect)
    Button       mBtnDisconnect;
    @BindView(R.id.tv_state)
    TextView     mTvState;
    @BindView(R.id.activity_home)
    LinearLayout mActivityHome;
    //跳转页面时标记页面类型的tag
    String type;
    @BindView(R.id.et_savenum)
    EditText mEtSavenum;
    @BindView(R.id.btn_savenum)
    Button   mBtnSavenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {
        mProtect.setOnClickListener(myClickListener);
        mNullcode.setOnClickListener(myClickListener);
        mTarget.setOnClickListener(myClickListener);
        mDisturb.setOnClickListener(myClickListener);
        mSetting.setOnClickListener(myClickListener);
        mPhone.setOnClickListener(myClickListener);

        mEtSavenum.setText(PreferenceUtils.getString(Constants.SAVEDNUMBER));
        mEtSavenum.setSelection(mEtSavenum.getText().length());

        mBtnSavenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEtSavenum.getText().toString().trim().length()==0){
                    ToastUtil.show("请填写目标号码!");
                }else {
                    PreferenceUtils.putString(Constants.SAVEDNUMBER,mEtSavenum.getText().toString().trim());
                    ToastUtil.show("保存成功!");
                }
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空sharedpreference的所有数据
                PreferenceUtils.clearData();
                mEtSavenum.setText("");
            }
        });
    }


    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.protect:
                    type = Constants.PROTECT;
                    break;
                case R.id.nullcode:
                    type = Constants.NULL_CODE;
                    break;
                case R.id.target:
                    type = Constants.TARGET;
                    break;
                case R.id.disturb:
                    type = Constants.DISTURB;
                    break;
                case R.id.setting:
                    type = Constants.PARAM_SETTING;
                    break;
                case R.id.phone:
                    type = Constants.INFO_PHONE;
                    break;
            }
            Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    };
}
