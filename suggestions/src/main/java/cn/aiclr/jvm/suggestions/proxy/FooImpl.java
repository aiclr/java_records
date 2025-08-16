package cn.aiclr.jvm.suggestions.proxy;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FooImpl implements Foo {

    private static final Logger logger = Logger.getLogger(FooImpl.class.getName());

    private static final Formatter formatter = new SimpleFormatter();

    static {
        Handler fh;
        try {
            fh = new FileHandler("%t/wombat2.log", true);
            fh.setFormatter(formatter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fh);
    }

    @Override
    public Object bar(Object data) {
        logger.info("FooImpl#run");
        return "2333";
    }
}
