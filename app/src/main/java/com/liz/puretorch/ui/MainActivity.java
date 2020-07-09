package com.liz.puretorch.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.liz.androidutils.LogUtils;
import com.liz.androidutils.ui.AppCompatActivityEx;
import com.liz.puretorch.R;

public class MainActivity extends AppCompatActivityEx {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                },
                new AppCompatActivityEx.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        LogUtils.trace();
                        Toast.makeText(MainActivity.this, "onPermissionGranted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TorchActivity.class));
                        MainActivity.this.finish();
                    }
                    @Override
                    public void onPermissionDenied() {
                        LogUtils.trace();
                        Toast.makeText(MainActivity.this, "onPermissionDenied", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                    }
                }
        );
    }
}
