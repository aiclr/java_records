package cn.aiclr.jvm.principle;

import cn.aiclr.jvm.principle.dip.NoneEmail;
import cn.aiclr.jvm.principle.dip.NoneSMS;
import cn.aiclr.jvm.principle.dip.NoneUser;
import cn.aiclr.jvm.principle.dip.Receiver;
import cn.aiclr.jvm.principle.dip.ReceiverEmail;
import cn.aiclr.jvm.principle.dip.ReceiverSMS;
import cn.aiclr.jvm.principle.dip.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("依赖倒置原则")
class DIPTest {

    @DisplayName("不符合依赖倒置原则")
    @Test
    void noneTest() {
        NoneUser user = new NoneUser();
        //代码简单易读
        NoneEmail email = new NoneEmail();
        assertEquals("Email", user.receiveEmail(email));

        //新增短信类型时 需要增加对应消息的方法
        NoneSMS sms = new NoneSMS();
        assertEquals("SMS", user.receiveSMS(sms));
    }

    @DisplayName("符合-接口传递依赖关系")
    @Test
    void dipInterfaceTest() {
        User user = new User();

        Receiver email = new ReceiverEmail();
        assertEquals("Email", user.receive(email));

        Receiver sms = new ReceiverSMS();
        assertEquals("SMS", user.receive(sms));
    }

    @DisplayName("符合-构造器传递依赖关系")
    @Test
    void dipConstructorTest() {
        Receiver email = new ReceiverEmail();
        User user = new User(email);
        assertEquals("Email", user.receive());

        Receiver sms = new ReceiverSMS();
        user = new User(sms);
        assertEquals("SMS", user.receive());
    }

    @DisplayName("符合-setter方法传递依赖关系")
    @Test
    void dipSetterTest() {
        User user = new User();

        Receiver email = new ReceiverEmail();
        user.setReceiver(email);
        assertEquals("Email", user.receive());

        Receiver sms = new ReceiverSMS();
        user.setReceiver(sms);
        assertEquals("SMS", user.receive());
    }

}
