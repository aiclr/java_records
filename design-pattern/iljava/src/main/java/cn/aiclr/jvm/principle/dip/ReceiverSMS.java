package cn.aiclr.jvm.principle.dip;

public class ReceiverSMS implements Receiver{
  @Override
  public String getInfo() {
    return "SMS";
  }
}
