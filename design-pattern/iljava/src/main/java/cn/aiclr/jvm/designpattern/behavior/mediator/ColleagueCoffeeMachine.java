package cn.aiclr.jvm.designpattern.behavior.mediator;

/**
 * 具体同事类-咖啡机
 */
public class ColleagueCoffeeMachine extends Colleague {
    /**
     * 创建 {@link ColleagueCoffeeMachine} 对象时,通过 {@link Mediator#register(String, Colleague)} 将自己放入到 {@link MediatorConcrete#colleagueMap} 中
     */
    public ColleagueCoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void startCoffeeMachine() {
        log.info("start CoffeeMachine");
    }

    public void stopCoffeeMachine() {
        log.info("stop CoffeeMachine");
    }

    /**
     * 调用中介对象的getMessage
     *
     * @param stateChange
     */
    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, getName());
    }
}
