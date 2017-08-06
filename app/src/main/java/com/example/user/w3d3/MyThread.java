package com.example.user.w3d3;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by user on 8/2/2017.
 */

public class MyThread extends Thread{

    Handler handler;

    public MyThread() {
        //here the context is of where you instance
        //MyThread...

    }
    @Override
    public void run(){

        Looper.prepare();//This attaches the Message Queue (MQ)
        handler = new Handler();
        Looper.loop(); // Is Looping for messages.
    }
}
