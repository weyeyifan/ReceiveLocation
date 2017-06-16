package com.receivelocation.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.receivelocation.MyApp;
import com.receivelocation.R;
import com.receivelocation.ViewHolder.AutoHolder;
import com.receivelocation.model.ProtectBean;
import com.receivelocation.ui.fragment.ProtectFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class AutoAdapter extends BaseAdapter {
    List<ProtectBean> datas = new ArrayList<>();

    public void setDatas(ArrayList<ProtectBean> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AutoHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(MyApp.getInstance());
            convertView = inflater.inflate(R.layout.item_lv_auto, parent, false);
            holder = new AutoHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AutoHolder) convertView.getTag();
        }
        holder.mTvEarfcn.setText(datas.get(position).getEarfcn());
        holder.mPci.setText(datas.get(position).getPci());
        holder.mTvTai.setText(datas.get(position).getTai());
        holder.mTvCgi.setText(datas.get(position).getCgi());
        holder.mTvSinr.setText(datas.get(position).getSinr() + "");
        holder.mRsrp.setText(datas.get(position).getRsrp() + "");
        holder.mCbAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean tag = false;
                for (int i = 0; i < ProtectFragment.protectDatas.size(); i++) {
                    if (ProtectFragment.protectDatas.get(i).getEarfcn().equals(datas.get(position).getEarfcn()) && ProtectFragment.protectDatas.get(i).getPci().equals(datas.get(position).getPci())) {
                        ProtectFragment.protectDatas.remove(i);
                        ProtectFragment.protectAdapter.notifyDataSetChanged();
                        tag = true;
                        break;
                    }
                }
                if (tag == false) {
                    ProtectFragment.protectDatas.add(0, datas.get(position));
                    ProtectFragment.protectAdapter.notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

}
