package com.shichen.mobapisample.config;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by shichen on 2017/11/6.
 *
 * @author shichen 754314442@qq.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    protected RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
}
