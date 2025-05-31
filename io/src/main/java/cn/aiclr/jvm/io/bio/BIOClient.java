package cn.aiclr.jvm.io.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class BIOClient {
    private static final Logger logger = LoggerFactory.getLogger(BIOClient.class);

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 6666);
             OutputStream outputStream = socket.getOutputStream()
        ) {
            outputStream.write("你好!".getBytes(StandardCharsets.UTF_8));
            outputStream.write("Hello World!".getBytes(StandardCharsets.UTF_8));
        } catch (UnknownHostException e) {
            logger.error("{}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

}
