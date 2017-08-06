package com.example.user.w3d3;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckConnection {

    Context context;

    public CheckConnection(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public boolean isItConnected(Context context) {

        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectManager != null){

            NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();

            if(networkInfo != null){

                return  networkInfo.isConnected();
            }
        }
        return false;
    }
}
