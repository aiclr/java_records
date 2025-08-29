package cn.aiclr.jvm.principle.dip;

public class ReceiverEmail implements Receiver{
  @Override
  public String getInfo() {
    return "Email";
  }
}
