package cn.aiclr.jvm.suggestions.utils;

import java.util.List;

public class ListUtils {

    /**
     * 利用 SubList 获取所有检索目标的位置信息
     *
     * @param list   原始集合
     * @param target 检索目标
     * @param result 结果集
     */
    public static void getIndex(List<String> list, String target, List<Integer> result) {
        if (list.contains(target)) {
            //记录位置，此时 index 记录的是每个子列表中的位置
            int index = list.indexOf(target);
            //计算得出源列表位置
            if (result.isEmpty()) {
                result.add(index);
            } else {
                result.add(index + result.getLast() + 1);
            }
            //判断是否还有剩余元素（是否还有子列表），有则递归
            if (index < list.size() - 1) {
                List<String> sublist = list.subList(index + 1, list.size());
                if (sublist.contains(target)) {
                    getIndex(sublist, target, result);
                }
            }
        }
    }
}
