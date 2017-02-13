package com.example.mrrobot.ssaattendance;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by MrRobot on 2/11/2017.
 *
 *
 */

public class SSAAttendance extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
