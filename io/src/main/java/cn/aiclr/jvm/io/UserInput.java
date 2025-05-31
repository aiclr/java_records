package cn.aiclr.jvm.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {

    private static final Logger logger = LoggerFactory.getLogger(UserInput.class);

    public static void main(String[] args) {
        logger.info("{}", getName());
    }

    /**
     * 输入交互方法
     */
    public static String getName() {
        BufferedReader string = new BufferedReader(new InputStreamReader(System.in));
        logger.info("wait input... cheese or greek");
        String name = null;
        try {
            name = string.readLine();
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
        }
        return name;
    }

}
