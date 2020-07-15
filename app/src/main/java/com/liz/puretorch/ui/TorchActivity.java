package com.liz.puretorch.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liz.androidutils.LogUtils;
import com.liz.androidutils.color.HSL;
import com.liz.androidutils.color.HSV;
import com.liz.androidutils.color.RGB;
import com.liz.androidutils.ui.AppCompatActivityEx;
import com.liz.androidutils.ui.FlingAction;
import com.liz.androidutils.ui.FlingActionFastLong;
import com.liz.androidutils.ui.FlingActionFastShort;
import com.liz.puretorch.R;
import com.liz.puretorch.app.MyApp;
import com.liz.puretorch.logic.ComDef;
import com.liz.puretorch.logic.DataLogic;

public class TorchActivity extends AppCompatActivityEx {

    public static final int REQUEST_CODE_COLOR_SETTINGS = 1;

    private RelativeLayout mLayoutBorder;
    private TextView tvFlingArea;
    private TextView tvColorInfo;

    private LinearLayout mLayoutColorEdits;
    private ColorEdit mEditRed;
    private ColorEdit mEditGreen;
    private ColorEdit mEditBlue;
    private ColorEdit mEditRedHex;
    private ColorEdit mEditGreenHex;
    private ColorEdit mEditBlueHex;
    private ColorEdit mEditHSV_H;
    private ColorEdit mEditHSV_S;
    private ColorEdit mEditHSV_V;
    private ColorEdit mEditHSL_H;
    private ColorEdit mEditHSL_S;
    private ColorEdit mEditHSL_L;
    private ColorEdit mEditLux;

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
                if (mLayoutColorEdits.getVisibility() == View.GONE) {
                    mLayoutColorEdits.setVisibility(View.VISIBLE);
                }
                else {
                    mLayoutColorEdits.setVisibility(View.GONE);
                }
            }
        });

        mLayoutColorEdits = findViewById(R.id.ll_color_edits);
        mEditRed = findViewById(R.id.edit_red);
        mEditGreen = findViewById(R.id.edit_green);
        mEditBlue = findViewById(R.id.edit_blue);
        mEditRedHex = findViewById(R.id.edit_red_hex);
        mEditGreenHex = findViewById(R.id.edit_green_hex);
        mEditBlueHex = findViewById(R.id.edit_blue_hex);
        mEditHSV_H = findViewById(R.id.edit_hsv_h);
        mEditHSV_S = findViewById(R.id.edit_hsv_s);
        mEditHSV_V = findViewById(R.id.edit_hsv_v);
        mEditHSL_H = findViewById(R.id.edit_hsl_h);
        mEditHSL_S = findViewById(R.id.edit_hsl_s);
        mEditHSL_L = findViewById(R.id.edit_hsl_l);
        mEditLux = findViewById(R.id.edit_lux);

        mEditRed.setMinMax(RGB.MIN, RGB.MAX);
        mEditGreen.setMinMax(RGB.MIN, RGB.MAX);
        mEditBlue.setMinMax(RGB.MIN, RGB.MAX);
        mEditHSV_H.setMinMax(HSV.HUE_MIN, HSV.HUE_MAX);
        mEditHSV_S.setMinMax(HSV.SAT_MIN, HSV.SAT_MAX);
        mEditHSV_V.setMinMax(HSV.VAL_MIN, HSV.VAL_MAX);
        mEditHSL_H.setMinMax(HSL.H_MIN, HSL.H_MAX);
        mEditHSL_S.setMinMax(HSL.S_MIN, HSL.S_MAX);
        mEditHSL_L.setMinMax(HSL.L_MIN, HSL.L_MAX);
        mEditLux.setMinMax(ComDef.LUX_MIN, ComDef.LUX_MAX);

        mEditRed.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateRed(text)) {
                    LogUtils.td("update red = " + DataLogic.red);
                    setEditRedHex();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditGreen.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateGreen(text)) {
                    LogUtils.td("update green = " + DataLogic.green);
                    setEditGreenHex();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditBlue.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateBlue(text)) {
                    LogUtils.td("update blue = " + DataLogic.blue);
                    setEditBlueHex();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditRedHex.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateRedHex(text)) {
                    LogUtils.td("update red hex = " + DataLogic.getRedHexStr());
                    setEditRed();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditGreenHex.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateGreenHex(text)) {
                    LogUtils.td("update green hex = " + DataLogic.getGreenHexStr());
                    setEditGreen();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditBlueHex.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateBlueHex(text)) {
                    LogUtils.td("update blue hex = " + DataLogic.getBlueHexStr());
                    setEditRed();
                    setEditHSV();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditHSV_H.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSV_H(text)) {
                    LogUtils.td("update hsv h = " + DataLogic.getHSV_HStr());
                    setEditRGB();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditHSV_S.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSV_S(text)) {
                    LogUtils.td("update hsv s = " + DataLogic.getHSV_SStr());
                    setEditRGB();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditHSV_V.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSV_V(text)) {
                    LogUtils.td("update hsv v = " + DataLogic.getHSV_VStr());
                    setEditRGB();
                    setEditHSL();
                    updateColors();
                }
            }
        });

        mEditHSL_H.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSL_H(text)) {
                    LogUtils.td("update hsl h = " + DataLogic.getHSL_HStr());
                    setEditRGB();
                    setEditHSV();
                    updateColors();
                }
            }
        });

        mEditHSL_S.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSL_S(text)) {
                    LogUtils.td("update hsl s = " + DataLogic.getHSL_SStr());
                    setEditRGB();
                    setEditHSV();
                    updateColors();
                }
            }
        });

        mEditHSL_L.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateHSL_L(text)) {
                    LogUtils.td("update hsl l = " + DataLogic.getHSL_LStr());
                    setEditRGB();
                    setEditHSV();
                    updateColors();
                }
            }
        });

        mEditLux.setEditCallback(new ColorEdit.EditCallback() {
            @Override
            public void onTextChanged(String text) {
                if (DataLogic.updateLux(text)) {
                    LogUtils.td("update lux = " + DataLogic.getLuxStr());
                    updateLux();
                }
            }
        });

        initFlingActions();
        updateUI();
        updateEdits();
    }

    protected void updateEdits() {
        setEditRGB();
        setEditHSL();
        setEditHSV();
        setEditLux();
    }

    protected void setEditRed() {
        mEditRed.updateText(DataLogic.getRedStr());
    }

    protected void setEditGreen() {
        mEditGreen.updateText(DataLogic.getGreenStr());
    }

    protected void setEditBlue() {
        mEditBlue.updateText(DataLogic.getBlueStr());
    }

    protected void setEditRGB() {
        setEditRed();
        setEditGreen();
        setEditBlue();
        setEditRedHex();
        setEditGreenHex();
        setEditBlueHex();
    }

    protected void setEditRedHex() {
        mEditRedHex.updateText(DataLogic.getRedHexStr());
    }

    protected void setEditGreenHex() {
        mEditGreenHex.updateText(DataLogic.getGreenHexStr());
    }

    protected void setEditBlueHex() {
        mEditBlueHex.updateText(DataLogic.getBlueHexStr());
    }

    protected void setEditHSV_H() {
        mEditHSV_H.updateText(DataLogic.getHSV_HStr());
    }

    protected void setEditHSV_S() {
        mEditHSV_S.updateText(DataLogic.getHSV_SStr());
    }

    protected void setEditHSV_V() {
        mEditHSV_V.updateText(DataLogic.getHSV_VStr());
    }

    protected void setEditHSV() {
        setEditHSV_H();
        setEditHSV_S();
        setEditHSV_V();
    }

    protected void setEditHSL_H() {
        mEditHSL_H.updateText(DataLogic.getHSL_HStr());
    }

    protected void setEditHSL_S() {
        mEditHSL_S.updateText(DataLogic.getHSL_SStr());
    }

    protected void setEditHSL_L() {
        mEditHSL_L.updateText(DataLogic.getHSL_LStr());
    }

    protected void setEditHSL() {
        setEditHSL_H();
        setEditHSL_S();
        setEditHSL_L();
    }

    protected void setEditLux() {
        mEditLux.updateText(DataLogic.getLuxStr());
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
    }

    @Override
    public void onMove(Point p1, Point p2, float dx, float dy, float d, float b) {
        int action = DataLogic.checkMoving(p1, p2, dx, dy, d, b);
        switch (action) {
            case DataLogic.MOVING_ACTION_RED:
            case DataLogic.MOVING_ACTION_GREEN:
            case DataLogic.MOVING_ACTION_BLUE:
            case DataLogic.MOVING_ACTION_GRAY:
                updateColors();
                updateEdits();
                break;
            case DataLogic.MOVING_ACTION_LUX:
                updateLux();
                break;
            default:
                break;
        }
    }

    protected void updateLux() {
        LogUtils.trace("lux = " + DataLogic.lux);
        setEditLux();
        setColorInfo();  // lux info involved
        changeAppBrightness(TorchActivity.this, DataLogic.lux);
    }

    public static void changeAppBrightness(Activity activity, int brightness) {
        LogUtils.trace("brightness = " + brightness);
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
        updateEdits();
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
