package com.liz.puretorch.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liz.androidutils.ui.AppCompatActivityEx;
import com.liz.androidutils.ui.FlingAction;
import com.liz.androidutils.ui.FlingActionFastLong;
import com.liz.androidutils.ui.FlingActionFastShort;
import com.liz.puretorch.R;
import com.liz.puretorch.app.MyApp;
import com.liz.puretorch.logic.DataLogic;

public class TorchActivity extends AppCompatActivityEx {

    public static final int REQUEST_CODE_COLOR_SETTINGS = 1;

    private LinearLayout mLayoutBorder;
    private TextView tvFlingArea;
    private TextView tvColorInfo;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProp(PROP_SCREEN_ORIENTATION_PORTRAIT);
        setProp(PROP_NO_TITLE);
        setProp(PROP_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_torch);

        mLayoutBorder = findViewById(R.id.ll_border);

        tvFlingArea = findViewById(R.id.tv_fling_area);
        tvColorInfo = findViewById(R.id.tv_color_info);
        tvColorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TorchActivity.this, ColorSettingsActivity.class), REQUEST_CODE_COLOR_SETTINGS);
            }
        });

        initFlingActions();
        updateUI();
    }

    protected void initFlingActions() {
        addFlingView(findViewById(R.id.tv_fling_area));
        addFlingAction(new FlingActionFastShort(new FlingAction.FlingCallback() {
                    @Override
                    public void onFlingAction(FlingAction action) {
                        DataLogic.switchTorch();
                        setTorchBorder();
                    }
                })
        );
        addFlingAction(new FlingActionFastLong(new FlingAction.FlingCallback() {
                    @Override
                    public void onFlingAction(FlingAction action) {
                        MyApp.exitApp();
                    }
                })
        );
        addFlingAction(new FlingActionBars(new FlingActionBars.FlingActionBarsCallback() {
                    @Override
                    public void onRedChanged(int delta) {
                        DataLogic.onRedChanged(delta);
                        updateColors();
                    }

                    @Override
                    public void onGreenChanged(int delta) {
                        DataLogic.onGreenChanged(delta);
                        updateColors();
                    }

                    @Override
                    public void onBlueChanged(int delta) {
                        DataLogic.onBlueChanged(delta);
                        updateColors();
                    }

                    @Override
                    public void onLightnessChanged(int delta) {
                        DataLogic.onLuxChanged(delta);
                        changeAppBrightness(TorchActivity.this, DataLogic.lux);
                        setColorInfo();
                    }

                    @Override
                    public void onGrayLevelChanged(int grayLevel) {
                        DataLogic.onGrayLevelChanged(grayLevel);
                        updateColors();
                    }
                })
        );
    }

    public static void changeAppBrightness(Activity activity, int brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    public void updateUI() {
        setTorchBorder();
        updateColors();
    }

    public void setTorchBorder() {
        mLayoutBorder.setBackgroundColor(DataLogic.isTorchOn() ? Color.RED : Color.GREEN);
    }

    public void updateColors() {
        setColorBackground();
        setColorInfo();
    }

    public void setColorBackground() {
        tvFlingArea.setBackgroundColor(DataLogic.getBackgroundColor());
    }

    public void setColorInfo() {
        tvColorInfo.setText(DataLogic.getColorInfo());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COLOR_SETTINGS) {
            updateUI();
        }
    }
}
