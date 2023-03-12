package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

public class RunAsyncLambdaDemo {
    public static void main(String[] args) {
        CommonUtils.printThreadLog("main start");

        // 开启一个异步任务读取文件
        CompletableFuture.runAsync(()->{
            CommonUtils.printThreadLog("读取文件开始");
            CommonUtils.sleepSecond(3);
            CommonUtils.printThreadLog("读取文件结束");
        });

        CommonUtils.printThreadLog("main continue");
        CommonUtils.sleepSecond(4);
        CommonUtils.printThreadLog("main end");
    }
}
