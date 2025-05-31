package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;

/**
 * position
 * 将要读取或写入的位置下标
 */
class PositionTest {

    private static final Logger logger = LoggerFactory.getLogger(PositionTest.class);

    @Test
    void testCharBufferLimit() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.displayBufferByPosition(bf);
        bf.position(2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put('z');//position ++
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.displayBufferByPosition(bf);
    }
}
