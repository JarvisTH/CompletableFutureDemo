package Demo;

import java.util.concurrent.CompletableFuture;

public class ExceptionChainDemo {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            int r = 1 / 0;
            return "result1";
        }).thenApply(result -> {
            return result + " result2";
        }).thenApply(result -> {
            return result + " result3";
        }).exceptionally(ex -> {
            System.out.println("出现异常 " + ex.getMessage());
            return "exception";
        });
    }
}
