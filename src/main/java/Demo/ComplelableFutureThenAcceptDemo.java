package Demo;

import utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureThenAcceptDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture.supplyAsync(()->{
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile("src/main/filter_words.txt");
        }).thenApply(result -> {
            CommonUtils.printThreadLog("split content");
            return result.split(",");
        }).thenAccept(result -> {
            CommonUtils.printThreadLog("filter words:" + Arrays.toString(result));
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
