package Demo;

import utils.CommonUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComparePriceService {
    public PriceResult computeRealPrice(PriceResult priceResult, int discount) {
        priceResult.setRealPrice(priceResult.getPrice() - discount);
        priceResult.setDiscount(discount);
        CommonUtils.printThreadLog(priceResult.getPlatform() + "最终价格" + priceResult.getRealPrice());
        return priceResult;
    }

    public PriceResult batchComparePrice(List<String> nameList) {
        // 1.便利每个商品名字。根据商品名称开启异步任务获取最终价， 最终价归集到list
        List<CompletableFuture<PriceResult>> list = nameList.stream().map(productName -> {
            return CompletableFuture
                    .supplyAsync(() -> HttpRequest.getTaoBaoPrice(productName))
                    .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoDiscount(productName)),
                            (((priceResult, discount) -> this.computeRealPrice(priceResult, discount))));
        }).collect(Collectors.toList());

        // 2.把多个商品最终价进行排序获取最小值
        return list.stream().map(CompletableFuture::join)
                .sorted(Comparator.comparing(PriceResult::getRealPrice))
                .findFirst()
                .get();
    }

    public PriceResult getCheapestPlatFormPrice(String productName) {
        // 获取各个平台的价格、优惠
        PriceResult priceResult;
        int discount;

        priceResult = HttpRequest.getTaoBaoPrice(productName);
        discount = HttpRequest.getTaoBaoDiscount(productName);
        PriceResult taoBao = this.computeRealPrice(priceResult, discount);

        priceResult = HttpRequest.getJingDongPrice(productName);
        discount = HttpRequest.getJingDongDiscount(productName);
        PriceResult jingDong = this.computeRealPrice(priceResult, discount);

        priceResult = HttpRequest.getPddPrice(productName);
        discount = HttpRequest.getJPddDiscount(productName);
        PriceResult pdd = this.computeRealPrice(priceResult, discount);

        // 计算最优平台价
        PriceResult result = Stream.of(taoBao, jingDong, pdd)
                .min(Comparator.comparing(PriceResult::getRealPrice))
                .get();

        return result;
    }

    public PriceResult getCheapestPlatFormPriceWithFuture(String productName) {
        // 获取各个平台的价格、优惠
        ExecutorService pool = Executors.newFixedThreadPool(4);

        Future<PriceResult> taoBaoFuture = pool.submit(() -> {
            PriceResult priceResult = HttpRequest.getTaoBaoPrice(productName);
            int discount = HttpRequest.getTaoBaoDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        });

        Future<PriceResult> jingDongFuture = pool.submit(() -> {
            PriceResult priceResult = HttpRequest.getJingDongPrice(productName);
            int discount = HttpRequest.getJingDongDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        });

        Future<PriceResult> pddFuture = pool.submit(() -> {
            PriceResult priceResult = HttpRequest.getPddPrice(productName);
            int discount = HttpRequest.getJPddDiscount(productName);
            return this.computeRealPrice(priceResult, discount);
        });

        // 计算最优平台价
        return Stream.of(taoBaoFuture, jingDongFuture, pddFuture)
                .map(future -> {
                    try {
                        return future.get(5, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    } finally {
                        pool.shutdown();
                    }
                }).filter(Objects::nonNull)
                .min(Comparator.comparing(PriceResult::getRealPrice))
                .get();
    }

    public PriceResult getCheapestPlatFormPriceWithCompletableFuture(String productName) {
        // 获取各个平台的价格、优惠
        ExecutorService pool = Executors.newFixedThreadPool(4);

        CompletableFuture<PriceResult> taobaoCF = CompletableFuture
                .supplyAsync(() -> HttpRequest.getTaoBaoPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getTaoBaoDiscount(productName)),
                        (priceResult, discount) -> this.computeRealPrice(priceResult, discount));

        CompletableFuture<PriceResult> jingdongCF = CompletableFuture
                .supplyAsync(() -> HttpRequest.getJingDongPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getJingDongDiscount(productName)),
                        (priceResult, discount) -> this.computeRealPrice(priceResult, discount));

        CompletableFuture<PriceResult> pddCF = CompletableFuture
                .supplyAsync(() -> HttpRequest.getPddPrice(productName))
                .thenCombine(CompletableFuture.supplyAsync(() -> HttpRequest.getJPddDiscount(productName)),
                        (priceResult, discount) -> this.computeRealPrice(priceResult, discount));

        // 计算最优平台价
        return Stream.of(taobaoCF, jingdongCF, pddCF)
                .map(CompletableFuture::join)
                .min(Comparator.comparing(PriceResult::getRealPrice))
                .get();
    }
}
