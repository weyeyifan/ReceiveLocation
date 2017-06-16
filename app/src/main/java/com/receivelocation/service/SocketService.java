package com.receivelocation.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.receivelocation.thread.ParamThread;
import com.receivelocation.utils.ThreadUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Service {
    PowerManager          pm;
    PowerManager.WakeLock wl;
    public static ServerSocket mSocket = null;
    public static Socket       socket  = null;
    Handler mHandler = new Handler();
    Runnable timeRunnable;
    ParamThread thread;

    Runnable run;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //程序后台时保持cpu不休眠,不会杀死进程(国产room一般还是会强制杀)
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "keepsocket");
        wl.acquire();
        init();
        timeRunnable = new Runnable() {
            @Override
            public void run() {


                mHandler.postDelayed(this, 1000 * 5);
            }
        };


        mHandler.postDelayed(timeRunnable, 1000 * 5);
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        ThreadUtils.getThreadPoolProxy().removeTask(run);
        run=new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket=null;
                    mSocket=new ServerSocket(33800);
                    socket=mSocket.accept();
                    thread = new ParamThread(socket);
                    ThreadUtils.getThreadPoolProxy().execute(thread);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadUtils.getThreadPoolProxy().execute(run);
    }


    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        wl.release();
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }

            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }
}
