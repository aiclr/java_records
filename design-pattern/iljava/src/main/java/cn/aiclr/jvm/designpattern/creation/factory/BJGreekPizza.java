package cn.aiclr.jvm.designpattern.creation.factory;

public class BJGreekPizza extends Pizza{
  @Override
  public void prepare() {
    setName("BJGreekPizza");
    log.info("prepare BJGreekPizza");
  }
}
