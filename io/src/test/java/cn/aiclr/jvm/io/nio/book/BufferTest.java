package cn.aiclr.jvm.io.nio.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * ByteBuffer、CharBuffer、DoubleBuffer、FloatBuffer、IntBuffer、LongBuffer和ShortBuffer是抽象类，
 * wrap()就相当于创建这些缓冲区的工厂方法
 * <p>
 * <ul>
 *      <li>缓冲区的capacity不能为负数，缓冲区的limit不能为负数，缓冲区的position不能为负数</li>
 *      <li>position不能大于其limit------看position(int newPosition)源码即可</li>
 *      <li>limit不能大于其capacity------看limit(int newLimit)源码即可</li>
 *      <li>如果定义了mark，则在将position或limit调整为小于该mark值时，该mark被丢弃</li>
 *      <li>如果未定义mark，那么调用reset()将抛出InvalidMarkException异常------reset()源码</li>
 *      <li>如果position大于新的limit则position的值就是新limit值------limit(int newLimit)源码</li>
 *      <li>当limit和position值一样时，在指定的position写入数据时会出现异常，因为此位置是被限制的</li>
 * </ul>
 */
class BufferTest {

    private static final Logger logger = LoggerFactory.getLogger(BufferTest.class);

    @Test
    void testByteBuffer() {
        byte[] data = new byte[]{1, 2, 3};
        ByteBuffer bf = ByteBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(3, bf.capacity());
        Assertions.assertEquals(3, bf.limit());
        Assertions.assertEquals(0, bf.position());

        ByteBuffer bf1 = ByteBuffer.wrap(data, 1, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(3, bf1.capacity());
        Assertions.assertEquals(3, bf1.limit());
        Assertions.assertEquals(1, bf1.position());
    }

    @Test
    void testShortBuffer() {
        short[] data = new short[]{1, 2, 3, 4};
        ShortBuffer bf = ShortBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(4, bf.capacity());
        Assertions.assertEquals(4, bf.limit());
        Assertions.assertEquals(0, bf.position());

        ShortBuffer bf1 = ShortBuffer.wrap(data, 2, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(4, bf1.capacity());
        Assertions.assertEquals(4, bf1.limit());
        Assertions.assertEquals(2, bf1.position());
    }

    @Test
    void testIntBuffer() {
        int[] data = new int[]{1, 2, 3, 4, 5};
        IntBuffer bf = IntBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(5, bf.capacity());
        Assertions.assertEquals(5, bf.limit());
        Assertions.assertEquals(0, bf.position());

        IntBuffer bf1 = IntBuffer.wrap(data, 3, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(5, bf1.capacity());
        Assertions.assertEquals(5, bf1.limit());
        Assertions.assertEquals(3, bf1.position());
    }

    @Test
    void testLongBuffer() {
        long[] data = new long[]{1, 2, 3, 4, 5, 6};
        LongBuffer bf = LongBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(6, bf.capacity());
        Assertions.assertEquals(6, bf.limit());
        Assertions.assertEquals(0, bf.position());

        LongBuffer bf1 = LongBuffer.wrap(data, 4, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(6, bf1.capacity());
        Assertions.assertEquals(6, bf1.limit());
        Assertions.assertEquals(4, bf1.position());
    }

    @Test
    void testFloatBuffer() {
        float[] data = new float[]{1, 2, 3, 4, 5, 6, 7};
        FloatBuffer bf = FloatBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(7, bf.capacity());
        Assertions.assertEquals(7, bf.limit());
        Assertions.assertEquals(0, bf.position());

        FloatBuffer bf1 = FloatBuffer.wrap(data, 5, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(7, bf1.capacity());
        Assertions.assertEquals(7, bf1.limit());
        Assertions.assertEquals(5, bf1.position());
    }

    @Test
    void testDoubleBuffer() {
        double[] data = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        DoubleBuffer bf = DoubleBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(8, bf.capacity());
        Assertions.assertEquals(8, bf.limit());
        Assertions.assertEquals(0, bf.position());

        DoubleBuffer bf1 = DoubleBuffer.wrap(data, 6, 2);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(8, bf1.capacity());
        Assertions.assertEquals(8, bf1.limit());
        Assertions.assertEquals(6, bf1.position());
    }

    @Test
    void testCharBuffer() {
        char[] data = new char[]{'a', 'b', 'c', 'd'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("capacity={},limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        logger.info("{}", bf.getClass().getName());
        Assertions.assertEquals(4, bf.capacity());
        Assertions.assertEquals(4, bf.limit());
        Assertions.assertEquals(0, bf.position());

        CharBuffer bf1 = CharBuffer.wrap(data, 0, 4);
        logger.info("capacity={},limit={},position={}", bf1.capacity(), bf1.limit(), bf1.position());
        logger.info("{}", bf1.getClass().getName());
        Assertions.assertEquals(4, bf1.capacity());
        Assertions.assertEquals(4, bf1.limit());
        Assertions.assertEquals(0, bf1.position());
    }

    /**
     * 缓冲区的capacity不能为负数，缓冲区的limit不能为负数，缓冲区的position不能为负数
     * 查看JDK源码
     */
    @Test
    void negativeTest() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> ByteBuffer.allocate(-1), "缓冲区的capacity不能为负数");
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> charBuffer.limit(-1), "缓冲区的limit不能为负数");
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> charBuffer.position(-1), "缓冲区的position不能为负数");
    }

}
