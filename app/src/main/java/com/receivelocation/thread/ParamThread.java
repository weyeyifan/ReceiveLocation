package com.receivelocation.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static com.receivelocation.service.SocketService.socket;


/**
 * @createAuthor zfb
 * @createTime 2017/5/3${Time}
 * @describe ${TODO}
 */

public class ParamThread implements Runnable{
    public Socket mSocket;
    StringBuffer sb = new StringBuffer();
    String unix;
    public static OutputStream os         = null;
    public static InputStream  inputStream = null;

    public ParamThread(Socket socket) {
        mSocket = socket;
    }

    @Override
    public void run() {
        int temp;
        byte bf[] = new byte[10*1024];
        try {

            if(mSocket!=null){
                os = mSocket.getOutputStream();
                PrintWriter pw1 = new PrintWriter(os);
//                String reply1 = "Welecome to tcp server!^M";
//                pw1.write(reply1);
//                pw1.flush();
                inputStream = mSocket.getInputStream();
                if ((temp = inputStream.read(bf)) > 0) {
                    sb = new StringBuffer();
                    sb.append(new String(bf, 0, temp));

                    unix = String.valueOf(System.currentTimeMillis());
//                    reply1 = "{\"code\":54,\"msg\":\"Device registered successfully.\",\"stats\":4129,\"timestamp\":" + unix + "}";
//                    pw1.write(reply1);
//                    pw1.flush();
                    bf = new byte[10 * 1024];

                    while (true) {
                        if (mSocket != null) {
                            inputStream= mSocket.getInputStream();
                            if ((temp = inputStream.read(bf)) > 0) {
                                sb = new StringBuffer();
                                sb.append(new String(bf, 0, temp));

                            } else {
                                if (socket!= null) {
                                    if(os!=null){
                                        os=null;
                                    }
                                    inputStream=null;
                                    break;
                                }
                            }

                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
