package com.bravedroid.main.sync;


import com.bravedroid.main.Data;

public class ServiceSync {
    public static final ServiceSync INSTANCE = new ServiceSync();

    private ServiceSync() {
    }

    public Data getData(String name, int age) {
        return new Data(name, age);
    }

}
