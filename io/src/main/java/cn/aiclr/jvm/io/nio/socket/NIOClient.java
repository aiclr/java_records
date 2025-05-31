package cn.aiclr.jvm.io.nio.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOClient {

    private static final Logger logger = LoggerFactory.getLogger(NIOClient.class);

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open()) {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
            socketChannel.configureBlocking(false);
            if (!socketChannel.connect(address)) {
                while (!socketChannel.finishConnect()) {
                    logger.info("连接需要时间，客户端不会阻塞，可以做其他工作");
                }
            }
            String msg = "Hello NIO 中文";
            ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
            socketChannel.write(wrap);
//            System.in.read();
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}
