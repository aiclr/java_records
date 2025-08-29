package cn.aiclr.jvm.designpattern.behavior.template.loan.lambda;

import cn.aiclr.jvm.designpattern.behavior.template.loan.ApplicationDenied;

public interface Criteria {
  void check() throws ApplicationDenied;
}
