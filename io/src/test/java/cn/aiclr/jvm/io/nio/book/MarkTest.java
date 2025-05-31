package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;
import java.nio.InvalidMarkException;

class MarkTest {

    private static final Logger logger = LoggerFactory.getLogger(MarkTest.class);

    /**
     * mark
     * 索引，调用reset()方法，会将缓冲区的position重置为该索引
     */
    @Test
    void testCharBufferMark() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.displayBufferByRemaining(bf);
        bf.position(2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.mark();
        logger.info("在position={}处调用 mark() 加标记", bf.position());
        bf.put('z');//position ++
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.displayBufferByRemaining(bf);
        bf.reset();
        logger.info("调用reset() 重置position = {} = mark标记位置", bf.position());
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.displayBufferByMark(bf);
    }

    /**
     * 如果定义了mark，将position调整为小于该mark值时，该mark被丢弃
     * 调用reset() 抛出异常 java.nio.InvalidMarkException
     */
    @Test
    void testCharBufferPositionRemoveMark() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.position(2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.mark();
        logger.info("在position={}处调用 mark() 加标记", bf.position());
        bf.position(1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        logger.warn("根据position(int newPosition)源码可知 position 小于 mark mark 被设置为 -1");

        Assertions.assertThrowsExactly(InvalidMarkException.class, bf::reset, "根据reset()源码可只当mark<0时，抛异常");
    }

    /**
     * 如果定义了mark，将limit调整为小于该mark值时，该mark被丢弃
     * 调用reset() 抛出异常 java.nio.InvalidMarkException
     */
    @Test
    void testCharBufferLimitRemoveMark() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.position(2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.mark();
        logger.info("在position={}处调用 mark() 加标记", bf.position());
        bf.limit(1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        logger.warn("limit(int newLimit)源码可知 limit 小于 mark mark 被设置为 -1");

        Assertions.assertThrowsExactly(InvalidMarkException.class, bf::reset, "根据reset()源码可只当mark<0时，抛异常");
    }
}
