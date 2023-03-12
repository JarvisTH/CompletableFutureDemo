package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureAnyOfDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSecond(2);
            return "future1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSecond(3);
            return "future2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleepSecond(1);
            return "future3";
        });

        CompletableFuture<Object> future = CompletableFuture.anyOf(future1, future2, future3);

        CommonUtils.printThreadLog("main continue");
        Object ret = future.get();
        System.out.println("ret =" + ret);
        CommonUtils.printThreadLog("main end");
    }
}
