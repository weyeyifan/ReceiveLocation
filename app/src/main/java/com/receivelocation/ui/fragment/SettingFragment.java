package com.receivelocation.ui.fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.receivelocation.MyApp;
import com.receivelocation.R;
import com.receivelocation.model.Constants;
import com.receivelocation.utils.PreferenceUtils;
import com.receivelocation.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @createAuthor zfb
 * @createTime 2017/4/19${Time}
 * @describe ${TODO}
 */

public class SettingFragment extends Fragment {
    @BindView(R.id.et_smscenter)
    EditText mEtSmscenter;
    @BindView(R.id.et_time1)
    EditText mEtTime1;
    @BindView(R.id.et_time2)
    EditText mEtTime2;
    @BindView(R.id.et_time3)
    EditText mEtTime3;
    @BindView(R.id.tv_phonestate)
    TextView mTvPhonestate;
    @BindView(R.id.btn_check)
    Button   mBtnCheck;
    @BindView(R.id.btn_send)
    Button   mBtnSend;
    @BindView(R.id.lv)
    ListView mLv;

    final byte[] payload = new byte[]{0x0A, 0x06, 0x03, (byte) 0xB0, (byte) 0xAF, (byte) 0x82, 0x03, 0x06, 0x6A, 0x00, 0x05};
    PendingIntent sentPI;
    PendingIntent deliveryPI;
    final String SENT    = "pingsms.sent";
    final String DELIVER = "pingsms.deliver";

    final IntentFilter sentFilter        = new IntentFilter(SENT);
    final IntentFilter deliveryFilter    = new IntentFilter(DELIVER);
    final IntentFilter wapDeliveryFilter = new IntentFilter("android.provider.Telephony.WAP_PUSH_DELIVER");
    //记录最后一次收到成功回执的时间
    long lastTime;
    //定时器
    Handler mhandler;
    Runnable run;
    Boolean tag=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        sentPI = PendingIntent.getBroadcast(MyApp.getInstance(), 0x1337, new Intent(SENT), PendingIntent.FLAG_CANCEL_CURRENT);
        deliveryPI = PendingIntent.getBroadcast(MyApp.getInstance(), 0x1337, new Intent(DELIVER), PendingIntent.FLAG_CANCEL_CURRENT);
        initEvent();
        mhandler=new Handler();
        return view;
    }

    private void initEvent() {

        getActivity().findViewById(R.id.tv_add).setVisibility(View.VISIBLE);
        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        run=new Runnable() {
            @Override
            public void run() {
                long currentTime=System.currentTimeMillis();
                if(currentTime-lastTime>1000*120){
                    tag=false;
                    mTvPhonestate.setText("不在线");
                }else {
                    tag=true;
                }
                if(tag){
                    send();
                }
                int time=Integer.parseInt(mEtTime1.getText().toString().trim());
                mhandler.postDelayed(this,1000*(time));
            }
        };

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mhandler.postDelayed(run,1000*5);
            }
        });



    }

    boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(MyApp.getInstance(),
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
            return false;
        }
        return true;
    }

    public void send(){
        if (checkPermissions()) {

            if(mEtSmscenter.getText().toString().length()>0){
                SmsManager.getDefault().sendDataMessage(PreferenceUtils.getString(Constants.SAVEDNUMBER), mEtSmscenter.getText().toString(), (short) 9200, payload, sentPI, deliveryPI);
            }else {
                SmsManager.getDefault().sendDataMessage(PreferenceUtils.getString(Constants.SAVEDNUMBER), null, (short) 9200, payload, sentPI, deliveryPI);
            }

        } else {
            ToastUtil.show("APP需要读写短信权限");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(br, sentFilter);
        getActivity().registerReceiver(br, deliveryFilter);
        getActivity().registerReceiver(br, wapDeliveryFilter);
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(br);
        mhandler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            if (DELIVER.equalsIgnoreCase(intent.getAction())) {
                boolean delivered = false;
                if (intent.hasExtra("pdu")) {
                    byte[] pdu = (byte[]) intent.getExtras().get("pdu");
                    if (pdu != null && pdu.length > 1) {
                        String resultPdu = getLogBytesHex(pdu).trim();
                        delivered = "00".equalsIgnoreCase(resultPdu.substring(resultPdu.length() - 2));
                    }
                }
                mTvPhonestate.setText(delivered ? "在线" : "不在线");
                if(delivered){
                    lastTime=System.currentTimeMillis();
                    tag=true;
                }
            }

        }
    };

    private String getLogBytesHex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("0x%02X ", b));
        }
        return sb.toString();
    }
}
