package cn.aiclr.jvm.designpattern.behavior.command;

/**
 * 打开电灯命令
 */
public class CommandLightOn implements Command {

    private final ReceiverLight receiver;

    public CommandLightOn(ReceiverLight receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.on();
    }

    @Override
    public void undo() {
        receiver.off();
    }
}
