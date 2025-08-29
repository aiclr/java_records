package cn.aiclr.jvm.designpattern.behavior.mediator;

/**
 * 具体同事类-窗帘
 */
public class ColleagueCurtains extends Colleague {
    /**
     * 创建 {@link ColleagueCurtains} 对象时,通过 {@link Mediator#register(String, Colleague)} 将自己放入到 {@link MediatorConcrete#colleagueMap} 中
     */
    public ColleagueCurtains(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void startCurtains() {
        log.info("start Curtains 开窗");
    }

    public void stopCurtains() {
        log.info("stop Curtains 关窗");
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
