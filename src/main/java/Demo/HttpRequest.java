package Demo;

import utils.CommonUtils;

public class HttpRequest {
    private static void mockCostTimeOpt() {
        CommonUtils.sleepSecond(1);
    }

    // 获取淘宝平台的商品价
    public static PriceResult getTaoBaoPrice(String productName) {
        CommonUtils.printThreadLog("获取淘宝上" + productName + "价格");
        mockCostTimeOpt();

        PriceResult priceResult = new PriceResult("淘宝");
        priceResult.setPrice(5199);
        CommonUtils.printThreadLog("获取淘宝上" + productName + "价格完成：5199");
        return priceResult;
    }

    // 获取淘宝平台的优惠
    public static int getTaoBaoDiscount(String productName) {
        CommonUtils.printThreadLog("获取淘宝上" + productName + "优惠");
        mockCostTimeOpt();

        CommonUtils.printThreadLog("获取淘宝上" + productName + "优惠完成：-200");
        return 200;
    }

    // 获取京东平台的商品价
    public static PriceResult getJingDongPrice(String productName) {
        CommonUtils.printThreadLog("获取京东上" + productName + "价格");
        mockCostTimeOpt();

        PriceResult priceResult = new PriceResult("淘宝");
        priceResult.setPrice(5199);
        CommonUtils.printThreadLog("获取京东上" + productName + "价格完成：5299");
        return priceResult;
    }

    // 获取京东平台的优惠
    public static int getJingDongDiscount(String productName) {
        CommonUtils.printThreadLog("获取京东上" + productName + "优惠");
        mockCostTimeOpt();

        CommonUtils.printThreadLog("获取京东上" + productName + "优惠完成：-150");
        return 150;
    }

    // 获取pdd平台的商品价
    public static PriceResult getPddPrice(String productName) {
        CommonUtils.printThreadLog("获取pdd上" + productName + "价格");
        mockCostTimeOpt();

        PriceResult priceResult = new PriceResult("淘宝");
        priceResult.setPrice(5399);
        CommonUtils.printThreadLog("获取pdd上" + productName + "价格完成：5399");
        return priceResult;
    }

    // 获取pdd平台的优惠
    public static int getJPddDiscount(String productName) {
        CommonUtils.printThreadLog("获取pdd上" + productName + "优惠");
        mockCostTimeOpt();

        CommonUtils.printThreadLog("获取pdd上" + productName + "优惠完成：-5300");
        return 5300;
    }
}
