package com.bravedroid.main.reactive;


import com.bravedroid.main.Data;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class ServiceReactive {
    private static final ServiceReactive INSTANCE = new ServiceReactive();


    private ServiceReactive() {
    }

    public static ServiceReactive getInstance() {
        return INSTANCE;
    }

    public Observable<Data> loadDataFromIO(String name, int age) {
        return Observable.create((ObservableOnSubscribe<Data>) emitter -> {
            System.out.println("executing in Observable creation in " + Thread.currentThread().getName());
            Thread.sleep(3000);
            Data data = new Data(name, age);
            emitter.onNext(data);
            System.out.println("we are emitting in " + Thread.currentThread().getName());
            Thread.sleep(500);
            emitter.onNext(data);
            emitter.onNext(new Data("3affat", -5));
            emitter.onNext(data);
            emitter.onNext(data);
            emitter.onComplete();

        }).subscribeOn(Schedulers.io());
    }
}
