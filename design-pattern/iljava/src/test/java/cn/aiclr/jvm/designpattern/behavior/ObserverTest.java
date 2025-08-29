package cn.aiclr.jvm.designpattern.behavior;

import cn.aiclr.jvm.designpattern.behavior.observer.Observer;
import cn.aiclr.jvm.designpattern.behavior.observer.ObserverA;
import cn.aiclr.jvm.designpattern.behavior.observer.ObserverB;
import cn.aiclr.jvm.designpattern.behavior.observer.SubjectWeather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("观察者模式")
class ObserverTest {

    private static final Logger log = LoggerFactory.getLogger(ObserverTest.class);

    /**
     * <pre>lambda 简化观察者模式。（观察者接口只有一个方法）。
     * 可以省略 ObserverA、ObserverB 等具体观察者，
     * 直接在 lambda 中编写业务代码。
     */
    @Test
    void weatherTest() {
        SubjectWeather weather = new SubjectWeather();
        Observer a = new ObserverA();
        Observer b = new ObserverB();

        weather.setData("12345");
        weather.registerObserver(a);
        weather.registerObserver(b);

        weather.registerObserver(data -> {
            String concat = "Lambda=====".concat(data);
            log.info(concat);
        });

        weather.notifyObserver();
    }
}
