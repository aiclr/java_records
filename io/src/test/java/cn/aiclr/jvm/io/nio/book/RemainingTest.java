package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;

/**
 * remaining
 * 缓冲区剩余的可操作空间
 * limit-position=remaining
 */
class RemainingTest {

    private static final Logger logger = LoggerFactory.getLogger(RemainingTest.class);

    @Test
    void testCharBufferRemaining() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.displayBufferByRemaining(bf);
        bf.position(2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.put('z');//position ++
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.displayBufferByRemaining(bf);
    }
}