package com.company.concurrent.executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorTest {

    static ExecutorService severalThreads = Executors.newFixedThreadPool(2);
    static ExecutorService dynamicThreads = Executors.newCachedThreadPool();
    static ExecutorService singleThreads = Executors.newSingleThreadExecutor();
    static ExecutorService forkJoinThreads = Executors.newWorkStealingPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
        );
        System.out.println(forkJoinThreads.invokeAny(callables));
        forkJoinThreads.shutdown();
        System.out.println(severalThreads.invokeAny(callables));
        severalThreads.shutdown();
        System.out.println(dynamicThreads.invokeAny(callables));
        dynamicThreads.shutdown();

        System.out.println(singleThreads.invokeAny(callables));
        singleThreads.shutdown();
        if (singleThreads.awaitTermination(3, TimeUnit.MILLISECONDS)) {
            System.out.println("Not all tasks performed");
            singleThreads.shutdownNow();
        }

        forkJoinThreads.invokeAll(callables)
                .stream()
                .map(future -> {
                    String res = null;
                    try {
                        res = future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return res;
                })
                .forEach(System.out::println);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "task1", singleThreads);
        System.out.println(future.get());
        future = future.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " what?"));
        System.out.println(future.get());
        singleThreads.shutdown();

        schedule();
    }

    private static void schedule() {
        System.out.println("start ...");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(() -> System.out.println("delay"), 2, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(() -> System.out.println("delay"), 0, 2, TimeUnit.SECONDS);
        service.shutdown();
    }

}
