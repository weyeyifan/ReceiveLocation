package com.receivelocation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.receivelocation.R;

/**
 * @createAuthor zfb
 * @createTime 2017/4/18${Time}
 * @describe ${动态更改颜色占比的textview,暂未使用}
 */

public class GradientTextview extends RelativeLayout {

    TextView mTv1;

    TextView mTv2;


    public GradientTextview(Context context) {
        super(context, null);
        init();
    }

    public GradientTextview(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public GradientTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.relative_gradient_textview, this);
        mTv1= (TextView) findViewById(R.id.tv1);
        mTv2= (TextView) findViewById(R.id.tv2);
    }
    public void setText(final String message) {

        mTv2.setText(message);
    }

    /**
     *
     * @param x
     * @param width 必须将控件width动态获得后传进来,不然里面width以静态布局为准
     */
    public void setColor(final float x,int width) {

        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mTv1.getLayoutParams();
        params.width=(int)(width*x);
        mTv1.setLayoutParams(params);
    }
}
