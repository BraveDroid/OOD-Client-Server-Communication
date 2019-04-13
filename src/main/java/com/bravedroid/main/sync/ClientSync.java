package com.bravedroid.main.sync;

import com.bravedroid.main.Data;

public class ClientSync {
    public static final ClientSync INSTANCE = new ClientSync();

    private ClientSync() {
    }

    public void consumeService(ServiceSync serviceSync) {
        Data mohsenData = serviceSync.getData("Mohsen", 62);
        System.out.println("*** mohsen data is:" + mohsenData);
    }
}
