package com.liz.puretorch.app;

import com.liz.androidutils.LogUtils;
import com.liz.androidutils.app.AppEx;
import com.liz.puretorch.logic.ComDef;
import com.liz.puretorch.logic.DataLogic;

/**
 * MyApp.java
 * Created by liz on 18-1-8.
 */

@SuppressWarnings("unused")
public class MyApp extends AppEx {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setTag(ComDef.APP_NAME);
        if (ComDef.DEBUG) {
            LogUtils.setLevel(LogUtils.LOG_LEVEL_V);
        }
        DataLogic.init();
    }
}
