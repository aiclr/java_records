package cn.aiclr.jvm.suggestions.book.java151.chapter09.et;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <pre>实现 Callable 接口的类，只是表明它是一个可调用的任务，并不表示它具有多线程运算能力，还是需要执行器来执行
 *
 * 税款计算器，
 *  该运算可能要花费 10 秒钟的时间，
 *  此时不能让用户一直等着吧，需要给用户输出点什么，
 *  让用户知道系统还在运行，这也是系统友好性的体现：
 *      用户输入即有输出，
 *          若耗时较长，则显示运算进度。
 *  如果我们直接计算，就只有一个 main 线程，是不可能有友好提示的，
 *  如果税金不计算完毕，也不会执行后续动作，
 *  所以此时最好的办法就是重启一个线程来运算，让 main 线程做进度提示
 */
public class TaxCalculator implements Callable<Integer> {

    //本金
    private int seedMoney;

    /**
     * 接收主线程提供参数
     *
     * @param seedMoney 本金
     */
    public TaxCalculator(int seedMoney) {
        this.seedMoney = seedMoney;
    }

    @Override
    public Integer call() throws Exception {
        //复杂计算，运行一次需要10秒
        TimeUnit.SECONDS.sleep(10);
        return seedMoney / 10;
    }
}
