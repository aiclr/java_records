package cn.aiclr.jvm.io.nio.book;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用List.toArray() 转成数组类型
 * List中存储ByteBuffer数据类型，则可以使用List中的toArray（）方法转成ByteBuffer[]数组类型
 */
class ListToArrayTest {

    private static final Logger logger = LoggerFactory.getLogger(ListToArrayTest.class);

    @Test
    void test() {
        ByteBuffer bf1 = ByteBuffer.wrap(new byte[]{'a', 'b', 'c'});
        ByteBuffer bf2 = ByteBuffer.wrap(new byte[]{'x', 'y', 'z'});
        ByteBuffer bf3 = ByteBuffer.wrap(new byte[]{'1', '2', '3'});

        List<ByteBuffer> list = new ArrayList<>();
        list.add(bf1);
        list.add(bf2);
        list.add(bf3);

        ByteBuffer[] bfs = new ByteBuffer[list.size()];
        list.toArray(bfs);
        logger.info("bfs.length={}", bfs.length);
        for (int i = 0; i < bfs.length; i++) {
            ByteBuffer byteBuffer = bfs[i];
            while (byteBuffer.hasRemaining()) {
                logger.info("{}", (char) byteBuffer.get());
            }
        }
    }
}
