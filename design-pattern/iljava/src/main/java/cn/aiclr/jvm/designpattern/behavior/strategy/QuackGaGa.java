package cn.aiclr.jvm.designpattern.behavior.strategy;

public class QuackGaGa implements Quack {
    @Override
    public void quack() {
        log.info("嘎嘎叫");
    }
}
