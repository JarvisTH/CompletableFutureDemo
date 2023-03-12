package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SupplyAsyncNewsPrintWithThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        ExecutorService executor = Executors.newFixedThreadPool(5);
        // 开启一个异步任务读取文件
        CompletableFuture<String> news = CompletableFuture.supplyAsync(()->{
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile("src/main/news.txt");
        }, executor);

        CommonUtils.printThreadLog("main continue");
        System.out.println(news.get());
        executor.shutdown();
        CommonUtils.printThreadLog("main end");

    }
}
