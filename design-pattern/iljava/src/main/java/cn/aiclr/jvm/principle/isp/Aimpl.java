package cn.aiclr.jvm.principle.isp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aimpl implements ISP1, ISP23 {

    private static final Logger log = LoggerFactory.getLogger(Aimpl.class);

    @Override
    public void operate1() {
        log.info("A#operate1");
    }

    @Override
    public void operate2() {
        log.info("A#operate2");
    }

    @Override
    public void operate3() {
        log.info("A#operate3");
    }
}
