package com.alibaba.executorTools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * create by gaolei
 * on 2020/10/10
 */
public class ExecutorUtils {


    private int MAX_THREAD_NUM = 64;

    ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS
            , new SynchronousQueue<Runnable>(), threadFactory("test Dispatcher", false));

    private Deque<Task> ready = new ArrayDeque<>();
    private Deque<Task> running = new ArrayDeque<>();

    public void setMAX_THREAD_NUM(int MAX_THREAD_NUM) {
        this.MAX_THREAD_NUM = MAX_THREAD_NUM;
    }

    public void putTask(Task task) {
        ready.add(task);
        promoteAndExecute();
    }

    private void promoteAndExecute() {
        List<Task> executableTask = new ArrayList<>();
        synchronized (this) {
            for (Iterator<Task> i = ready.iterator(); i.hasNext(); ) {
                Task task = i.next();
                if (running.size() >= MAX_THREAD_NUM) {
                    break;
                }
                i.remove();
                executableTask.add(task);
                running.add(task);
            }
        }
        for (Task task : executableTask) {
            try {
                executorService.execute(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    void finished(Task task) {
        finished(running, task);
    }

    private void finished(Deque<Task> calls, Task task) {
        synchronized (this) {
            calls.remove(task);
            promoteAndExecute();
        }
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }
}
