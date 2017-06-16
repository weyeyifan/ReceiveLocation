package com.receivelocation.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.receivelocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/5/5${Time}
 * @describe ${TODO}
 */

public class TargetHolder {
    @BindView(R.id.tv_count)
    public TextView mTvCount;
    @BindView(R.id.tv_power)
    public TextView mTvPower;
    @BindView(R.id.tv_pci)
    public TextView mTvPci;
    @BindView(R.id.tv_earfcn)
    public TextView mTvEarfcn;
    @BindView(R.id.tv_time)
    public TextView mTvTime;

    public TargetHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
