package com.receivelocation.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.receivelocation.MyApp;
import com.receivelocation.R;
import com.receivelocation.ViewHolder.ProtectHolder;
import com.receivelocation.model.ProtectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @createAuthor zfb
 * @createTime 2017/5/4${Time}
 * @describe ${TODO}
 */

public class ProtectAdapter extends BaseAdapter{

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
        ProtectHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(MyApp.getInstance());
            convertView = inflater.inflate(R.layout.item_lv_auto, parent, false);
            holder = new ProtectHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ProtectHolder) convertView.getTag();
        }
        holder.mTvEarfcn.setText(datas.get(position).getEarfcn());
        holder.mPci.setText(datas.get(position).getPci());
        holder.mTvTai.setText(datas.get(position).getTai());
        holder.mCbAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }
}
