package Demo;

import utils.CommonUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ApplyToEitherDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int x = new Random().nextInt(3);
            CommonUtils.sleepSecond(x);
            CommonUtils.printThreadLog("耗时 " + x);
            return x;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int x = new Random().nextInt(5);
            CommonUtils.sleepSecond(x);
            CommonUtils.printThreadLog("耗时 " + x);
            return x;
        });

        CompletableFuture<Integer> future = future1.applyToEither(future2, result -> {
            CommonUtils.printThreadLog("最先到达的结果 " + result);
            return result;
        });

        CommonUtils.sleepSecond(5);
        int ret = future.get();
        CommonUtils.printThreadLog("ret = " + ret);
    }
}
