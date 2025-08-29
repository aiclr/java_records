package cn.aiclr.jvm.principle.isp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bimpl implements ISP1, ISP45 {

    private static final Logger log = LoggerFactory.getLogger(Bimpl.class);

    @Override
    public void operate1() {
        log.info("B#operate1");
    }

    @Override
    public void operate4() {
        log.info("B#operate4");
    }

    @Override
    public void operate5() {
        log.info("B#operate5");
    }
}
