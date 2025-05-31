package cn.aiclr.jvm.io.nio.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * remaining
 * 缓冲区剩余的可操作空间
 * limit-position=remaining
 */
class HasArrayTest {

    private static final Logger logger = LoggerFactory.getLogger(HasArrayTest.class);

    @Test
    @DisplayName("debug 追踪 hasArray() 源码")
    void hasArrayTest() {
        ByteBuffer bf = ByteBuffer.allocate(100);//非直接缓冲区
        bf.put((byte) 1);
        bf.put((byte) 2);
        logger.info("ByteBuffer 使用了一个 byte[]数组 hb 保存数据");
        Assertions.assertTrue(bf.hasArray());
        ByteBuffer dbf = ByteBuffer.allocateDirect(100);//直接缓冲区
        dbf.put((byte) 1);
        dbf.put((byte) 1);
        logger.info("直接缓冲区并没有将数据存储到 byte[]数组 hb 中，而是直接存储在内存中，所以 ByteBuffer.hb 是 null，返回false");
        Assertions.assertFalse(dbf.hasArray());
    }

    @Test
    @DisplayName("遍历 ByteBuffer")
    void iteratorTest() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteBuffer bf = ByteBuffer.wrap(bytes);
        int remaining = bf.remaining();
        for (int i = 0; i < remaining; i++) {
            logger.info("fori _> {}", bf.get());
        }
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.clear();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        while (bf.hasRemaining()) {
            logger.info("while _> {}", bf.get());
        }
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.clear();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        for (; bf.hasRemaining(); ) {
            logger.info("for _> {}", bf.get());
        }
        //偏移量
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={},偏移量 arrayOffset={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining(), bf.arrayOffset());
    }
}