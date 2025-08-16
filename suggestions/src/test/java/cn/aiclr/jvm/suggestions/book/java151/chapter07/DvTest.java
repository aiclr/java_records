package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DvTest {

    /**
     * <pre> Object[] 只能保证数组内的元素是 Object 类型，
     * 却不能确保它们都是 String 的父类型或子类，所以类型转换失败
     */
    @Test
    void testClassCastExp() {

        Assertions.assertThrows(ClassCastException.class, () -> {
            List<String> list = Arrays.asList("A", "B");
            String[] array = Dv.toArray(list);
        });

        //上面的代码 报错原因如下
        Assertions.assertThrows(ClassCastException.class, () -> {
            Object[] objArr = {"A", "B"};
            String[] strArr = (String[]) objArr;
        });


        List<String> list = Arrays.asList("A", "B");
        String[] array = Dv.toArrayPro(list, String.class);
    }

    /**
     * <pre>要想把一个Obejct数组转换为String数组，
     *  只要Object数组的实际类型（Actual Type）也是String就可以了
     *  objArray的实际类型和表面类型都是String数组
     */
    @Test
    @DisplayName("Object[] 转换为 String[]")
    void testObjectArrayToStringArray() {
        //类型转换异常
        Assertions.assertThrows(ClassCastException.class, () -> {
            Object[] objArr = {"A", "B"};
            String[] strArr = (String[]) objArr;
        });

        String[] fromArr = {"A", "B"};
        System.out.println(fromArr);

        //tempArr 的真实类型是 String[]，显示类型为 Object[]
        Object[] tempArr = fromArr;
        System.out.println(tempArr);

        //顺利将 Object[] 转换为 String[]
        String[] toArr = (String[]) tempArr;
        System.out.println(toArr);
    }
}
