package Demo;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureTasksWithExcutorDemo {
    public static void main(String[] args) {
        // 1.创建10 Mytask对象，每个任务持续 1 s，存入list
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> myTasks = intStream.mapToObj(item -> {
            return new MyTask(1);
        }).collect(Collectors.toList());

        // 准备线程池
        int N_CPU = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(Math.min(myTasks.size(), N_CPU * 2));

        // 2.构建10个异步任务
        List<CompletableFuture<Integer>> futureList = myTasks.stream().map(myTask -> {
            return CompletableFuture.supplyAsync(() -> {
                return myTask.doWork();
            }, pool);
        }).collect(Collectors.toList());

        // 3.执行异步任务。完成后，获取异步任务结果存入list，统计总耗时
        // join方法不需要处理异常
        long start = System.currentTimeMillis();
        List<Integer> list = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks %.2f second", futureList.size(), costTime);
        pool.shutdown();
    }
}
