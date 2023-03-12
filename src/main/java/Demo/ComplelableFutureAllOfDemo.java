package Demo;

import utils.CommonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ComplelableFutureAllOfDemo {
    public static CompletableFuture<String> readFileFuture(String fileName) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("read txt file");
            return CommonUtils.readFile(fileName);
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        List<String> fileNames = Arrays.asList("src/main/news1.txt", "src/main/news2.txt", "src/main/news3.txt");
        List<CompletableFuture<String>> futures = fileNames.stream().map(fileName -> {
            return readFileFuture(fileName);
        }).collect(Collectors.toList());

        int len = futures.size();
        CompletableFuture[] filesArr = futures.toArray(new CompletableFuture[len]);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(filesArr);
        CompletableFuture<Long> longCompletableFuture = allOf.thenApply(v -> {
            return futures.stream().map(future -> future.join()).filter(content -> content.contains("completablefuture"))
                    .count();
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("count:" + longCompletableFuture.join());
        CommonUtils.printThreadLog("main end");
    }
}
