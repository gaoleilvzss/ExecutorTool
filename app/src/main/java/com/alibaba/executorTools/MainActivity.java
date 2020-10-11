package com.alibaba.executorTools;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.permissionx.guolindev.PermissionX;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionX.init(this).permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> Toast.makeText(MainActivity.this, "get permission success", Toast.LENGTH_SHORT).show());
        ExecutorUtils executorUtils = new ExecutorUtils();
        findViewById(R.id.btn).setOnClickListener(view -> {
            for (int i = 0; i < 1000; i++) {
                executorUtils.putTask(new TestTask(executorUtils,i));
            }
        });

    }
}