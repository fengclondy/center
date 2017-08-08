package cn.htd.common;

import java.util.Arrays;

public class CommonUtil {

	public static boolean useArraysBinarySearch(String[] arr, String targetValue) {
        int a = Arrays.binarySearch(arr, targetValue);
        if (a > 0)
            return true;
        else
            return false;
    }
}
