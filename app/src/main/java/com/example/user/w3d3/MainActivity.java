package com.example.user.w3d3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName()+"_TAG";
    private static final String  MESSAGE_EXTRA ="com.example.user.w3d3_MESSAGE_EXTRA";
    private TextView resultTV;
    private MyThread myThread;

    CheckConnection checkConnection;

    //threading

    Handler handler = new Handler(){
       @Override
        public void handleMessage(Message msg){
           String message = msg.getData().getString(MESSAGE_EXTRA);
           setResultTextView(message);

       }
    };

    @Override
   protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTV = (TextView) findViewById(R.id.tv_result);
        setResultTextView(""); // executed in main thread
        checkConnection = new CheckConnection(this);
        myThread = new MyThread();
        myThread.start();

    }
    //This is a bad idea
    public void doMainThread( View v){
        try{
            String message ="From main thread";
            Thread.sleep(500);
            setResultTextView(message);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void separateThread(View view){
        final String message ="From thread";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try{
                        wait(500);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                   // setResultTextView(message);
                    Log.d(TAG, "run:"+ message);

                }
            }

            });
        thread.start();
    }

    public void doWithLooper(View view){

        myThread.handler.post(new Runnable(){

                @Override
                public void run(){
                    String message = "From Thread with Looper";
                    String thread  = Thread.currentThread().getName();
                    Log.d(TAG, "run: "+message+" "+thread);
                }


        });

    }
    public void doWithHandler(View view){
        final String message = "From Thread with Handler";
        Context curContext = checkConnection.getContext();
        boolean yes = checkConnection.isItConnected(curContext);

        if( yes == true){

            Toast.makeText(this, "Is it connected "+yes , Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Is it connected "+yes , Toast.LENGTH_SHORT).show();
        }

        Thread thread = new Thread(){
            public void run(){
                Message msg = handler.obtainMessage();
                Bundle data = new Bundle();
                data.putString(MESSAGE_EXTRA, message);
                msg.setData(data);

                try{
                    sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                handler.sendMessage(msg);


            }
        };
        thread.start();
    }

    public void doWithAsyncTask(View view){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void...voids){
                // here you do the bckground work
                synchronized(this){
                    try{
                        wait(500);

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    String message = "Done with AsyncTask";

                    return message;
                }


            }


            @Override
            protected void onProgressUpdate(Void ... s){
                super.onProgressUpdate(s);
            }

            @Override
            protected void onPostExecute(String value){
               super.onPostExecute(value);
            }
        }.execute();
    }

    private void setResultTextView(String message){

        String result = String.format(getString(R.string.lbl_result), message);
        resultTV.setText(result);
    }
}
