package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;

/**
 * limit使用场景是反复地向缓冲区存取数据
 * 示例
 * 第一次向缓冲区存 9个、读取 9个
 * 第二次向缓冲区存 4个、防止读取 9个，可以设置 limit = 4 读取4个
 */
class LimitTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitTest.class);

    @Test
    void testCharBufferLimit() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.displayBuffer(bf);

        bf.put(0, 'o');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put(1, 'p');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put(2, 'q');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.limit(3);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.displayBuffer(bf);

        bf.put(0, 'o');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put(1, 'p');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put(2, 'q');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        //此时已达到 limit 3 所以想添加数据 需要重新设置 limit, 且最大值为 capacity
        bf.limit(bf.capacity());
        //如果不重新设置 limit 下面是第一个不可读不可写索引，此行异常 java.lang.IndexOutOfBoundsException
        bf.put(3, 'r');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put(4, 's');
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},position={}", bf.capacity(), bf.limit(), bf.position());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> bf.put(5, 't'));
        //limit 需要小于 capacity
        Assertions.assertThrows(IllegalArgumentException.class, () -> bf.limit(6));
        BufferUtil.displayBuffer(bf);
        BufferUtil.displayBuffer(bf);
    }
}
