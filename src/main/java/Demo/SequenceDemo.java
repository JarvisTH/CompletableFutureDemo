package Demo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SequenceDemo {
    public static void main(String[] args) {
        // 1.创建10个MyTeak对象，每个任务1s，存入list
        IntStream intStream = IntStream.range(0, 10);
        List<MyTask> tasks = intStream.mapToObj(item -> {
            return new MyTask(1);
        }).collect(Collectors.toList());

        // 2.执行10个任务
        long start = System.currentTimeMillis();
        List<Integer> results = tasks.stream().map(myTask -> {
            return myTask.doWork();
        }).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("processed %d tasks %.2f second", tasks.size(), costTime);
    }
}
