package Demo;

import utils.CommonUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureThenComposeDemo {

    public static CompletableFuture<String> readFileFuture(String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile(fileName);
        });
    }

    public static CompletableFuture<String[]> splitFileFuture(String content) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("split content");
            return content.split(",");
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        CompletableFuture<String[]> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("read file");
            return CommonUtils.readFile("src/main/filter_words.txt");
        }).thenCompose(content -> CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("split content");
            return content.split(",");
        }));

        CommonUtils.printThreadLog("main continue");
        CommonUtils.printThreadLog("filter words:" + Arrays.toString(completableFuture.get()));
        CommonUtils.printThreadLog("main end");
    }
}
