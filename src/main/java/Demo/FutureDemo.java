package Demo;

import utils.CommonUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 1.读取敏感词汇 可能是耗时任务  => thread1
        Future<String[]> filterWordsFuture = executorService.submit(()->{
            String content = CommonUtils.readFile("src/main/filter_words.txt");
            String[] filterWords = content.split(",");
            return filterWords;
        });

        // 2.读取新闻稿 可能是耗时任务 => thread2
        Future<String> newsFuture = executorService.submit(()->{
            return CommonUtils.readFile("FutureDemo");
        });

        // 3.替换操作 可能是耗时任务 => thread3
        Future<String> newsFutureReplaced = executorService.submit(()->{
            String[] words = filterWordsFuture.get();
            String content = newsFuture.get();

            for (String word : words) {
                if (content.indexOf(word) >= 0) {
                    content = content.replace(word, "**");
                }
            }

            return content;
        });

        // 4.打印输出替换后的新闻稿 => main
        String news = newsFutureReplaced.get();
        System.out.println("news:" + news);
    }
}
