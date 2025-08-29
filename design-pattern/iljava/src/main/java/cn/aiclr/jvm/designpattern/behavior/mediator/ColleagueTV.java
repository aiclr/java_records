package cn.aiclr.jvm.designpattern.behavior.mediator;

/**
 * 具体同事类-电视机
 */
public class ColleagueTV extends Colleague {
    /**
     * 创建 {@link ColleagueTV} 对象时,通过 {@link Mediator#register(String, Colleague)} 将自己放入到 {@link MediatorConcrete#colleagueMap} 中
     */
    public ColleagueTV(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void startTV() {
        log.info("start TV");
    }

    public void stopTV() {
        log.info("stop TV");
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
