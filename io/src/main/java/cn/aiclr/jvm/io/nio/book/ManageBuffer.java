package cn.aiclr.jvm.io.nio.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * 管理缓冲区内存
 */
public class ManageBuffer {

    private static final Logger logger = LoggerFactory.getLogger(ManageBuffer.class);

    public static void main(String[] args) {
        ByteBuffer bfDirect = ByteBuffer.allocateDirect(100);
        logger.info("position={},limit={},capacity={}", bfDirect.position(), bfDirect.limit(), bfDirect.capacity());
        ByteBuffer bf = ByteBuffer.allocate(100);
        logger.info("position={},limit={},capacity={}", bf.position(), bf.limit(), bf.capacity());

        jvm();

        try {
            hand();
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    /**
     * JVM 管理内存
     * <p>
     * 多次运行后，一直在耗费内存
     * <p>
     * 进程结束后，不会马上回收内存
     * <p>
     * 当触发GC垃圾回收器进行内存的回收
     */
    public static void jvm() {
        ByteBuffer bf = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        byte[] bytes = new byte[]{1};
        while (bf.hasRemaining()) {
            bf.put(bytes);
        }
    }

    /**
     * 手动释放直接缓冲区内存
     * <p>
     * 下面程序运行效果1秒钟之后立即回收内存直接缓冲区所占用的内存
     *
     * @throws Exception
     */
    public static void hand() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //2^31 - 1 = 2,147,483,647 字节 约等于 2 GB
        ByteBuffer bf = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        byte[] bytes = new byte[]{1};
        while (bf.hasRemaining()) {
            bf.put(bytes);
        }
        //仅用于暂停线程，便于调试观察。
        Thread.sleep(1000);
        //获取 DirectByteBuffer 内部的 Cleaner 对象，它是用于管理堆外内存释放的机制。
        Method cleanerMethod = bf.getClass().getMethod("cleaner");
        cleanerMethod.setAccessible(true);
        //通过反射调用 cleaner() 方法获取 Cleaner 对象
        Object returnValue = cleanerMethod.invoke(bf);

        //强制执行内存回收，而不是等待 GC 自动触发
        Method cleanMethod = returnValue.getClass().getMethod("clean");
        cleanMethod.setAccessible(true);
        //调用 clean() 方法强制释放堆外内存
        cleanMethod.invoke(returnValue);
    }

}
