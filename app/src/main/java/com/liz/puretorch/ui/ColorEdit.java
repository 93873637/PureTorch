package com.liz.puretorch.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.liz.androidutils.LogUtils;
import com.liz.androidutils.ui.InputFilterMinMax;

@SuppressLint("AppCompatCustomView")
public class ColorEdit extends EditText {

    private String mLastValue = "";
    private EditCallback mCallback = null;

    public interface EditCallback {
        void onTextChanged(String val);
    };

    private TextWatcher mEditWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            LogUtils.td("charSequence=" + charSequence);
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            LogUtils.td("charSequence=" + charSequence);
            if (!TextUtils.isEmpty(charSequence.toString())) {
                mLastValue = charSequence.toString();
            }
            if (mCallback != null) {
                mCallback.onTextChanged(mLastValue);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
            LogUtils.td("editable=" + editable.toString());
        }
    };

    public ColorEdit(Context context) {
        super(context);
        initEdit();
    }

    public ColorEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEdit();
    }

    public ColorEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEdit();
    }

    public ColorEdit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initEdit();
    }

    protected void initEdit() {
        addTextChangedListener(mEditWatcher);
        setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(ColorEdit.this.getText())) {
                        setText(mLastValue);
                    }
                }
            }
        });
    }

    public void setEditCallback(EditCallback callback) {
        mCallback = callback;
    }

    public void updateText(String text) {
        removeTextChangedListener(mEditWatcher);
        setText(text);
        addTextChangedListener(mEditWatcher);
    }

    public void setMinMax(int min, int max) {
        setFilters(new InputFilter[]{new InputFilterMinMax(min, max)});
    }
}
