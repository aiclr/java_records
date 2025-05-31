package cn.aiclr.jvm.juc;

import java.util.concurrent.Callable;

/**
 * Callable接口
 * 相较于Runnable接口，方法可以有返回值，并且可以抛出异常
 */
public class CallableInterfaceForInteger implements Callable<Integer> {

    private int start = 0;
    private int end = 0;

    @Override
    public Integer call() throws Exception {
        return compute(start, end);
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public static int compute(int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            sum += i;
        }
        return sum;
    }
}
