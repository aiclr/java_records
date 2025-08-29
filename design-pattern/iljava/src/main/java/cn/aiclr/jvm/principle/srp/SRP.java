package cn.aiclr.jvm.principle.srp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单一职责原则
 */
public class SRP {

    public static final Logger log = LoggerFactory.getLogger(SRP.class);

    public static void main(String[] args) {
        /**
         * 过于单一
         */
        Vehicle vehicle = new Vehicle();
        vehicle.run("摩托车");

        /**
         * 改动较大
         */
        VehicleAir air = new VehicleAir();
        air.run("飞机");

        /**
         * 改动小
         */
        VehiclePro pro = new VehiclePro();
        pro.run("摩托车");
        pro.runAir("飞机");
        pro.runWater("轮船");
    }

    /**
     * <pre>遵守单一职责原则
     * 交通工具类
     * 由于设计缺陷，该类只能处理公路上运行的交通工具
     */
    public static class Vehicle {
        public void run(String vehicle) {
            log.info("{}在公路上运行......", vehicle);
        }
    }

    /**
     * <pre>遵守单一职责原则
     * 改动较大。在原类型 {@link Vehicle} 基础上分解类，还要修改客户端代码
     * 扩展一个能处理天空上运行的交通工具类
     * 由于设计缺陷，该类只能处理天空上运行的交通工具
     */
    public static class VehicleAir {
        public void run(String vehicle) {
            log.info("{}在天空上运行......", vehicle);
        }
    }

    /**
     * <pre>在原类型 {@link Vehicle} 基础上仅增加方法，改动较小
     * 在方法级别遵守单一职责原则，没有在类级别遵守单一职责原则
     */
    public static class VehiclePro {
        public void run(String vehicle) {
            log.info("{}在公路上运行......", vehicle);
        }

        public void runAir(String vehicle) {
            log.info("{}在天空上运行......", vehicle);
        }

        public void runWater(String vehicle) {
            log.info("{}在水里运行......", vehicle);
        }
    }
}
