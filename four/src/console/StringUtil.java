package console;

public class StringUtil {
    //判断字符串是否为空
    //去括号trim()
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

}
