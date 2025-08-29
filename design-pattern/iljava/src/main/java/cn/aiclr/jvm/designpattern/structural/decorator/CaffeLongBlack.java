package cn.aiclr.jvm.designpattern.structural.decorator;

/**
 * 具体咖啡类，扩展很方便
 */
public class CaffeLongBlack extends Caffe {

  public CaffeLongBlack() {
    setCost(10.0f);
    setDes("LongBlack Caffe");
  }
}
