package cn.aiclr.jvm.designpattern.creation;

import cn.aiclr.jvm.designpattern.creation.builder.BuildHouseDirect;
import cn.aiclr.jvm.designpattern.creation.builder.CommonBuilder;
import cn.aiclr.jvm.designpattern.creation.builder.HighBuilder;
import cn.aiclr.jvm.designpattern.creation.builder.House;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("建造者模式")
class BuilderTest {

    private static final Logger log = LoggerFactory.getLogger(BuilderTest.class);

    @Test
    void build() {
        CommonBuilder commonBuilder = new CommonBuilder();
        BuildHouseDirect direct = new BuildHouseDirect(commonBuilder);

        House commonHouse = direct.build();
        log.info("{}", commonHouse);
        assertSame(1, commonHouse.getFloors());

        commonBuilder.buildBasic();
        commonBuilder.buildWalls();
        commonBuilder.roofed();
        log.info("{}", commonHouse);
        assertSame(2, commonHouse.getFloors());


        HighBuilder highBuilder = new HighBuilder();
        direct.setBuilder(highBuilder);

        House highHouse = direct.build();
        log.info("{}", highHouse);
        assertSame(1, highHouse.getFloors());


        highBuilder.buildBasic();
        highBuilder.buildWalls();

        highBuilder.buildBasic();
        highBuilder.buildWalls();

        highBuilder.buildBasic();
        highBuilder.buildWalls();
        highBuilder.roofed();
        log.info("{}", highHouse);
        assertSame(4, highHouse.getFloors());
    }
}
