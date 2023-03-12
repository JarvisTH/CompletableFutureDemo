package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

public class RunAsyncNewsPrintDemo {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        // 开启一个异步任务读取文件
        CompletableFuture.runAsync(()->{
            System.out.println("read file");
            String content = CommonUtils.readFile("src/main/news.txt");
            System.out.println(content);
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
