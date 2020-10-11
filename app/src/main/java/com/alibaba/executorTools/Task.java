package com.alibaba.executorTools;

/**
 * create by gaolei
 * on 2020/10/11
 */
public abstract class Task implements Runnable {

    public Task() {
    }

    @Override
    public final void run() {
            execute();
    }

    protected abstract void execute();
}
