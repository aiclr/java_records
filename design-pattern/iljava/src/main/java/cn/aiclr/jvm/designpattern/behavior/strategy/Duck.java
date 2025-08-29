package cn.aiclr.jvm.designpattern.behavior.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 算法-策略 使用者
 */
public class Duck {

    private static final Logger log = LoggerFactory.getLogger(Duck.class);

    public String name;
    public Fly fly;
    public Quack quack;
    public Eat eat;

    public Duck(Eat eat) {
        this.eat = eat;
    }

    public void display() {
        log.info("{} : ", name);
        this.fly();
        this.quack();
        log.info("{}", eat.eat());
    }

    public void fly() {
        fly.fly();
    }

    public void quack() {
        quack.quack();
    }

    public void setFly(Fly fly) {
        this.fly = fly;
    }

    public void setQuack(Quack quack) {
        this.quack = quack;
    }
}
