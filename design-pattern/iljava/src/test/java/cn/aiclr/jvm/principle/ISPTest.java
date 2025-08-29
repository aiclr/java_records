package cn.aiclr.jvm.principle;

import cn.aiclr.jvm.principle.isp.Aimpl;
import cn.aiclr.jvm.principle.isp.Bimpl;
import cn.aiclr.jvm.principle.isp.NoneAImpl;
import cn.aiclr.jvm.principle.isp.NoneBImpl;
import cn.aiclr.jvm.principle.isp.NoneUseA;
import cn.aiclr.jvm.principle.isp.NoneUseB;
import cn.aiclr.jvm.principle.isp.UseA;
import cn.aiclr.jvm.principle.isp.UseB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("接口隔离原则")
class ISPTest {

    @DisplayName("符合")
    @Test
    void ocpTest() {
        UseA useA = new UseA();
        Aimpl a = new Aimpl();
        useA.dependAOperate1(a);
        useA.dependAOperate2(a);
        useA.dependAOperate3(a);

        UseB useB = new UseB();
        Bimpl b = new Bimpl();
        useB.dependBOperate1(b);
        useB.dependBOperate4(b);
        useB.dependBOperate5(b);
    }

    @DisplayName("不符合")
    @Test
    void noneISPTest() {
        NoneUseA useA = new NoneUseA();
        NoneAImpl a = new NoneAImpl();
        useA.dependAOperate1(a);
        useA.dependAOperate2(a);
        useA.dependAOperate3(a);

        NoneUseB useB = new NoneUseB();
        NoneBImpl b = new NoneBImpl();
        useB.dependBOperate1(b);
        useB.dependBOperate4(b);
        useB.dependBOperate5(b);
    }
}
