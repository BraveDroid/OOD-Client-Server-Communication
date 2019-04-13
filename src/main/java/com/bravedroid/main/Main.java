package com.bravedroid.main;

import com.bravedroid.main.async.ClientAsync;
import com.bravedroid.main.async.ServiceAsync;
import com.bravedroid.main.reactive.ClientReactive;
import com.bravedroid.main.reactive.ServiceReactive;
import com.bravedroid.main.sync.ClientSync;
import com.bravedroid.main.sync.ServiceSync;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Main {
    private static Disposable disposable;

    private static CommunicationPattern communicationPattern = CommunicationPattern.SYNC;

    public static void main(String[] args) throws Exception {
        switch (communicationPattern) {
            case SYNC:
                //the client uses the service to read the data then print it (the sync way)
                ClientSync.INSTANCE.consumeService(ServiceSync.INSTANCE);
                break;
            case ASYNC:
                //the client uses the service to read the data then print it (the async way)
                ClientAsync.getInstance().consumeService(ServiceAsync.getInstance());
                break;
            case REACTIVE:
                //the client uses the service to read the data then print it (the reactive way) without subscribing no deed to dispose, sync way
                ClientReactive.getInstance().consumeServiceSync(ServiceReactive.getInstance());
                //the client uses the service to read the data then print it (the reactive way) but unsubscribe/dispose with an async way
                disposable = ClientReactive.getInstance().consumeService(ServiceReactive.getInstance(), Main::onDestroy);
                //the client uses the service to read the data then print it (the reactive way) and also unsubscribe/dispose with a reactive ay
                Observable<Disposable> disposableObservable = ClientReactive.getInstance().consumeService(ServiceReactive.getInstance());
                Disposable[] mainDisposable = {null};
                disposableObservable
                        .subscribe(
                                disposableOfClientReactive -> disposableOfClientReactive.dispose(),
                                err -> {
                                },
                                () -> {
                                    onDestroy(mainDisposable [0]);
                                    //or I can directly use disposable[0].dispose();
                                },
                                d -> mainDisposable [0] = d
                        );

                break;
            default:
                throw new Exception();
        }


        System.out.println("finished");
        while (true) ;
    }

    private static void onDestroy() {
        disposable.dispose();
        System.out.println("****onDestroy is called and disposable is disposed***");
        System.out.println("****dispose the disposable of ClientReactive---> ServiceReactive***");
    }

    private static void onDestroy(Disposable disposable) {
        disposable.dispose();
        System.out.println("****onDestroy is called and disposable is disposed***");
        System.out.println("****dispose the disposable of main---> ClientReactive***");
    }


    enum CommunicationPattern {
        SYNC,
        ASYNC,
        REACTIVE
    }
}
