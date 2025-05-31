package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static cn.aiclr.jvm.juc.CallableInterfaceForInteger.compute;

class CallableInterfaceTest {

    private static final Logger logger = LoggerFactory.getLogger(CallableInterfaceTest.class);

    @Test
    @DisplayName("双线程计算1加到100总和")
    void calculate1To100() {
        CallableInterfaceForInteger task1 = new CallableInterfaceForInteger();
        task1.setStart(1);
        task1.setEnd(49);
        //Callable有返回值需要接收，使用FutureTask（Future实现类）实现类，接收运算结果
        //FutureTask也能用于闭锁
        //Future 接口提供了以下关键方法：
        //
        //get(): 获取结果，会阻塞直到计算完成
        //
        //get(long timeout, TimeUnit unit): 带超时的获取结果
        //
        //isDone(): 检查任务是否完成
        //
        //cancel(boolean mayInterruptIfRunning): 尝试取消任务
        //
        //isCancelled(): 检查任务是否被取消
        FutureTask<Integer> futureTask1 = new FutureTask<>(task1);
        new Thread(futureTask1).start();

        CallableInterfaceForInteger task2 = new CallableInterfaceForInteger();
        task2.setStart(50);
        task2.setEnd(100);

        FutureTask<Integer> futureTask2 = new FutureTask<>(task2);
        new Thread(futureTask2).start();

        try {
            //1加到49=1225
            // 每次加法 Thread.sleep(10), 设置800豪秒超时
            Integer result1 = futureTask1.get(800, TimeUnit.MILLISECONDS);
            Assertions.assertEquals(1225, result1);
            //50加到100=3825
            // 每次加法 Thread.sleep(10), 设置0.8秒超时
            Integer result2 = futureTask2.get(800, TimeUnit.MILLISECONDS);
            Assertions.assertEquals(3825, result2);
            //两条线程结果汇总得到1加到100=5050
            Assertions.assertEquals(5050, result1 + result2);
            //检查线程运行中结果有没有执行-----------并没有执行，只有等线程计算出结果后才会获取结果，可以用于闭锁
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                // 处理IO异常
            }
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            //超时中断任务
            futureTask1.cancel(true);
            futureTask2.cancel(true);
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("与 ExecutorService 结合使用")
    void executorServiceTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CallableInterfaceForInteger task1 = new CallableInterfaceForInteger();
        task1.setStart(1);
        task1.setEnd(49);
        CallableInterfaceForInteger task2 = new CallableInterfaceForInteger();
        task2.setStart(50);
        task2.setEnd(100);

        List<Future<Integer>> futureList = new ArrayList<>();
        futureList.add(executorService.submit(task1));
        futureList.add(executorService.submit(task2));
        Integer result = 0;
        try {
            for (Future<Integer> future : futureList) {
                if (Objects.nonNull(future)) {
                    result += future.get(800, TimeUnit.MILLISECONDS);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                // 处理IO异常
            }
            logger.error("{}", e.getMessage(), e);
        } catch (TimeoutException e) {
            //超时中断任务
            futureList.stream().filter(Objects::nonNull).forEach(it -> it.cancel(true));
            logger.error("{}", e.getMessage(), e);
        }
        Assertions.assertEquals(5050, result);
    }

    /**
     * 异步执行：supplyAsync/runAsync 启动异步任务。
     * <p>
     * 链式调用：thenApply、thenAccept、thenRun 处理结果。
     * <p>
     * 任务组合：thenCompose、thenCombine、allOf/anyOf。
     * <p>
     * 异常处理：exceptionally、handle。
     * <p>
     * 线程池控制：通过 Executor 自定义线程池。
     */
    @Test
    @DisplayName("supplyAsync 默认使用 ForkJoinPool.commonPool() 执行任务 (java 8+)")
    void ForkJoinPoolTest() {
        //CompletableFuture.supplyAsync 默认使用 ForkJoinPool.commonPool() 执行任务
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> compute(1, 49));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> compute(50, 100));
        //合并两个任务的结果
        CompletableFuture<Integer> futureResult = future1.thenCombine(future2, Integer::sum);
        try {
            Assertions.assertEquals(5050, futureResult.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                // 处理IO异常
            }
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("supplyAsync 指定自定义线程池执行任务 (java 8+)")
    void ExecutorTest() {
        //CompletableFuture.supplyAsync 指定自定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> compute(1, 49), executorService);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> compute(50, 100), executorService);
        //合并两个任务的结果
        CompletableFuture<Integer> futureResult = future1.thenCombine(future2, Integer::sum);
        try {
            Assertions.assertEquals(5050, futureResult.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                // 处理IO异常
            }
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("runAsync 无返回值")
    void runAsync() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 异步任务（无返回值）
            logger.info("Task is running");
        });
    }

    @Test
    @DisplayName("同步处理结果 thenApply")
    void thenApply() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World"); // 同步转换结果
        try {
            Assertions.assertEquals("Hello World", future.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("消费结果，无返回值 thenAccept")
    void thenAccept() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenAccept(s -> logger.info("Result: {}", s)); // 输出 "Result: Hello"
    }

    @Test
    @DisplayName("任务完成后执行操作 thenRun")
    void thenRun() {
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenRun(() -> logger.info("Task finished"));
    }

    @Test
    @DisplayName("链式依赖 thenCompose")
    void thenCompose() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        try {
            Assertions.assertEquals("Hello World", future.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("合并两个任务的结果 thenCombine")
    void thenCombine() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> " World");
        CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + s2);
        try {
            Assertions.assertEquals("Hello World", combined.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("等待多个任务 allOf")
    void allOf() {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> "Task1");
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "Task2");

        // 等待所有任务完成
        CompletableFuture<Void> all = CompletableFuture.allOf(task1, task2);
        all.thenRun(() -> logger.info("All tasks done"));
    }

    @Test
    @DisplayName("等待多个任务 anyOf")
    void anyOf() {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> "Task1");
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "Task2");

        // 任意一个任务完成
        CompletableFuture<Object> any = CompletableFuture.anyOf(task1, task2);
        any.thenAccept(result -> logger.info("First result: {}", result));
    }

    @Test
    @DisplayName("捕获异常并返回默认值 exceptionally")
    void exceptionally() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("Error!");
            return "Success";
        }).exceptionally(ex -> {
            logger.info("Exception: {}", ex.getMessage(), ex);
            return "Fallback";
        });
        try {
            Assertions.assertEquals("Fallback", future.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("无论成功/失败都会执行 handle")
    void handle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .handle((result, ex) -> {
                    if (ex != null) return "Error occurred";
                    return result + " World";
                });
        try {
            Assertions.assertEquals("Hello World", future.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("超时控制 java9+")
    void orTimeout() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    return "Result";
                }).orTimeout(1, TimeUnit.SECONDS) // 超时设置
                .exceptionally(ex -> "Timeout occurred");
        try {
            Assertions.assertEquals("Timeout occurred", future.get());
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}
