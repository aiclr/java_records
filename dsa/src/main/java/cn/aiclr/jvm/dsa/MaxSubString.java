package cn.aiclr.jvm.dsa;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>含有多个空格的 ASCII 串
 * 求最长非空格字符串的长度，尽可能最优
 *
 * aa bc aaaa aaa ==> 4
 */
public class MaxSubString {

    public static int getStrLength(String str) {
        int result = 0;
        int temp = 0;
        str = str.trim();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!Character.isSpaceChar(c)) {
                temp += 1;
                if (result < temp) {
                    result = temp;
                }
            } else {
                temp = 0;
            }
        }
        return result;
    }

    //减少循环次数
    public static int getStrLengthPlus(String str) {
        int result = 0;
        int temp = 0;
        str = str.trim();
        char[] chars = str.toCharArray();
        //减少循环次数
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isSpaceChar(chars[i])) {
                temp += 1;
                if (result < temp) {
                    result = temp;
                }
            } else {
                //最长字符串==剩余字符串可直接退出循环
                if (result >= chars.length - 1 - i) {
                    break;
                }
                temp = 0;
            }
        }
        return result;
    }

    /**
     * <pre>获取字符串中无重复的子字符串长度
     *
     * 输入: "abcabcbb"
     * 输出：3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * w前后出现过
     * 输入：pwwkew
     * 输出：3
     */
    public static int lengthOfLongestSubstring(String str) {
        byte[] bytes = str.getBytes();
        List<Byte> temp = new ArrayList<>();
        int maxLength = 0;
        for (int j = 0; j < bytes.length; j++) {
            //从第一位开始遍历将不同值放到temp
            int tmp = 0;
            for (int i = j; i < bytes.length; i++) {
                if (!temp.contains(bytes[i])) {
                    tmp++;
                    temp.add(bytes[i]);
                } else {
                    //置空
                    temp.clear();
                    break;
                }
            }
            if (maxLength < tmp) {
                maxLength = tmp;
            }
        }
        return maxLength;
    }
}
