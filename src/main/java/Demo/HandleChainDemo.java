package Demo;

import utils.CommonUtils;

import java.util.concurrent.CompletableFuture;

public class HandleChainDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            int r = 1 / 0;
            return "result1";
        }).handle((result, ex) -> {
            CommonUtils.printThreadLog("上一步异常的恢复");
            if (ex != null) {
                CommonUtils.printThreadLog("出现异常 " + ex.getMessage());
                return "unknown";
            }
            return result;
        }).thenApply(result -> {
            return result + " result2";
        }).thenApply(result -> {
            return result + " result3";
        }).thenAccept(ret -> {
            CommonUtils.printThreadLog(ret);
        });
    }
}
