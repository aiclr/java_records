package cn.aiclr.jvm.suggestions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 牛逼人 {

    private static final Logger logger = LoggerFactory.getLogger(牛逼人.class);

    public void 我真牛逼啊() {
        logger.info("不牛逼不行啊");
    }

    public static void main(String[] args) {
        牛逼人 牛逼人 = new 牛逼人();
        牛逼人.我真牛逼啊();
    }
}