package Demo;

import java.util.Arrays;
import java.util.List;

public class ComparePriceDemo {
    public static void main(String[] args) {
        // 4.CompletableFuture方式
        List<String> products = Arrays.asList("iphone14黑", "iphone14白", "iphone14红");
        ComparePriceService comparePriceService = new ComparePriceService();
        long start = System.currentTimeMillis();
        PriceResult priceResult = comparePriceService.batchComparePrice(products);
        long end = System.currentTimeMillis();
        double costTime = (end - start) / 1000.0;
        System.out.printf("costs %.2f second \n", costTime);
        System.out.println(priceResult);


    }
}
