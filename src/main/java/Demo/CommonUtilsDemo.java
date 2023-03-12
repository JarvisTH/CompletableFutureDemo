package Demo;

import utils.CommonUtils;

public class CommonUtilsDemo {
    public static void main(String[] args) {
        String content = CommonUtils.readFile("src/main/news.txt");
        CommonUtils.printThreadLog(content);
    }

}
