package com.bravedroid.main.async;

import com.bravedroid.main.Data;

public class ServiceAsync {
    private static ServiceAsync INSTANCE;

    private ServiceAsync() {
    }

    public synchronized static ServiceAsync getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ServiceAsync();
        }
        return INSTANCE;
    }

    public void loadData(String name, int age, Callback callback) {
        new Thread(() -> {
            try {
                System.out.println("executing in " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Data data = new Data(name, age);
            callback.onDataLoaded(data);
        }, name+"WorkerThread ").start();
    }

    @FunctionalInterface
    interface Callback {
        void onDataLoaded(Data data);
    }
}
