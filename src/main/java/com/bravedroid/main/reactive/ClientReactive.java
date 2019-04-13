package com.bravedroid.main.reactive;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class ClientReactive {
    private static final ClientReactive INSTANCE = new ClientReactive();


    private ClientReactive() {
    }

    public static ClientReactive getInstance() {
        return INSTANCE;
    }

    public void  consumeServiceSync(ServiceReactive serviceReactive ) {
          serviceReactive
                .loadDataFromIO("Refka", 28)
                 .blockingSubscribe(
                        data -> {
                            System.out.println("executing onNext in " + Thread.currentThread().getName());
                            System.out.println("onNext, emission is running");
                            System.out.println("data received " + data);

                        }, error -> {
                            System.out.println("onError , emission is terminated");
                        },
                        () -> {
                            System.out.println("onComplete emission is terminated");
                            System.out.println("executing onComplete in " + Thread.currentThread().getName());
                         }
                );
     }

    public Disposable consumeService(ServiceReactive serviceReactive, OnCompleteListener listener) {
        return serviceReactive
                .loadDataFromIO("Refka", 28)
                .observeOn(Schedulers.computation())
                .subscribe(
                        data -> {
                            System.out.println("executing onNext in " + Thread.currentThread().getName());
                            System.out.println("onNext, emission is running");
                            System.out.println("data received " + data);
                            if (data.age < -1) listener.onComplete();

                        }, error -> {
                            System.out.println("onError , emission is terminated");
                        },
                        () -> {
                            System.out.println("onComplete emission is terminated");
                            System.out.println("executing onComplete in " + Thread.currentThread().getName());
                            listener.onComplete();
                        }
                );
        //.blockingSubscribe();
    }

    public Observable<Disposable> consumeService(ServiceReactive serviceReactive) {
        BehaviorSubject<Disposable> subject = BehaviorSubject.create();
        final Disposable[] disposable = new Disposable[1];

        serviceReactive
                .loadDataFromIO("Refka", 28)
                .observeOn(Schedulers.computation())
                .subscribe(
                        data -> {
                            System.out.println("executing onNext in " + Thread.currentThread().getName());
                            System.out.println("onNext, emission is running");
                            System.out.println("data received " + data);

                            if (data.age < -1) {
                                subject.onNext(disposable[0]);
                                subject.onComplete();
                            }
                        }, error -> {
                            System.out.println("onError , emission is terminated");
                        },
                        () -> {
                            System.out.println("onComplete emission is terminated");
                            System.out.println("executing onComplete in " + Thread.currentThread().getName());
                            subject.onNext(disposable[0]);
                            subject.onComplete();

                        },
                        disposable1 -> {
                            disposable[0] = disposable1;
                        }
                );

        return subject;
    }

    @FunctionalInterface
    public interface OnCompleteListener {
        void onComplete();
    }
}
