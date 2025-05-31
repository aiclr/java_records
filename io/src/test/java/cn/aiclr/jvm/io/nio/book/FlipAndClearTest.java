package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;

/**
 * flip(),写后从头读，防止之前设置的position影响当前需要读取的位置
 * limit = position;
 * position = 0;
 * mark = -1;
 * <p>
 * clear()，从头开始写，防止之前设置的position，limit，影响当前的写入
 * position = 0;
 * limit = capacity;
 * mark = -1;
 */
class FlipAndClearTest {

    private static final Logger logger = LoggerFactory.getLogger(FlipAndClearTest.class);

    @Test
    @DisplayName("flip 写入后从头开始读")
    void testFlip() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.position(1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put('s');//position +
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        Assertions.assertEquals('c', bf.get());
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.flip();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        Assertions.assertEquals('a', bf.get());
    }

    @Test
    @DisplayName("clear() 从头开始写")
    void testClear() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.position(1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put('s');//position +
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.clear();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
    }

    @Test
    void flipErrorTest() {
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'};
        CharBuffer bf = CharBuffer.wrap(chars);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put("我是中国人我爱中华人民共和国");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        //不用flip(),多输出opqrs
        bf.clear();
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
    }


    @Test
    void flipOkTest() {
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'};
        CharBuffer bf = CharBuffer.wrap(chars);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put("我是中国人我爱中华人民共和国");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.flip();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
    }

    @Test
    void clearErrorTest() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put("我是中国人我爱中华人民共和国");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        //重新写入
        bf.put("他是美国人");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        //不 clear()
        bf.flip();
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
    }

    @Test
    void clearOkTest() {
        char[] data = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'};
        CharBuffer bf = CharBuffer.wrap(data);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put("我是中国人我爱中华人民共和国");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.flip();
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        //重新写入
        bf.clear();
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.put("他是美国人");
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
        bf.flip();
        BufferUtil.readBuffer(bf);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={}", bf.capacity(), bf.limit(), bf.position());
    }
}
