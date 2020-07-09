package com.liz.puretorch.logic;

import com.liz.androidutils.CameraUtils;
import com.liz.androidutils.ColorUtils;
import com.liz.androidutils.LogUtils;
import com.liz.androidutils.NumUtils;
import com.liz.androidutils.color.RGB;
import com.liz.puretorch.app.MyApp;

/**
 * DataLogic.java
 * Created by liz on 2018/3/8.
 */

@SuppressWarnings("unused")
public class DataLogic extends ComDef {

    private static boolean mEnableTorch = false;

    public static int alpha = 0;
    public static int red = 0;
    public static int green = 0;
    public static int blue = 0;
    public static int lux = 0;

    public static void init() {
        alpha = ALPHA_MAX;
        red = RED_MAX;
        green = GREEN_MAX;
        blue = BLUE_MAX;
        lux = LUX_MAX;
        switchTorch();
    }

    public static int getBackgroundColor() {
        return 0xff000000 + (red << 16) + (green << 8) + blue;
    }

    public static String getColorInfo() {
        return red + "," + green + "," + blue +
                "(" +
                NumUtils.int2HexStrCompact(red).toUpperCase() + "," +
                NumUtils.int2HexStrCompact(green).toUpperCase() + "," +
                NumUtils.int2HexStrCompact(blue).toUpperCase() +
                ")" +
                "   " +
                getHsvStr() +
                "   " +
                getHslStr() +
                "   " +
                lux;
    }

    public static String getHsvStr() {
        return ColorUtils.RGB2HSV(new RGB(red, green, blue)).toString();
    }

    public static String getHslStr() {
        return ColorUtils.RGB2HSL(new RGB(red, green, blue)).toString();
    }

    public static void onRedChanged(int delta) {
        red += delta;
        if (red < RED_MIN) {
            red = RED_MIN;
        }
        if (red > RED_MAX) {
            red = RED_MAX;
        }
        LogUtils.td("delta=" + delta + ", red=" + red);
    }

    public static void onGreenChanged(int delta) {
        green += delta;
        if (green < GREEN_MIN) {
            green = GREEN_MIN;
        }
        if (green > GREEN_MAX) {
            green = GREEN_MAX;
        }
        LogUtils.td("delta=" + delta + ", green=" + green);
    }

    public static void onBlueChanged(int delta) {
        blue += delta;
        if (blue < BLUE_MIN) {
            blue = BLUE_MIN;
        }
        if (blue > BLUE_MAX) {
            blue = BLUE_MAX;
        }
        LogUtils.td("delta=" + delta + ", blue=" + blue);
    }

    public static void onLuxChanged(int delta) {
        lux += delta;
        if (lux < LUX_MIN) {
            lux = LUX_MIN;
        }
        if (lux > LUX_MAX) {
            lux = LUX_MAX;
        }
        LogUtils.td("delta=" + delta + ", lux=" + lux);
    }

    public static void onGrayLevelChanged(int grayLevel) {
        if (grayLevel < GRAY_MIN) {
            grayLevel = GRAY_MIN;
        }
        if (grayLevel > GRAY_MAX) {
            grayLevel = GRAY_MAX;
        }
        red = grayLevel;
        green = grayLevel;
        blue = grayLevel;
        LogUtils.td("grayLevel=" + grayLevel);
    }

    public static void switchTorch() {
        mEnableTorch = !mEnableTorch;
        CameraUtils.enableTorch(MyApp.getAppContext(), mEnableTorch);
    }

    public static boolean isTorchOn() {
        return mEnableTorch;
    }
}
