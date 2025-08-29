package cn.aiclr.jvm.designpattern.behavior.mediator;

/**
 * 具体同事类-警报器
 */
public class ColleagueAlarm extends Colleague {

    /**
     * 创建 {@link ColleagueAlarm} 对象时,通过 {@link Mediator#register(String, Colleague)} 将自己放入到 {@link MediatorConcrete#colleagueMap} 中
     */
    public ColleagueAlarm(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void sendAlarm(int stateChange) {
        sendMessage(stateChange);
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
