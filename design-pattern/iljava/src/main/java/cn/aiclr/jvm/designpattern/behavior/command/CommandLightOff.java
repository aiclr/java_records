package cn.aiclr.jvm.designpattern.behavior.command;

/**
 * 关闭电灯命令
 */
public class CommandLightOff implements Command {

  private final ReceiverLight receiver;

  public CommandLightOff(ReceiverLight receiver) {
    this.receiver = receiver;
  }

  @Override
  public void execute() {
    receiver.off();
  }

  @Override
  public void undo() {
    receiver.on();
  }
}
