package cn.aiclr.jvm.designpattern.structural.decorator;

public class Caffe extends Drink {
  @Override
  public float cost() {
    return getCost();
  }
}
