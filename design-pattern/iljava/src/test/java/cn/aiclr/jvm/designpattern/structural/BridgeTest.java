package cn.aiclr.jvm.designpattern.structural;

import cn.aiclr.jvm.designpattern.structural.bridge.Brand;
import cn.aiclr.jvm.designpattern.structural.bridge.BrandIPhone;
import cn.aiclr.jvm.designpattern.structural.bridge.BrandXiaomi;
import cn.aiclr.jvm.designpattern.structural.bridge.Phone;
import cn.aiclr.jvm.designpattern.structural.bridge.PhoneFolded;
import cn.aiclr.jvm.designpattern.structural.bridge.PhoneUpRight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("桥接模式")
class BridgeTest {

  @Test
  void bridgeTest() {
    Brand xiaomi = new BrandXiaomi();
    Brand iphone = new BrandIPhone();

    Phone phone = new PhoneUpRight(xiaomi);
    phone.open();
    phone.call();
    phone.close();

    phone = new PhoneUpRight(iphone);
    phone.open();
    phone.call();
    phone.close();

    phone = new PhoneFolded(xiaomi);
    phone.open();
    phone.call();
    phone.close();

    phone = new PhoneFolded(iphone);
    phone.open();
    phone.call();
    phone.close();
  }
}