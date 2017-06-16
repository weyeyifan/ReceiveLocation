package com.receivelocation.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.receivelocation.MyApp;
import com.receivelocation.R;
import com.receivelocation.ViewHolder.TargetHolder;
import com.receivelocation.model.TargetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @createAuthor zfb
 * @createTime 2017/5/5${Time}
 * @describe ${TODO}
 */

public class TargetAdapter extends BaseAdapter {

    List<TargetBean> datas = new ArrayList<>();

    public void setDatas(ArrayList<TargetBean> datas) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TargetHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(MyApp.getInstance());
            convertView = inflater.inflate(R.layout.item_lv_target, parent, false);
            holder = new TargetHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TargetHolder) convertView.getTag();
        }
        holder.mTvCount.setText(datas.get(position).getCount());
        holder.mTvPower.setText(datas.get(position).getPower());
        holder.mTvPci.setText(datas.get(position).getPci());
        holder.mTvEarfcn.setText(datas.get(position).getEARFCN());
        holder.mTvTime.setText(datas.get(position).getTime());

        return convertView;
    }

}
