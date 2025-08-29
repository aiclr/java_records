package cn.aiclr.jvm.designpattern.behavior.strategy;

public class FlyBad implements Fly {

    @Override
    public void fly() {
        log.info("飞行技术一般");
    }
}
