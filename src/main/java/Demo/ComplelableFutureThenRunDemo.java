package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureThenRunDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture.supplyAsync(()->{
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile("src/main/filter_words.txt");
        }).thenAccept(result -> {
            CommonUtils.printThreadLog("read finish");
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
