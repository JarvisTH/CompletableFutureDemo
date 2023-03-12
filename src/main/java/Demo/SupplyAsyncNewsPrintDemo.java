package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplyAsyncNewsPrintDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        // 开启一个异步任务读取文件
        CompletableFuture<String> news = CompletableFuture.supplyAsync(()->{
            return CommonUtils.readFile("src/main/news.txt");
        });

        CommonUtils.printThreadLog("main continue");
        System.out.println(news.get());
        CommonUtils.printThreadLog("main end");
    }
}
