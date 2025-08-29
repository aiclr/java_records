package cn.aiclr.jvm.principle.isp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface NoneISP {

    Logger log = LoggerFactory.getLogger(NoneISP.class);

    void operate1();

    void operate2();

    void operate3();

    void operate4();

    void operate5();
}
