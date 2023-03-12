package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComplelableFutureThenCombineDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CommonUtils.printThreadLog("main start");

        // 1。读取敏感词。解析为数组
        CompletableFuture<String[]> filterFileFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("read filter file");
            return CommonUtils.readFile("src/main/filter_words.txt").split(",");
        });

        // 2.读取news
        CompletableFuture<String> newsFileFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtils.printThreadLog("read news file");
            return CommonUtils.readFile("src/main/news.txt");
        });

        // 3.替换敏感词

        CompletableFuture<String> future = filterFileFuture.thenCombine(newsFileFuture, (filterWords, news) -> {
            for (String word : filterWords) {
                if (news.indexOf(word) >= 0) {
                    news = news.replace(word, "**");
                }
            }
            CommonUtils.printThreadLog("replace word");
            return news;
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.printThreadLog("filter words:" + future.get());
        CommonUtils.printThreadLog("main end");
    }
}
