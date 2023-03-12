package Demo;

import utils.CommonUtils;

public class MyTask {
    private int duration;

    public MyTask(int duration) {
        this.duration = duration;
    }

    // 模拟耗时的长任务
    public int doWork() {
        CommonUtils.printThreadLog("do work");
        CommonUtils.sleepSecond(duration);
        return duration;
    }
}
