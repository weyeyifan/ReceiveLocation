package com.receivelocation.ViewHolder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.receivelocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class ProtectHolder {

    @BindView(R.id.tv_earfcn)
    public TextView mTvEarfcn;
    @BindView(R.id.pci)
    public TextView mPci;
    @BindView(R.id.tv_tai)
    public TextView mTvTai;

    @BindView(R.id.cb_auto)
    public CheckBox mCbAuto;

    public ProtectHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
