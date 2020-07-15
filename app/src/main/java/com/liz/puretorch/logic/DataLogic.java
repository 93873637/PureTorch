package com.liz.puretorch.logic;

import android.graphics.Point;

import com.liz.androidutils.CameraUtils;
import com.liz.androidutils.ColorUtils;
import com.liz.androidutils.LogUtils;
import com.liz.androidutils.NumUtils;
import com.liz.androidutils.StringUtils;
import com.liz.androidutils.color.HSL;
import com.liz.androidutils.color.HSV;
import com.liz.androidutils.color.RGB;
import com.liz.androidutils.ext.RichResult;
import com.liz.puretorch.app.MyApp;

/**
 * DataLogic.java
 * Created by liz on 2018/3/8.
 */

@SuppressWarnings("unused")
public class DataLogic extends ComDef {

    private static boolean mEnableTorch = false;

    public static final float MOVING_BEARING_AREA = 30;
    public static final int MOVING_ACTION_NULL = 0;
    public static final int MOVING_ACTION_RED = 1;
    public static final int MOVING_ACTION_GREEN = 2;
    public static final int MOVING_ACTION_BLUE = 3;
    public static final int MOVING_ACTION_GRAY = 4;
    public static final int MOVING_ACTION_LUX = 5;

    public static int alpha = 0;
    public static int red = 0;
    public static int green = 0;
    public static int blue = 0;
    public static int gray = 0;
    public static int lux = 0;

    public static void init() {
        alpha = ALPHA_MAX;
        red = RED_MAX;
        green = GREEN_MAX;
        blue = BLUE_MAX;
        gray = GRAY_MAX;
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
                "  " +
                getHSVStr() +
                "  " +
                getHSLStr() +
                "  " +
                lux;
    }

    public static boolean updateRed(CharSequence charSeq) {
        return updateRed(charSeq.toString());
    }

    public static boolean updateRed(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.str2Int(str, RED_MIN, RED_MAX, rr)) {
            red = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateRedHex(CharSequence charSeq) {
        return updateRedHex(charSeq.toString());
    }

    public static boolean updateRedHex(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.strHex2Int(str, RED_MIN, RED_MAX, rr)) {
            red = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateGreenHex(CharSequence charSeq) {
        return updateGreenHex(charSeq.toString());
    }

    public static boolean updateGreenHex(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.strHex2Int(str, GREEN_MIN, GREEN_MAX, rr)) {
            green = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateBlueHex(CharSequence charSeq) {
        return updateBlueHex(charSeq.toString());
    }

    public static boolean updateBlueHex(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.strHex2Int(str, BLUE_MIN, BLUE_MAX, rr)) {
            blue = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateHSV_H(CharSequence charSeq) {
        return updateHSV_H(charSeq.toString());
    }

    public static boolean updateHSV_H(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSV.HUE_MIN, HSV.HUE_MAX, rr)) {
            return false;
        }
        updateHSV_H(rr.result_int);
        return true;
    }

    public static void updateHSV_H(int n) {
        HSV hsv = DataLogic.getHSV();
        hsv.setH(n);
        updateColor(hsv);
    }

    public static boolean updateHSV_S(CharSequence charSeq) {
        return updateHSV_S(charSeq.toString());
    }

    public static boolean updateHSV_S(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSV.SAT_MIN, HSV.SAT_MAX, rr)) {
            return false;
        }
        updateHSV_S(rr.result_int);
        return true;
    }

    public static void updateHSV_S(int n) {
        HSV hsv = DataLogic.getHSV();
        hsv.setS(n);
        updateColor(hsv);
    }

    public static boolean updateHSV_V(CharSequence charSeq) {
        return updateHSV_V(charSeq.toString());
    }

    public static boolean updateHSV_V(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSV.VAL_MIN, HSV.VAL_MAX, rr)) {
            return false;
        }
        updateHSV_V(rr.result_int);
        return true;
    }

    public static void updateHSV_V(int n) {
        HSV hsv = DataLogic.getHSV();
        hsv.setH(n);
        updateColor(hsv);
    }

    public static boolean updateHSL_H(CharSequence charSeq) {
        return updateHSL_H(charSeq.toString());
    }

    public static boolean updateHSL_H(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSL.H_MIN, HSL.H_MAX, rr)) {
            return false;
        }
        updateHSL_H(rr.result_int);
        return true;
    }

    public static void updateHSL_H(int h) {
        HSL hsl = DataLogic.getHSL();
        hsl.setH(h);
        updateColor(hsl);
    }

    public static boolean updateHSL_S(CharSequence charSeq) {
        return updateHSL_S(charSeq.toString());
    }

    public static boolean updateHSL_S(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSL.S_MIN, HSL.S_MAX, rr)) {
            return false;
        }
        updateHSL_S(rr.result_int);
        return true;
    }

    public static void updateHSL_S(int s) {
        HSL hsl = DataLogic.getHSL();
        hsl.setS(s);
        updateColor(hsl);
    }

    public static boolean updateHSL_L(CharSequence charSeq) {
        return updateHSL_L(charSeq.toString());
    }

    public static boolean updateHSL_L(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, HSL.L_MIN, HSL.L_MAX, rr)) {
            return false;
        }
        updateHSL_L(rr.result_int);
        return true;
    }

    public static void updateHSL_L(int l) {
        HSL hsl = DataLogic.getHSL();
        hsl.setL(l);
        updateColor(hsl);
    }

    public static void updateColor(HSV hsv) {
        updateColor(ColorUtils.HSV2RGB(hsv));
    }

    public static void updateColor(HSL hsl) {
        updateColor(ColorUtils.HSL2RGB(hsl));
    }

    public static void updateColor(RGB rgb) {
        red = rgb.r;
        green = rgb.g;
        blue = rgb.b;
    }

    public static boolean updateLux(CharSequence charSeq) {
        return updateLux(charSeq.toString());
    }

    public static boolean updateLux(String str) {
        RichResult rr = new RichResult();
        if (!StringUtils.str2Int(str, ComDef.LUX_MIN, ComDef.LUX_MAX, rr)) {
            return false;
        }
        lux = rr.result_int;
        return true;
    }

    public static boolean updateGreen(CharSequence charSeq) {
        return updateGreen(charSeq.toString());
    }

    public static boolean updateGreen(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.str2Int(str, GREEN_MIN, GREEN_MAX, rr)) {
            green = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateBlue(CharSequence charSeq) {
        return updateBlue(charSeq.toString());
    }

    public static boolean updateBlue(String str) {
        RichResult rr = new RichResult();
        if (StringUtils.str2Int(str, BLUE_MIN, BLUE_MAX, rr)) {
            blue = rr.result_int;
            return true;
        }
        else {
            return false;
        }
    }

    public static String getRedStr() {
        return "" + red;
    }

    public static String getGreenStr() {
        return "" + green;
    }

    public static String getBlueStr() {
        return "" + blue;
    }

    public static String getRedHexStr() {
        return NumUtils.int2HexStrCompact(red);
    }

    public static String getGreenHexStr() {
        return NumUtils.int2HexStrCompact(green);
    }

    public static String getBlueHexStr() {
        return NumUtils.int2HexStrCompact(blue);
    }

    public static String getHSV_HStr() {
        return getHSV().getHStr();
    }

    public static String getHSV_SStr() {
        return getHSV().getSStr();
    }

    public static String getHSV_VStr() {
        return getHSV().getVStr();
    }

    public static String getHSL_HStr() {
        return getHSL().getHStr();
    }

    public static String getHSL_SStr() {
        return getHSL().getSStr();
    }

    public static String getHSL_LStr() {
        return getHSL().getLStr();
    }

    public static String getLuxStr() {
        return "" + lux;
    }

    public static HSV getHSV() {
        return ColorUtils.RGB2HSV(new RGB(red, green, blue));
    }

    public static String getHSVStr() {
        return getHSV().toString(",");
    }

    public static HSL getHSL() {
        return ColorUtils.RGB2HSL(new RGB(red, green, blue));
    }

    public static String getHSLStr() {
        return getHSL().toString(",");
    }

    public static boolean isMovingHorizontal(float b) {
        return (b >= 0 && b <= MOVING_BEARING_AREA)
                || (b >= 360 - MOVING_BEARING_AREA && b < 360)
                || (b >= 180 - MOVING_BEARING_AREA && b <= 180 + MOVING_BEARING_AREA);
    }

    public static boolean isMovingVertical(float b) {
        return (b >= 90 - MOVING_BEARING_AREA && b <= 90 + MOVING_BEARING_AREA)
                || (b >= 270 - MOVING_BEARING_AREA && b <= 270 + MOVING_BEARING_AREA);
    }

    public static int checkMoving(Point p1, Point p2, float dx, float dy, float d, float b) {
        if (isMovingHorizontal(b)) {
            int delta = (int) (dx * 255 / 1400);
            int startY = p1.y;
            LogUtils.td("moving horizontal: delta=" + delta + ", startY=" + startY);
            if (startY < 750) {
                onRedChanged(delta);
                return MOVING_ACTION_RED;
            } else if (startY < 1500) {
                onGreenChanged(delta);
                return MOVING_ACTION_GREEN;
            } else {
                onBlueChanged(delta);
                return MOVING_ACTION_BLUE;
            }
        }
        if (isMovingVertical(b)) {
            int delta = (int) (dy * 255 / 2250);
            int startX = p1.x;
            LogUtils.td("moving vertical: delta=" + delta + ", startX=" + startX);
            if (startX < 750) {
                onLuxChanged(delta);
                return MOVING_ACTION_LUX;
            } else {
                onGrayChanged(delta);
                return MOVING_ACTION_GRAY;
            }
        }
        return MOVING_ACTION_NULL;
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

    public static void onGrayChanged(int delta) {
        gray += delta;
        if (gray < GRAY_MIN) {
            gray = GRAY_MIN;
        }
        if (gray > GRAY_MAX) {
            gray = GRAY_MAX;
        }
        red = gray;
        green = gray;
        blue = gray;
        LogUtils.td("delta=" + delta + ", gray=" + gray);
    }

    public static void switchTorch() {
        mEnableTorch = !mEnableTorch;
        CameraUtils.enableTorch(MyApp.getAppContext(), mEnableTorch);
    }

    public static boolean isTorchOn() {
        return mEnableTorch;
    }
}
