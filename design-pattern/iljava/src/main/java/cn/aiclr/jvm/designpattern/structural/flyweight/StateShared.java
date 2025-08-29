package cn.aiclr.jvm.designpattern.structural.flyweight;

/**
 * 共享状态
 */
public class StateShared extends FlyWeight {
    private String data;

    public StateShared(String data) {
        this.data = data;
    }

    @Override
    public void use(StateUnshared unshared) {
        log.info("当前共享的状态为： {}。不共享的内部状态为： {}", data, unshared.getData());
    }

    //一些方法
    public void doSomething() {
        data = data.toLowerCase();
    }
}
