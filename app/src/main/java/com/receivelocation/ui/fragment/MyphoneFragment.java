package com.receivelocation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.receivelocation.R;

/**
 * @createAuthor zfb
 * @createTime 2017/4/19${Time}
 * @describe ${TODO}
 */

public class MyphoneFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phone,container,false);
        initData();
        return view;
    }

    private void initData() {
        getActivity().findViewById(R.id.tv_save).setVisibility(View.GONE);
    }
}
