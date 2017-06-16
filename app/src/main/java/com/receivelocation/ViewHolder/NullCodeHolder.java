package com.receivelocation.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.receivelocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class NullCodeHolder {

    @BindView(R.id.tv_stmsi)
    public TextView mTvStmsi;
    @BindView(R.id.tv_count)
    public TextView mTvCount;
    @BindView(R.id.tv_time)
    public TextView mTvTime;
    @BindView(R.id.tv_pci)
    public TextView mTvPci;
    @BindView(R.id.tv_earfcn)
    public TextView mTvEarfcn;

    public NullCodeHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
