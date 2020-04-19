package com.zscat.mallplus.utils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IdWorker {
    private static final Sequence worker = new Sequence();

    public IdWorker() {
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int j = 0; j < 5; ++j) {
            executorService.submit(() -> {
                for (int i = 0; i < 30000; ++i) {
                    System.out.println(Thread.currentThread().getId() + "--" + getId());
                }

            });
        }

        executorService.shutdown();
    }

    public static long getId() {
        return worker.nextId();
    }

    private static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
