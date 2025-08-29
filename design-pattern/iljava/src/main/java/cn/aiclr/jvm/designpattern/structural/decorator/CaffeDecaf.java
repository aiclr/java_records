package cn.aiclr.jvm.designpattern.structural.decorator;

/**
 * 具体咖啡类，扩展很方便
 */
public class CaffeDecaf extends Caffe {

  public CaffeDecaf() {
    setCost(4.0f);
    setDes("Decaf Caffe");
  }
}
