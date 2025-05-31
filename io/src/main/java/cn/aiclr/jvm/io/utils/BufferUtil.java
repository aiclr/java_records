package cn.aiclr.jvm.io.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class BufferUtil {

    private static final Logger logger = LoggerFactory.getLogger(BufferUtil.class);

    public static void displayBuffer(CharBuffer buffer) {
        char[] data = new char[buffer.limit()];
        for (int i = 0; i < buffer.limit(); i++) {
            data[i] = buffer.get(i);
        }
        logger.info("{}", new String(data));
    }

    public static void displayBufferByPosition(CharBuffer buffer) {
        int size = buffer.limit() - buffer.position();
        char[] data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = buffer.get(buffer.position() + i);
        }
        logger.info("{}", new String(data));
    }

    public static void displayBufferByRemaining(CharBuffer buffer) {
        int size = buffer.remaining();
        char[] data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = buffer.get(buffer.position() + i);
        }
        logger.info("{}", new String(data));
    }

    public static void displayBufferByMark(CharBuffer buffer) {
        int size = buffer.remaining();
        char[] data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = buffer.get(buffer.position() + i);
        }
        logger.info("{}", new String(data));
    }

    public static void readBuffer(CharBuffer buffer) {
        int size = buffer.remaining();
        char[] data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = buffer.get();
        }
        logger.info("{}", new String(data));
    }

    public static void display(ByteBuffer buffer) {
        //通过limit控制读取的长度，
        for (int i = 0; i < buffer.limit(); i++) {
            logger.info("{}", String.format("%d\t", buffer.get(i)));
        }
    }
}
