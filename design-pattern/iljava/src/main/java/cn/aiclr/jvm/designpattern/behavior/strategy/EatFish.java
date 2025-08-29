package cn.aiclr.jvm.designpattern.behavior.strategy;

public class EatFish implements Eat {
    @Override
    public String eat() {
        return "小鱼";
    }
}
