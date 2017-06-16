package com.receivelocation.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.receivelocation.MyApp;
import com.receivelocation.R;
import com.receivelocation.ViewHolder.NullCodeHolder;
import com.receivelocation.model.NullCodeBean;

import java.util.ArrayList;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class NullCodeAdapter extends BaseAdapter {
    ArrayList<NullCodeBean> datas = new ArrayList<>();

    public void setDatas(ArrayList<NullCodeBean> data) {
        datas = data;
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
        NullCodeHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(MyApp.getInstance());
            convertView = inflater.inflate(R.layout.item_lv_nullcode, parent, false);
            holder = new NullCodeHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (NullCodeHolder) convertView.getTag();
        }
        holder.mTvCount.setText(datas.get(position).getStmsi());
        holder.mTvCount.setText(datas.get(position).getCount());
        holder.mTvTime.setText(datas.get(position).getTime());
        holder.mTvPci.setText(datas.get(position).getPci());
        holder.mTvEarfcn.setText(datas.get(position).getEarfcn());

        return convertView;
    }

}
