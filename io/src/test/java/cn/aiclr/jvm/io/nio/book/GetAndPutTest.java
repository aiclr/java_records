package cn.aiclr.jvm.io.nio.book;

import cn.aiclr.jvm.io.utils.BufferUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

class GetAndPutTest {

    private static final Logger logger = LoggerFactory.getLogger(GetAndPutTest.class);

    @Test
    @DisplayName("ByteBuffer get put 测试")
    void test() {
        byte[] bts1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] bts2 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        ByteBuffer bf1 = ByteBuffer.wrap(bts1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf1.capacity(), bf1.limit(), bf1.position(), bf1.remaining());
        BufferUtil.display(bf1);

        ByteBuffer bf2 = ByteBuffer.wrap(bts2, 2, 4);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf2.capacity(), bf2.limit(), bf2.position(), bf2.remaining());
        BufferUtil.display(bf2);

        bf1.put((byte) 11);//会移动 position
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf1.capacity(), bf1.limit(), bf1.position(), bf1.remaining());
        bf1.put(2, (byte) 11);//不会移动 position
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf2.capacity(), bf2.limit(), bf2.position(), bf2.remaining());

        logger.info("bf1.get()={}", bf1.get());//会移动 position
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf1.capacity(), bf1.limit(), bf1.position(), bf1.remaining());
        logger.info("bf1.get(2)={}", bf1.get(2));//不会移动 position
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf2.capacity(), bf2.limit(), bf2.position(), bf2.remaining());

        BufferUtil.display(bf1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf1.capacity(), bf1.limit(), bf1.position(), bf1.remaining());
        BufferUtil.display(bf2);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf2.capacity(), bf2.limit(), bf2.position(), bf2.remaining());

    }

    @Test
    @DisplayName("ByteBuffer get put 批量测试")
    void patchTest() {

        byte[] bts1 = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] bts2 = new byte[]{55, 66, 77, 88};
        ByteBuffer bf = ByteBuffer.allocate(10);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        bf.put(bts1);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.display(bf);
        bf.position(2);
        //将byte2的66，77，88放入缓冲区第三个位置
        bf.put(bts2, 1, 3);
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());
        BufferUtil.display(bf);

        //接收批量get到的数组
        byte[] bts3 = new byte[bf.capacity()];
        //索引为3的四个字节
        bf.get(bts3, 3, 4);
        for (int i = 0; i < bts3.length; i++) {
            logger.info("{}", String.format("%d\t", bts3[i]));
        }
        logger.info("缓冲区容量capacity={},限制操作区容量limit={},下一个读取或写入操作位置索引position={},剩余可操作空间 remaining={}", bf.capacity(), bf.limit(), bf.position(), bf.remaining());

    }
}
