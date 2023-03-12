package Demo;

import utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureThenApplyAsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String[]> filterWordsFuture = CompletableFuture.supplyAsync(()->{
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile("src/main/filter_words.txt");
        }).thenApplyAsync(result -> {
            CommonUtils.printThreadLog("split content");
            return result.split(",");
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.printThreadLog("filter words:" + Arrays.toString(filterWordsFuture.get()));
        CommonUtils.printThreadLog("main end");
    }
}
