package cn.aiclr.jvm.designpattern.behavior.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同事类
 */
public abstract class Colleague {

    protected static final Logger log = LoggerFactory.getLogger(Colleague.class);

    private Mediator mediator;

    private String name;

    public abstract void sendMessage(int stateChange);

    public Colleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mediator getMediator() {
        return mediator;
    }

}
