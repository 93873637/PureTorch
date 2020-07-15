package com.liz.puretorch.ui;

import com.liz.androidutils.LogUtils;
import com.liz.androidutils.ui.FlingAction;

public class MoveActionBars extends FlingAction {

    public static final float FLING_BAR_VELOCITY_MAX = 3000;
    public static final float FLING_BAR_BEARING_AREA = 30;

    private FlingActionBarsCallback mCallback;
    public interface FlingActionBarsCallback {
        void onRedChanged(int delta);
        void onGreenChanged(int delta);
        void onBlueChanged(int delta);
        void onLightnessChanged(int delta);
        void onGrayLevelChanged(int delta);
    }

    public MoveActionBars(FlingActionBarsCallback callback) {
        mCallback = callback;
    }

    boolean isFlingHorizontal(float bearing) {
        return (b >= 0 && b <= FLING_BAR_BEARING_AREA)
                || (b >= 360 - FLING_BAR_BEARING_AREA && b < 360)
                || (b >= 180 - FLING_BAR_BEARING_AREA && b <= 180 + FLING_BAR_BEARING_AREA);
    }

    boolean isFlingVertical(float bearing) {
        return (b >= 90 - FLING_BAR_BEARING_AREA && b <= 90 + FLING_BAR_BEARING_AREA)
                || (b >= 270 - FLING_BAR_BEARING_AREA && b <= 270 + FLING_BAR_BEARING_AREA);
    }

    @Override
    public boolean checkFling() {
        LogUtils.td(this.toString());

        // filter out fast fling
        if (Math.abs(v) > FLING_BAR_VELOCITY_MAX) {
            LogUtils.td("fling too fast");
            return false;
        }

        if (isFlingHorizontal(b)) {
            int delta = (int)(dx * 255 / 1400);
            float startY = e1.getY();
            LogUtils.td("fling horizontal: delta=" + delta + ", startY=" + startY);
            if (startY < 750) {
                if (mCallback != null) {
                    mCallback.onRedChanged(delta);
                }
            }
            else if (startY < 1500) {
                if (mCallback != null) {
                    mCallback.onGreenChanged(delta);
                }
            }
            else {
                if (mCallback != null) {
                    mCallback.onBlueChanged(delta);
                }
            }
        }

        if (isFlingVertical(b)) {
            int delta = (int)(dy * 255 / 2250);
            float startX = e1.getX();
            LogUtils.td("fling vertical: delta=" + delta + ", startX=" + startX);
            if (startX < 750) {
                if (mCallback != null) {
                    mCallback.onLightnessChanged(delta);
                }
            }
            else {
                if (mCallback != null) {
                    mCallback.onGrayLevelChanged(delta);
                }
            }
        }

        return false;
    }
}
