package com.alibaba.executorTools;

import android.util.Log;

/**
 * create by gaolei
 * on 2020/10/10
 */
public class TestTask extends Task {
    ExecutorUtils executorUtils;
    private int id;

    public TestTask(ExecutorUtils executorUtils, int id) {
        this.executorUtils = executorUtils;
        this.id = id;
    }

    @Override
    protected void execute() {
        try {
            Thread.sleep(2000);
            Log.d("winter", "test=" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis() + ",id = " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.d("winter", "finally time=" + System.currentTimeMillis());
            executorUtils.finished(this);
        }
    }

}
