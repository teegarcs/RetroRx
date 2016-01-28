package com.captech.retrorx;

import android.app.Application;

/**
 * Created by cteegarden on 1/26/16.
 */
public class RxApplication extends Application {

    private NetworkService networkService;
    @Override
    public void onCreate() {
        super.onCreate();

        networkService = new NetworkService();

    }

    public NetworkService getNetworkService(){
        return networkService;
    }


}
