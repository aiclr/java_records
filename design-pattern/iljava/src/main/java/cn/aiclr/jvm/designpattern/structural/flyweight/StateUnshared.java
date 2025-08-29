package cn.aiclr.jvm.designpattern.structural.flyweight;

/**
 * 不共享内部状态
 */
public class StateUnshared {

  private String data;

  public StateUnshared(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  //一些方法
  public void doSomething(){
      data=data.toLowerCase();
  }
}
