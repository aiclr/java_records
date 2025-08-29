package cn.aiclr.jvm.designpattern.behavior.strategy;

public class FlyGood implements Fly {

    @Override
    public void fly() {
        log.info("善于飞行");
    }
}
