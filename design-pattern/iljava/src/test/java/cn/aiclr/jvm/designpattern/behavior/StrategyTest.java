package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.strategy.Duck;
import cn.aiclr.jvm.designpattern.behavior.strategy.DuckPeking;
import cn.aiclr.jvm.designpattern.behavior.strategy.DuckToy;
import cn.aiclr.jvm.designpattern.behavior.strategy.DuckWild;
import cn.aiclr.jvm.designpattern.behavior.strategy.FlyGood;
import cn.aiclr.jvm.designpattern.behavior.strategy.QuackNo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;


/**
 * <pre>策略模式 strategy pattern
 * 将继承换成组合或者聚合。
 * 定义算法簇,分别封装,让他们可以互相替换,此模式让算法的变化独立与使用算法的用户，
 * 把变化的代码从不变的代码中分离出来。
 * 针对接口编程,策略接口。
 * 多用组合/聚合,少用继承--客户通过组合方式使用策略。
 *
 * 对修改关闭,对扩展开放
 *
 * 策略过多,会导致类数目庞大
 *
 * 如果策略带返回值 可以使用 lambda 简化策略
 */
@DisplayName("策略模式")
class StrategyTest {
    Logger log = LoggerFactory.getLogger(StrategyTest.class);

    @DisplayName("鸭子模式")
    @Test
    void strategyTest() {
        Duck wild = new DuckWild(() -> "吃鱼");
        wild.display();
        Duck toy = new DuckToy(() -> "吃屁");
        toy.display();
        Duck peking = new DuckPeking(() -> "吃饲料");
        peking.display();
        peking.setFly(new FlyGood());
        peking.setQuack(new QuackNo());
        peking.display();
    }

    /**
     * @see Comparator
     */
    @DisplayName("Comparator<T> 策略模式演示")
    @Test
    void comparableTest() {

        Integer[] data = {11, 2, 43, 33, 24, 3};

        //匿名内部类
        Comparator<Integer> comparable =
                new Comparator<Integer>() {
                    //升序
                    @Override
                    public int compare(Integer var1, Integer var2) {
                        if (var1.compareTo(var2) > 0) {
                            return 1;
                        } else if (var1.compareTo(var2) == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                };

        Arrays.sort(data, comparable);
        log.info("asc: {}", Arrays.toString(data));

        // lambda 表达式
        Arrays.sort(data, (var1, var2) -> {
            //降序
            if (var1.compareTo(var2) > 0) {
                return -1;
            } else if (var1.compareTo(var2) == 0) {
                return 0;
            } else {
                return 1;
            }
        });

        log.info("desc: {}", Arrays.toString(data));
    }
}
