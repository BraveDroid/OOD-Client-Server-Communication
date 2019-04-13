package com.bravedroid.main.async;

import com.bravedroid.main.Data;

public class ClientAsync {
    private static ClientAsync INSTANCE;

    private ClientAsync() {
    }

    public static ClientAsync getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ClientAsync();
        }
        return INSTANCE;
    }

    public void consumeService(ServiceAsync serviceAsync) {
        serviceAsync.loadData("Rachida1", 59, data -> println("first call ", data));
        serviceAsync.loadData("Rachida3", 59, System.out::println);

        serviceAsync.loadData("Rachida2", 59, new ServiceAsync.Callback() {
            @Override
            public void onDataLoaded(Data data) {
                println("second call", data);
                System.out.println("executing second call in " + Thread.currentThread().getName());
            }
        });
    }

    private void println(String prefix, Object obj) {
        System.out.println(prefix + " " + obj);
    }
}
