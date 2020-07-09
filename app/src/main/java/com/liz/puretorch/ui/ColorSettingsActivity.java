package com.liz.puretorch.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.liz.androidutils.ui.AppCompatActivityEx;
import com.liz.puretorch.R;

public class ColorSettingsActivity extends AppCompatActivityEx {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProp(PROP_SCREEN_ORIENTATION_PORTRAIT);
        setProp(PROP_NO_TITLE);
        setProp(PROP_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_color_settings);
    }
}
